package sn.bfrost.myafiback.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sn.bfrost.myafiback.controller.errors.BadRequestAlertException;
import sn.bfrost.myafiback.models.Role;
import sn.bfrost.myafiback.models.User;
import sn.bfrost.myafiback.repository.UserRepository;
import sn.bfrost.myafiback.service.JwtService;
import sn.bfrost.myafiback.service.UserService;
import sn.bfrost.myafiback.service.dto.ChangePasswordRequest;
import sn.bfrost.myafiback.service.dto.UserDTO;
import sn.bfrost.myafiback.service.mapper.UserMapper;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    @Override
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new BadRequestAlertException("Mauvaise mot de passe","user","password.wrong");
        }
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new BadRequestAlertException("Les mots de passes ne sont pas les mêmes","user","password.not.same");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);
    }

    @Override
    public UserDTO create(UserDTO userDTO) {

        var userExist = userRepository.findByEmail(userDTO.getEmail());
        if(userExist.isPresent()){
            throw new BadRequestAlertException("Email existant","user","email.existant");
        }
        User user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setFirstConnection(true);
        String matricule = generateMatricule();
        user.setMatricule(matricule);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserDTO update(Long id, UserDTO userDTO) {
        var existingUser = userRepository.findById(id);
        if(existingUser.isPresent()){
            if(!userDTO.getEmail().equals(existingUser.get().getEmail())){
                var userExist = userRepository.findByEmail(userDTO.getEmail());
                if(userExist.isPresent()){
                    throw new BadRequestAlertException("Email existant","user","email.existant");
                }
            }
        }
        User user = userMapper.toEntity(userDTO);
        User finalUser = user;
        existingUser.ifPresent(u -> finalUser.setPassword(u.getPassword()));
        user = userRepository.save(finalUser);
        return userMapper.toDto(user);
    }


    @Override
    public Page<UserDTO> searchUsers(Pageable pageable, String matricule, String nom, String prenom, String filiere, String email, String role) {
        Role searchRole = Role.valueOf(role);
        return userRepository.searchUsers(pageable, matricule,nom, prenom,filiere,email,searchRole).map(userMapper::toDto);
    }


    @Override
    public Optional<UserDTO> findOne(Long id) {
        return userRepository.findById(id).map(userMapper::toDto);
    }

    @Override
    public Optional<UserDTO> findByEmail(String email) {
        return userRepository.findByEmail(email).map(userMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
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
