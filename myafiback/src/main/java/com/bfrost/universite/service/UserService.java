package com.bfrost.universite.service;

import com.bfrost.universite.config.Constants;
import com.bfrost.universite.domain.Authority;
import com.bfrost.universite.domain.Semestre;
import com.bfrost.universite.domain.User;
import com.bfrost.universite.domain.enumeration.TypeProfil;
import com.bfrost.universite.repository.AuthorityRepository;
import com.bfrost.universite.repository.CalendrierCoursRepository;
import com.bfrost.universite.repository.SemestreRepository;
import com.bfrost.universite.repository.UserRepository;
import com.bfrost.universite.security.AuthoritiesConstants;
import com.bfrost.universite.security.SecurityUtils;
import com.bfrost.universite.service.dto.*;
import com.bfrost.universite.service.impl.AbsenceServiceImpl;
import com.bfrost.universite.service.impl.AnneeScolaireUserServiceImpl;
import com.bfrost.universite.service.impl.NoteServiceImpl;
import com.bfrost.universite.service.mapper.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tech.jhipster.security.RandomUtil;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for managing users.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthorityRepository authorityRepository;

    private final CacheManager cacheManager;

    private final CampusMapper campusMapper;

    private final FiliereMapper filiereMapper;

    private final ProfilMapper profilMapper;
    private final UserMapper userMapper;
    private final AuthorityMapper authorityMapper;
    private final MailService mailService;
    private final CalendrierCoursService calendrierCoursService;
    private final NoteService noteService;
    private final AbsenceService absenceService;
    private final AnneeScolaireUserService anneeScolaireUserService;
    private final SemestreRepository semestreRepository;


    public Optional<User> activateRegistration(String email) {
        LOG.debug("Activating user for activation key {}", email);
        return userRepository
            .findOneByLogin(email)
            .map(user -> {
                // activate given user for the registration key.
                user.setActivated(!user.isActivated());
                user.setActivationKey(null);
                this.clearUserCaches(user);
                LOG.debug("Activated user: {}", user);
                String title = "";
                String content = "";
                if(user.isActivated()){
                    title = "Activation de compte";
                    content = "Votre compte MyAfi a été activé. Veuillez vous connectez";
                }else{
                    title = "Désactivation de compte";
                    content = "Votre compte MyAfi a été désactivé. Veuillez vous rapprochez de l'administration pour plus d'information.";
                }
                mailService.sendEmail(user.getEmail(),title,content, false, false);
                return user;
            });
    }

    public User registerUser(AdminUserDTO userDTO, String password) {
        userRepository
            .findOneByLogin(userDTO.getLogin().toLowerCase())
            .ifPresent(existingUser -> {
                boolean removed = removeNonActivatedUser(existingUser);
                if (!removed) {
                    throw new UsernameAlreadyUsedException();
                }
            });
        userRepository
            .findOneByEmailIgnoreCase(userDTO.getEmail())
            .ifPresent(existingUser -> {
                boolean removed = removeNonActivatedUser(existingUser);
                if (!removed) {
                    throw new EmailAlreadyUsedException();
                }
            });
        User newUser = new User();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(userDTO.getLogin().toLowerCase());
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        if (userDTO.getEmail() != null) {
            newUser.setEmail(userDTO.getEmail().toLowerCase());
        }
        newUser.setImageUrl(userDTO.getImageUrl());
        newUser.setLangKey(userDTO.getLangKey());
        // new user is not active
        newUser.setActivated(true);
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        newUser.setAuthorities(authorityMapper.toEntity(userDTO.getAuthorities()));
        newUser.setDateDeNaissance(userDTO.getDateDeNaissance());
        newUser.setCampus(campusMapper.toEntity(userDTO.getCampus()));
        newUser.setProfil(profilMapper.toEntity(userDTO.getProfil()));
        newUser.setFiliere(filiereMapper.toEntity(userDTO.getFiliere()));
        newUser.setTelephone(userDTO.getTelephone());
        newUser.setMatricule(generateMatricule());
        newUser.setCreatedBy(userDTO.getCreatedBy());
        newUser.setFirstConnection(true);
        newUser.setNationalite(userDTO.getNationalite());
        newUser.setCampuses(campusMapper.toEntity(userDTO.getCampuses()));
        LOG.info("avant save: {}", newUser);
        newUser = userRepository.save(newUser);
        LOG.info("apres save: {}", newUser);
        this.clearUserCaches(newUser);
        return newUser;
    }

    private boolean removeNonActivatedUser(User existingUser) {
        if (existingUser.isActivated()) {
            return false;
        }
        userRepository.delete(existingUser);
        userRepository.flush();
        this.clearUserCaches(existingUser);
        return true;
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update.
     * @return updated user.
     */
    public Optional<AdminUserDTO> updateUser(AdminUserDTO userDTO) {
        return Optional.of(userRepository.findById(userDTO.getId()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(user -> {
                this.clearUserCaches(user);
                user.setLogin(userDTO.getLogin().toLowerCase());
                user.setFirstName(userDTO.getFirstName());
                user.setLastName(userDTO.getLastName());
                if (userDTO.getEmail() != null) {
                    user.setEmail(userDTO.getEmail().toLowerCase());
                }
                user.setImageUrl(userDTO.getImageUrl());
                user.setActivated(userDTO.isActivated());
                user.setLangKey(userDTO.getLangKey());
                user.setDateDeNaissance(userDTO.getDateDeNaissance());
                user.setCampus(campusMapper.toEntity(userDTO.getCampus()));
                user.setProfil(profilMapper.toEntity(userDTO.getProfil()));
                user.setFiliere(filiereMapper.toEntity(userDTO.getFiliere()));
                user.setTelephone(userDTO.getTelephone());
                user.getAuthorities().clear();
                user.setNationalite(userDTO.getNationalite());
                user.setActivated(true);
                user.setCampuses(campusMapper.toEntity(userDTO.getCampuses()));
                user.setAuthorities(authorityMapper.toEntity(userDTO.getAuthorities()));
                userRepository.save(user);
                this.clearUserCaches(user);
                return user;
            })
            .map(userMapper::toDto);
    }

    public void deleteUser(String login) {
        userRepository
            .findOneByLogin(login)
            .ifPresent(user -> {
                userRepository.delete(user);
                this.clearUserCaches(user);
            });
    }


    @Transactional
    public void changePassword(String email) {
        LOG.debug("Reset user for account {}", email);
        userRepository
                .findOneByLogin(email)
                .map(user -> {
                    // activate given user for the registration key.
                    user.setFirstConnection(true);
                    user.setPassword(passwordEncoder.encode("Passer@123"));
                    user.setActivationKey(null);
                    this.clearUserCaches(user);
                    LOG.debug("Activated user: {}", user);
                    mailService.sendEmail(user.getEmail(),"Restauration de mot de passe","Votre mot de passe MyAFI a été restauré.\nVeuillez vous connecter avec Passer@123 puis le changer", false, false);
                    return user;
                });


    }

    @Transactional(readOnly = true)
    public Page<AdminUserDTO> getAllManagedUsers(Pageable pageable, String prenom, String nom, String email, String telephone, String filiere, String campus, String matricule, String profil,Integer admin, String name) {
        //return userRepository.findAll(pageable).map(userMapper::userToAdminUserDTO);

        TypeProfil typeProfil = StringUtils.hasText(profil) ? TypeProfil.valueOf(profil) : null;
        return userRepository.managedUserBy(pageable, prenom, nom, email, telephone, filiere, campus, matricule, typeProfil, admin, name).map(userMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllPublicUsers(Pageable pageable) {
        return userRepository.findAllByIdNotNullAndActivatedIsTrue(pageable).map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public Optional<AdminUserDTO> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneWithAuthoritiesByLogin(login).map(user -> {
            AdminUserDTO dto = userMapper.toDto(user);

            List<NoteDTO> notes = noteService.findByUserId(user.getId());
            List<AbsenceDTO> absences = absenceService.findByUserId(user.getId());

            Map<SemestreDTO, List<NoteDTO>> notesParSemestre = notes.stream()
                    .filter(n -> n.getMatiereUser() != null && n.getMatiereUser().getSemestre() != null)
                    .collect(Collectors.groupingBy(n -> n.getMatiereUser().getSemestre()));

            Map<SemestreDTO, List<AbsenceDTO>> absencesParSemestre = absences.stream()
                    .filter(a -> a.getCalendrierCours() != null &&
                            a.getCalendrierCours().getMatiereUser() != null &&
                            a.getCalendrierCours().getMatiereUser().getSemestre() != null)
                    .collect(Collectors.groupingBy(a -> a.getCalendrierCours().getMatiereUser().getSemestre()));

            List<StatParSemestreDTO> statsParSemestre = new ArrayList<>();
            Double moyennePrecedente = null;
            Double presencePrecedente = null;

            Set<SemestreDTO> tousLesSemestres = new HashSet<>();
            tousLesSemestres.addAll(notesParSemestre.keySet());
            tousLesSemestres.addAll(absencesParSemestre.keySet());

            for (SemestreDTO semestre : tousLesSemestres) {
                StatistiquesSemestreDTO stats = new StatistiquesSemestreDTO();

                // Moyenne
                List<NoteDTO> notesSemestre = notesParSemestre.getOrDefault(semestre, List.of());
                double total = 0;
                double totalCredits = 0;

                Map<Long, List<NoteDTO>> notesParUE = notesSemestre.stream()
                        .filter(n -> n.getMatiereUser().getMatiere() != null &&
                                n.getMatiereUser().getMatiere().getUe() != null &&
                                n.getValeur() != null)
                        .collect(Collectors.groupingBy(n -> n.getMatiereUser().getMatiere().getUe().getId()));

                for (var ueEntry : notesParUE.entrySet()) {
                    List<NoteDTO> notesUE = ueEntry.getValue();
                    double moyenneUE = notesUE.stream().mapToDouble(NoteDTO::getValeur).average().orElse(0.0);
                    String creditStr = notesUE.get(0).getMatiereUser().getMatiere().getUe().getCredit();
                    double credit = 0;
                    try {
                        credit = Double.parseDouble(creditStr);
                    } catch (NumberFormatException ignored) {}
                    total += moyenneUE * credit;
                    totalCredits += credit;
                }

                if (totalCredits > 0) {
                    double moyenne = total / totalCredits;
                    stats.setMoyenne(moyenne);
                    if (moyennePrecedente != null) {
                        double trend = moyenne - moyennePrecedente;
                        stats.setTrendMoyenne(trend);
                        stats.setIconMoyenne(trend > 0 ? "trending_up" : trend < 0 ? "trending_down" : "trending_flat");

                    }
                    moyennePrecedente = moyenne;
                }

                // Taux de présence
                List<AbsenceDTO> abs = absencesParSemestre.getOrDefault(semestre, List.of());
                long totalCours = abs.size();
                long absents = abs.stream().filter(a -> Boolean.FALSE.equals(a.getPresence())).count();
                if (totalCours > 0) {
                    double tauxPresence = 100.0 - ((absents * 100.0) / totalCours);
                    stats.setTauxPresence(tauxPresence);
                    if (presencePrecedente != null) {
                        double trendP = tauxPresence - presencePrecedente;
                        stats.setTrendPresence(trendP);
                        stats.setIconPresence(trendP > 0 ? "trending_up" : trendP < 0 ? "trending_down" : "trending_flat");
                    }

                    presencePrecedente = tauxPresence;
                }

                statsParSemestre.add(new StatParSemestreDTO(semestre, stats));
            }
            dto.setStatsParSemestre(statsParSemestre);

            // Prochain cours
            List<AnneeScolaireUserDTO> inscriptions = anneeScolaireUserService.findByUserId(user.getId());
            Set<Long> anneeIdsInscrit = inscriptions.stream()
                    .map(a -> a.getAnneeScolaire().getId())
                    .collect(Collectors.toSet());

            FiliereDTO filiereEtudiant = dto.getFiliere();
            if (filiereEtudiant != null && !anneeIdsInscrit.isEmpty()) {
                List<CalendrierCoursDTO> tousLesCours = calendrierCoursService.findByFiliere(filiereEtudiant.getId());

                Optional<CalendrierCoursDTO> prochainCours = tousLesCours.stream()
                        .filter(c -> c.getDateDebut().isAfter(ZonedDateTime.now()))
                        .filter(c -> c.getMatiereUser() != null &&
                                c.getMatiereUser().getFiliere() != null &&
                                c.getMatiereUser().getAnneeScolaire() != null)
                        .filter(c -> Objects.equals(c.getMatiereUser().getFiliere().getId(), filiereEtudiant.getId()) &&
                                anneeIdsInscrit.contains(c.getMatiereUser().getAnneeScolaire().getId()))
                        .sorted(Comparator.comparing(CalendrierCoursDTO::getDateDebut))
                        .findFirst();

              prochainCours.ifPresent(dto::setCalendrierCours);
            }

            // Récupère tous les semestres du système
            List<Semestre> semestres = semestreRepository.findAll();
            int nbTotalSemestres = semestres.size();


            // On extrait les semestres uniques auxquels il est inscrit
            Set<Long> semestresInscrits = inscriptions.stream()
                    .map(i -> i.getSemestre().getId())
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

// Progression = % de semestres complétés / existants
            double progression = 0;
            if (nbTotalSemestres > 0) {
                progression = ((double) semestresInscrits.size() / nbTotalSemestres) * 100;
            }

            dto.setProgressionAcademique(progression);

            return dto;
        });
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLogin);
    }

    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired every day, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        userRepository
            .findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS))
            .forEach(user -> {
                LOG.debug("Deleting not activated user {}", user.getLogin());
                userRepository.delete(user);
                this.clearUserCaches(user);
            });
    }

    /**
     * Gets a list of all the authorities.
     * @return a list of all the authorities.
     */
    @Transactional(readOnly = true)
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).toList();
    }

    private void clearUserCaches(User user) {
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE)).evictIfPresent(user.getLogin());
        if (user.getEmail() != null) {
            Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE)).evictIfPresent(user.getEmail());
        }
    }

    private String generateMatricule() {
        String anneeScolaire = getAnneeScolaire();

        int length = new Random().nextInt(2) + 4; // 4 ou 5 lettres
        StringBuilder lettres = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = (char) ('A' + new Random().nextInt(26));
            lettres.append(c);
        }

        return anneeScolaire + lettres;
    }


    private String getAnneeScolaire() {
        LocalDate today = LocalDate.now();
        int year = today.getYear();

        int startYear, endYear;
        if (today.getMonthValue() >= 9) { // Septembre à Décembre
            startYear = year;
            endYear = year + 1;
        } else { // Janvier à Août
            startYear = year - 1;
            endYear = year;
        }

        return String.format("%02d%02d", startYear % 100, endYear % 100);
    }

}
