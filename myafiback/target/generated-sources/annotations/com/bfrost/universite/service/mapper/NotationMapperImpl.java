package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.AnneeScolaire;
import com.bfrost.universite.domain.Authority;
import com.bfrost.universite.domain.CalendrierCours;
import com.bfrost.universite.domain.Campus;
import com.bfrost.universite.domain.Filiere;
import com.bfrost.universite.domain.Matiere;
import com.bfrost.universite.domain.MatiereUser;
import com.bfrost.universite.domain.Notation;
import com.bfrost.universite.domain.Profil;
import com.bfrost.universite.domain.Salle;
import com.bfrost.universite.domain.Semestre;
import com.bfrost.universite.domain.UE;
import com.bfrost.universite.domain.User;
import com.bfrost.universite.service.dto.AdminUserDTO;
import com.bfrost.universite.service.dto.AnneeScolaireDTO;
import com.bfrost.universite.service.dto.AuthorityDTO;
import com.bfrost.universite.service.dto.CalendrierCoursDTO;
import com.bfrost.universite.service.dto.CampusDTO;
import com.bfrost.universite.service.dto.FiliereDTO;
import com.bfrost.universite.service.dto.MatiereDTO;
import com.bfrost.universite.service.dto.MatiereUserDTO;
import com.bfrost.universite.service.dto.NotationDTO;
import com.bfrost.universite.service.dto.ProfilDTO;
import com.bfrost.universite.service.dto.SalleDTO;
import com.bfrost.universite.service.dto.SemestreDTO;
import com.bfrost.universite.service.dto.UEDTO;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-24T17:00:10+0000",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.4 (Oracle Corporation)"
)
@Component
public class NotationMapperImpl implements NotationMapper {

    @Override
    public Notation toEntity(NotationDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Notation notation = new Notation();

        notation.setId( dto.getId() );
        notation.setNote( dto.getNote() );
        notation.setAppreciation( dto.getAppreciation() );
        notation.setCalendrierCours( calendrierCoursDTOToCalendrierCours( dto.getCalendrierCours() ) );
        notation.setEtudiant( adminUserDTOToUser( dto.getEtudiant() ) );

        return notation;
    }

    @Override
    public NotationDTO toDto(Notation entity) {
        if ( entity == null ) {
            return null;
        }

        NotationDTO notationDTO = new NotationDTO();

        notationDTO.setId( entity.getId() );
        notationDTO.setNote( entity.getNote() );
        notationDTO.setAppreciation( entity.getAppreciation() );
        notationDTO.setCalendrierCours( calendrierCoursToCalendrierCoursDTO( entity.getCalendrierCours() ) );
        notationDTO.setEtudiant( userToAdminUserDTO( entity.getEtudiant() ) );

        return notationDTO;
    }

    @Override
    public List<Notation> toEntity(List<NotationDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Notation> list = new ArrayList<Notation>( dtoList.size() );
        for ( NotationDTO notationDTO : dtoList ) {
            list.add( toEntity( notationDTO ) );
        }

        return list;
    }

    @Override
    public List<NotationDTO> toDto(List<Notation> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<NotationDTO> list = new ArrayList<NotationDTO>( entityList.size() );
        for ( Notation notation : entityList ) {
            list.add( toDto( notation ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Notation entity, NotationDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getNote() != null ) {
            entity.setNote( dto.getNote() );
        }
        if ( dto.getAppreciation() != null ) {
            entity.setAppreciation( dto.getAppreciation() );
        }
        if ( dto.getCalendrierCours() != null ) {
            if ( entity.getCalendrierCours() == null ) {
                entity.setCalendrierCours( new CalendrierCours() );
            }
            calendrierCoursDTOToCalendrierCours1( dto.getCalendrierCours(), entity.getCalendrierCours() );
        }
        if ( dto.getEtudiant() != null ) {
            if ( entity.getEtudiant() == null ) {
                entity.setEtudiant( new User() );
            }
            adminUserDTOToUser1( dto.getEtudiant(), entity.getEtudiant() );
        }
    }

    protected AnneeScolaire anneeScolaireDTOToAnneeScolaire(AnneeScolaireDTO anneeScolaireDTO) {
        if ( anneeScolaireDTO == null ) {
            return null;
        }

        AnneeScolaire anneeScolaire = new AnneeScolaire();

        anneeScolaire.setId( anneeScolaireDTO.getId() );
        anneeScolaire.setNom( anneeScolaireDTO.getNom() );

        return anneeScolaire;
    }

    protected Authority authorityDTOToAuthority(AuthorityDTO authorityDTO) {
        if ( authorityDTO == null ) {
            return null;
        }

        Authority authority = new Authority();

        authority.setName( authorityDTO.getName() );

        return authority;
    }

    protected Set<Authority> authorityDTOSetToAuthoritySet(Set<AuthorityDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Authority> set1 = new LinkedHashSet<Authority>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( AuthorityDTO authorityDTO : set ) {
            set1.add( authorityDTOToAuthority( authorityDTO ) );
        }

        return set1;
    }

    protected Filiere filiereDTOToFiliere(FiliereDTO filiereDTO) {
        if ( filiereDTO == null ) {
            return null;
        }

        Filiere filiere = new Filiere();

        filiere.setId( filiereDTO.getId() );
        filiere.setNom( filiereDTO.getNom() );

        return filiere;
    }

    protected Campus campusDTOToCampus(CampusDTO campusDTO) {
        if ( campusDTO == null ) {
            return null;
        }

        Campus campus = new Campus();

        campus.setId( campusDTO.getId() );
        campus.setNom( campusDTO.getNom() );

        return campus;
    }

    protected Profil profilDTOToProfil(ProfilDTO profilDTO) {
        if ( profilDTO == null ) {
            return null;
        }

        Profil profil = new Profil();

        profil.setId( profilDTO.getId() );
        profil.setNom( profilDTO.getNom() );
        profil.setRedirection( profilDTO.getRedirection() );
        profil.setTypeProfil( profilDTO.getTypeProfil() );
        profil.setAuthorities( authorityDTOSetToAuthoritySet( profilDTO.getAuthorities() ) );

        return profil;
    }

    protected Set<Campus> campusDTOSetToCampusSet(Set<CampusDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Campus> set1 = new LinkedHashSet<Campus>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( CampusDTO campusDTO : set ) {
            set1.add( campusDTOToCampus( campusDTO ) );
        }

        return set1;
    }

    protected User adminUserDTOToUser(AdminUserDTO adminUserDTO) {
        if ( adminUserDTO == null ) {
            return null;
        }

        User user = new User();

        user.setCreatedBy( adminUserDTO.getCreatedBy() );
        user.setCreatedDate( adminUserDTO.getCreatedDate() );
        user.setLastModifiedBy( adminUserDTO.getLastModifiedBy() );
        user.setLastModifiedDate( adminUserDTO.getLastModifiedDate() );
        user.setId( adminUserDTO.getId() );
        user.setLogin( adminUserDTO.getLogin() );
        user.setPassword( adminUserDTO.getPassword() );
        user.setFirstName( adminUserDTO.getFirstName() );
        user.setLastName( adminUserDTO.getLastName() );
        user.setEmail( adminUserDTO.getEmail() );
        user.setActivated( adminUserDTO.isActivated() );
        user.setLangKey( adminUserDTO.getLangKey() );
        user.setImageUrl( adminUserDTO.getImageUrl() );
        user.setDateDeNaissance( adminUserDTO.getDateDeNaissance() );
        user.setTelephone( adminUserDTO.getTelephone() );
        user.setFirstConnection( adminUserDTO.getFirstConnection() );
        user.setMatricule( adminUserDTO.getMatricule() );
        user.setNationalite( adminUserDTO.getNationalite() );
        user.setAuthorities( authorityDTOSetToAuthoritySet( adminUserDTO.getAuthorities() ) );
        user.setFiliere( filiereDTOToFiliere( adminUserDTO.getFiliere() ) );
        user.setCampus( campusDTOToCampus( adminUserDTO.getCampus() ) );
        user.setProfil( profilDTOToProfil( adminUserDTO.getProfil() ) );
        user.setCampuses( campusDTOSetToCampusSet( adminUserDTO.getCampuses() ) );

        return user;
    }

    protected UE uEDTOToUE(UEDTO uEDTO) {
        if ( uEDTO == null ) {
            return null;
        }

        UE uE = new UE();

        uE.setId( uEDTO.getId() );
        uE.setNom( uEDTO.getNom() );
        uE.setCredit( uEDTO.getCredit() );

        return uE;
    }

    protected Matiere matiereDTOToMatiere(MatiereDTO matiereDTO) {
        if ( matiereDTO == null ) {
            return null;
        }

        Matiere matiere = new Matiere();

        matiere.setId( matiereDTO.getId() );
        matiere.setNom( matiereDTO.getNom() );
        matiere.setCredit( matiereDTO.getCredit() );
        matiere.ue( uEDTOToUE( matiereDTO.getUe() ) );

        return matiere;
    }

    protected Semestre semestreDTOToSemestre(SemestreDTO semestreDTO) {
        if ( semestreDTO == null ) {
            return null;
        }

        Semestre semestre = new Semestre();

        semestre.setId( semestreDTO.getId() );
        semestre.setNom( semestreDTO.getNom() );
        semestre.setAnnee( semestreDTO.getAnnee() );

        return semestre;
    }

    protected MatiereUser matiereUserDTOToMatiereUser(MatiereUserDTO matiereUserDTO) {
        if ( matiereUserDTO == null ) {
            return null;
        }

        MatiereUser matiereUser = new MatiereUser();

        matiereUser.setId( matiereUserDTO.getId() );
        matiereUser.setAnneeScolaire( anneeScolaireDTOToAnneeScolaire( matiereUserDTO.getAnneeScolaire() ) );
        matiereUser.setUser( adminUserDTOToUser( matiereUserDTO.getUser() ) );
        matiereUser.setMatiere( matiereDTOToMatiere( matiereUserDTO.getMatiere() ) );
        matiereUser.setFiliere( filiereDTOToFiliere( matiereUserDTO.getFiliere() ) );
        matiereUser.setSemestre( semestreDTOToSemestre( matiereUserDTO.getSemestre() ) );

        return matiereUser;
    }

    protected Salle salleDTOToSalle(SalleDTO salleDTO) {
        if ( salleDTO == null ) {
            return null;
        }

        Salle salle = new Salle();

        salle.setId( salleDTO.getId() );
        salle.setNumero( salleDTO.getNumero() );
        salle.campus( campusDTOToCampus( salleDTO.getCampus() ) );

        return salle;
    }

    protected CalendrierCours calendrierCoursDTOToCalendrierCours(CalendrierCoursDTO calendrierCoursDTO) {
        if ( calendrierCoursDTO == null ) {
            return null;
        }

        CalendrierCours calendrierCours = new CalendrierCours();

        calendrierCours.setId( calendrierCoursDTO.getId() );
        calendrierCours.setLien( calendrierCoursDTO.getLien() );
        calendrierCours.setDateDebut( calendrierCoursDTO.getDateDebut() );
        calendrierCours.setDateFin( calendrierCoursDTO.getDateFin() );
        calendrierCours.setMatiereUser( matiereUserDTOToMatiereUser( calendrierCoursDTO.getMatiereUser() ) );
        calendrierCours.setSalle( salleDTOToSalle( calendrierCoursDTO.getSalle() ) );

        return calendrierCours;
    }

    protected CampusDTO campusToCampusDTO(Campus campus) {
        if ( campus == null ) {
            return null;
        }

        CampusDTO campusDTO = new CampusDTO();

        campusDTO.setId( campus.getId() );
        campusDTO.setNom( campus.getNom() );

        return campusDTO;
    }

    protected SalleDTO salleToSalleDTO(Salle salle) {
        if ( salle == null ) {
            return null;
        }

        SalleDTO salleDTO = new SalleDTO();

        salleDTO.setId( salle.getId() );
        salleDTO.setNumero( salle.getNumero() );
        salleDTO.setCampus( campusToCampusDTO( salle.getCampus() ) );

        return salleDTO;
    }

    protected AnneeScolaireDTO anneeScolaireToAnneeScolaireDTO(AnneeScolaire anneeScolaire) {
        if ( anneeScolaire == null ) {
            return null;
        }

        AnneeScolaireDTO anneeScolaireDTO = new AnneeScolaireDTO();

        anneeScolaireDTO.setId( anneeScolaire.getId() );
        anneeScolaireDTO.setNom( anneeScolaire.getNom() );

        return anneeScolaireDTO;
    }

    protected AuthorityDTO authorityToAuthorityDTO(Authority authority) {
        if ( authority == null ) {
            return null;
        }

        AuthorityDTO authorityDTO = new AuthorityDTO();

        authorityDTO.setName( authority.getName() );

        return authorityDTO;
    }

    protected Set<AuthorityDTO> authoritySetToAuthorityDTOSet(Set<Authority> set) {
        if ( set == null ) {
            return null;
        }

        Set<AuthorityDTO> set1 = new LinkedHashSet<AuthorityDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Authority authority : set ) {
            set1.add( authorityToAuthorityDTO( authority ) );
        }

        return set1;
    }

    protected FiliereDTO filiereToFiliereDTO(Filiere filiere) {
        if ( filiere == null ) {
            return null;
        }

        FiliereDTO filiereDTO = new FiliereDTO();

        filiereDTO.setId( filiere.getId() );
        filiereDTO.setNom( filiere.getNom() );

        return filiereDTO;
    }

    protected ProfilDTO profilToProfilDTO(Profil profil) {
        if ( profil == null ) {
            return null;
        }

        ProfilDTO profilDTO = new ProfilDTO();

        profilDTO.setId( profil.getId() );
        profilDTO.setNom( profil.getNom() );
        profilDTO.setRedirection( profil.getRedirection() );
        profilDTO.setTypeProfil( profil.getTypeProfil() );
        profilDTO.setAuthorities( authoritySetToAuthorityDTOSet( profil.getAuthorities() ) );

        return profilDTO;
    }

    protected Set<CampusDTO> campusSetToCampusDTOSet(Set<Campus> set) {
        if ( set == null ) {
            return null;
        }

        Set<CampusDTO> set1 = new LinkedHashSet<CampusDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Campus campus : set ) {
            set1.add( campusToCampusDTO( campus ) );
        }

        return set1;
    }

    protected AdminUserDTO userToAdminUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        AdminUserDTO adminUserDTO = new AdminUserDTO();

        adminUserDTO.setId( user.getId() );
        adminUserDTO.setLogin( user.getLogin() );
        adminUserDTO.setFirstName( user.getFirstName() );
        adminUserDTO.setLastName( user.getLastName() );
        adminUserDTO.setEmail( user.getEmail() );
        adminUserDTO.setMatricule( user.getMatricule() );
        adminUserDTO.setPassword( user.getPassword() );
        adminUserDTO.setImageUrl( user.getImageUrl() );
        adminUserDTO.setActivated( user.isActivated() );
        adminUserDTO.setLangKey( user.getLangKey() );
        adminUserDTO.setDateDeNaissance( user.getDateDeNaissance() );
        adminUserDTO.setTelephone( user.getTelephone() );
        adminUserDTO.setNationalite( user.getNationalite() );
        adminUserDTO.setFirstConnection( user.getFirstConnection() );
        adminUserDTO.setCreatedBy( user.getCreatedBy() );
        adminUserDTO.setCreatedDate( user.getCreatedDate() );
        adminUserDTO.setLastModifiedBy( user.getLastModifiedBy() );
        adminUserDTO.setLastModifiedDate( user.getLastModifiedDate() );
        adminUserDTO.setAuthorities( authoritySetToAuthorityDTOSet( user.getAuthorities() ) );
        adminUserDTO.setFiliere( filiereToFiliereDTO( user.getFiliere() ) );
        adminUserDTO.setCampus( campusToCampusDTO( user.getCampus() ) );
        adminUserDTO.setProfil( profilToProfilDTO( user.getProfil() ) );
        adminUserDTO.setCampuses( campusSetToCampusDTOSet( user.getCampuses() ) );

        return adminUserDTO;
    }

    protected UEDTO uEToUEDTO(UE uE) {
        if ( uE == null ) {
            return null;
        }

        UEDTO uEDTO = new UEDTO();

        uEDTO.setId( uE.getId() );
        uEDTO.setNom( uE.getNom() );
        uEDTO.setCredit( uE.getCredit() );

        return uEDTO;
    }

    protected MatiereDTO matiereToMatiereDTO(Matiere matiere) {
        if ( matiere == null ) {
            return null;
        }

        MatiereDTO matiereDTO = new MatiereDTO();

        matiereDTO.setId( matiere.getId() );
        matiereDTO.setNom( matiere.getNom() );
        matiereDTO.setCredit( matiere.getCredit() );
        matiereDTO.setUe( uEToUEDTO( matiere.getUe() ) );

        return matiereDTO;
    }

    protected SemestreDTO semestreToSemestreDTO(Semestre semestre) {
        if ( semestre == null ) {
            return null;
        }

        SemestreDTO semestreDTO = new SemestreDTO();

        semestreDTO.setId( semestre.getId() );
        semestreDTO.setNom( semestre.getNom() );
        semestreDTO.setAnnee( semestre.getAnnee() );

        return semestreDTO;
    }

    protected MatiereUserDTO matiereUserToMatiereUserDTO(MatiereUser matiereUser) {
        if ( matiereUser == null ) {
            return null;
        }

        MatiereUserDTO matiereUserDTO = new MatiereUserDTO();

        matiereUserDTO.setId( matiereUser.getId() );
        matiereUserDTO.setAnneeScolaire( anneeScolaireToAnneeScolaireDTO( matiereUser.getAnneeScolaire() ) );
        matiereUserDTO.setUser( userToAdminUserDTO( matiereUser.getUser() ) );
        matiereUserDTO.setMatiere( matiereToMatiereDTO( matiereUser.getMatiere() ) );
        matiereUserDTO.setFiliere( filiereToFiliereDTO( matiereUser.getFiliere() ) );
        matiereUserDTO.setSemestre( semestreToSemestreDTO( matiereUser.getSemestre() ) );

        return matiereUserDTO;
    }

    protected CalendrierCoursDTO calendrierCoursToCalendrierCoursDTO(CalendrierCours calendrierCours) {
        if ( calendrierCours == null ) {
            return null;
        }

        CalendrierCoursDTO calendrierCoursDTO = new CalendrierCoursDTO();

        calendrierCoursDTO.setId( calendrierCours.getId() );
        calendrierCoursDTO.setLien( calendrierCours.getLien() );
        calendrierCoursDTO.setDateDebut( calendrierCours.getDateDebut() );
        calendrierCoursDTO.setDateFin( calendrierCours.getDateFin() );
        calendrierCoursDTO.setSalle( salleToSalleDTO( calendrierCours.getSalle() ) );
        calendrierCoursDTO.setMatiereUser( matiereUserToMatiereUserDTO( calendrierCours.getMatiereUser() ) );

        return calendrierCoursDTO;
    }

    protected void anneeScolaireDTOToAnneeScolaire1(AnneeScolaireDTO anneeScolaireDTO, AnneeScolaire mappingTarget) {
        if ( anneeScolaireDTO == null ) {
            return;
        }

        mappingTarget.setId( anneeScolaireDTO.getId() );
        mappingTarget.setNom( anneeScolaireDTO.getNom() );
    }

    protected void filiereDTOToFiliere1(FiliereDTO filiereDTO, Filiere mappingTarget) {
        if ( filiereDTO == null ) {
            return;
        }

        mappingTarget.setId( filiereDTO.getId() );
        mappingTarget.setNom( filiereDTO.getNom() );
    }

    protected void campusDTOToCampus1(CampusDTO campusDTO, Campus mappingTarget) {
        if ( campusDTO == null ) {
            return;
        }

        mappingTarget.setId( campusDTO.getId() );
        mappingTarget.setNom( campusDTO.getNom() );
    }

    protected void profilDTOToProfil1(ProfilDTO profilDTO, Profil mappingTarget) {
        if ( profilDTO == null ) {
            return;
        }

        mappingTarget.setId( profilDTO.getId() );
        mappingTarget.setNom( profilDTO.getNom() );
        mappingTarget.setRedirection( profilDTO.getRedirection() );
        mappingTarget.setTypeProfil( profilDTO.getTypeProfil() );
        if ( mappingTarget.getAuthorities() != null ) {
            Set<Authority> set = authorityDTOSetToAuthoritySet( profilDTO.getAuthorities() );
            if ( set != null ) {
                mappingTarget.getAuthorities().clear();
                mappingTarget.getAuthorities().addAll( set );
            }
            else {
                mappingTarget.setAuthorities( null );
            }
        }
        else {
            Set<Authority> set = authorityDTOSetToAuthoritySet( profilDTO.getAuthorities() );
            if ( set != null ) {
                mappingTarget.setAuthorities( set );
            }
        }
    }

    protected void adminUserDTOToUser1(AdminUserDTO adminUserDTO, User mappingTarget) {
        if ( adminUserDTO == null ) {
            return;
        }

        mappingTarget.setCreatedBy( adminUserDTO.getCreatedBy() );
        mappingTarget.setCreatedDate( adminUserDTO.getCreatedDate() );
        mappingTarget.setLastModifiedBy( adminUserDTO.getLastModifiedBy() );
        mappingTarget.setLastModifiedDate( adminUserDTO.getLastModifiedDate() );
        mappingTarget.setId( adminUserDTO.getId() );
        mappingTarget.setLogin( adminUserDTO.getLogin() );
        mappingTarget.setPassword( adminUserDTO.getPassword() );
        mappingTarget.setFirstName( adminUserDTO.getFirstName() );
        mappingTarget.setLastName( adminUserDTO.getLastName() );
        mappingTarget.setEmail( adminUserDTO.getEmail() );
        mappingTarget.setActivated( adminUserDTO.isActivated() );
        mappingTarget.setLangKey( adminUserDTO.getLangKey() );
        mappingTarget.setImageUrl( adminUserDTO.getImageUrl() );
        mappingTarget.setDateDeNaissance( adminUserDTO.getDateDeNaissance() );
        mappingTarget.setTelephone( adminUserDTO.getTelephone() );
        mappingTarget.setFirstConnection( adminUserDTO.getFirstConnection() );
        mappingTarget.setMatricule( adminUserDTO.getMatricule() );
        mappingTarget.setNationalite( adminUserDTO.getNationalite() );
        if ( mappingTarget.getAuthorities() != null ) {
            Set<Authority> set = authorityDTOSetToAuthoritySet( adminUserDTO.getAuthorities() );
            if ( set != null ) {
                mappingTarget.getAuthorities().clear();
                mappingTarget.getAuthorities().addAll( set );
            }
            else {
                mappingTarget.setAuthorities( null );
            }
        }
        else {
            Set<Authority> set = authorityDTOSetToAuthoritySet( adminUserDTO.getAuthorities() );
            if ( set != null ) {
                mappingTarget.setAuthorities( set );
            }
        }
        if ( adminUserDTO.getFiliere() != null ) {
            if ( mappingTarget.getFiliere() == null ) {
                mappingTarget.setFiliere( new Filiere() );
            }
            filiereDTOToFiliere1( adminUserDTO.getFiliere(), mappingTarget.getFiliere() );
        }
        else {
            mappingTarget.setFiliere( null );
        }
        if ( adminUserDTO.getCampus() != null ) {
            if ( mappingTarget.getCampus() == null ) {
                mappingTarget.setCampus( new Campus() );
            }
            campusDTOToCampus1( adminUserDTO.getCampus(), mappingTarget.getCampus() );
        }
        else {
            mappingTarget.setCampus( null );
        }
        if ( adminUserDTO.getProfil() != null ) {
            if ( mappingTarget.getProfil() == null ) {
                mappingTarget.setProfil( new Profil() );
            }
            profilDTOToProfil1( adminUserDTO.getProfil(), mappingTarget.getProfil() );
        }
        else {
            mappingTarget.setProfil( null );
        }
        if ( mappingTarget.getCampuses() != null ) {
            Set<Campus> set1 = campusDTOSetToCampusSet( adminUserDTO.getCampuses() );
            if ( set1 != null ) {
                mappingTarget.getCampuses().clear();
                mappingTarget.getCampuses().addAll( set1 );
            }
            else {
                mappingTarget.setCampuses( null );
            }
        }
        else {
            Set<Campus> set1 = campusDTOSetToCampusSet( adminUserDTO.getCampuses() );
            if ( set1 != null ) {
                mappingTarget.setCampuses( set1 );
            }
        }
    }

    protected void uEDTOToUE1(UEDTO uEDTO, UE mappingTarget) {
        if ( uEDTO == null ) {
            return;
        }

        mappingTarget.setId( uEDTO.getId() );
        mappingTarget.setNom( uEDTO.getNom() );
        mappingTarget.setCredit( uEDTO.getCredit() );
    }

    protected void matiereDTOToMatiere1(MatiereDTO matiereDTO, Matiere mappingTarget) {
        if ( matiereDTO == null ) {
            return;
        }

        mappingTarget.setId( matiereDTO.getId() );
        mappingTarget.setNom( matiereDTO.getNom() );
        mappingTarget.setCredit( matiereDTO.getCredit() );
        if ( matiereDTO.getUe() != null ) {
            if ( mappingTarget.getUe() == null ) {
                mappingTarget.ue( new UE() );
            }
            uEDTOToUE1( matiereDTO.getUe(), mappingTarget.getUe() );
        }
        else {
            mappingTarget.ue( null );
        }
    }

    protected void semestreDTOToSemestre1(SemestreDTO semestreDTO, Semestre mappingTarget) {
        if ( semestreDTO == null ) {
            return;
        }

        mappingTarget.setId( semestreDTO.getId() );
        mappingTarget.setNom( semestreDTO.getNom() );
        mappingTarget.setAnnee( semestreDTO.getAnnee() );
    }

    protected void matiereUserDTOToMatiereUser1(MatiereUserDTO matiereUserDTO, MatiereUser mappingTarget) {
        if ( matiereUserDTO == null ) {
            return;
        }

        mappingTarget.setId( matiereUserDTO.getId() );
        if ( matiereUserDTO.getAnneeScolaire() != null ) {
            if ( mappingTarget.getAnneeScolaire() == null ) {
                mappingTarget.setAnneeScolaire( new AnneeScolaire() );
            }
            anneeScolaireDTOToAnneeScolaire1( matiereUserDTO.getAnneeScolaire(), mappingTarget.getAnneeScolaire() );
        }
        else {
            mappingTarget.setAnneeScolaire( null );
        }
        if ( matiereUserDTO.getUser() != null ) {
            if ( mappingTarget.getUser() == null ) {
                mappingTarget.setUser( new User() );
            }
            adminUserDTOToUser1( matiereUserDTO.getUser(), mappingTarget.getUser() );
        }
        else {
            mappingTarget.setUser( null );
        }
        if ( matiereUserDTO.getMatiere() != null ) {
            if ( mappingTarget.getMatiere() == null ) {
                mappingTarget.setMatiere( new Matiere() );
            }
            matiereDTOToMatiere1( matiereUserDTO.getMatiere(), mappingTarget.getMatiere() );
        }
        else {
            mappingTarget.setMatiere( null );
        }
        if ( matiereUserDTO.getFiliere() != null ) {
            if ( mappingTarget.getFiliere() == null ) {
                mappingTarget.setFiliere( new Filiere() );
            }
            filiereDTOToFiliere1( matiereUserDTO.getFiliere(), mappingTarget.getFiliere() );
        }
        else {
            mappingTarget.setFiliere( null );
        }
        if ( matiereUserDTO.getSemestre() != null ) {
            if ( mappingTarget.getSemestre() == null ) {
                mappingTarget.setSemestre( new Semestre() );
            }
            semestreDTOToSemestre1( matiereUserDTO.getSemestre(), mappingTarget.getSemestre() );
        }
        else {
            mappingTarget.setSemestre( null );
        }
    }

    protected void salleDTOToSalle1(SalleDTO salleDTO, Salle mappingTarget) {
        if ( salleDTO == null ) {
            return;
        }

        mappingTarget.setId( salleDTO.getId() );
        mappingTarget.setNumero( salleDTO.getNumero() );
        if ( salleDTO.getCampus() != null ) {
            if ( mappingTarget.getCampus() == null ) {
                mappingTarget.campus( new Campus() );
            }
            campusDTOToCampus1( salleDTO.getCampus(), mappingTarget.getCampus() );
        }
        else {
            mappingTarget.campus( null );
        }
    }

    protected void calendrierCoursDTOToCalendrierCours1(CalendrierCoursDTO calendrierCoursDTO, CalendrierCours mappingTarget) {
        if ( calendrierCoursDTO == null ) {
            return;
        }

        mappingTarget.setId( calendrierCoursDTO.getId() );
        mappingTarget.setLien( calendrierCoursDTO.getLien() );
        mappingTarget.setDateDebut( calendrierCoursDTO.getDateDebut() );
        mappingTarget.setDateFin( calendrierCoursDTO.getDateFin() );
        if ( calendrierCoursDTO.getMatiereUser() != null ) {
            if ( mappingTarget.getMatiereUser() == null ) {
                mappingTarget.setMatiereUser( new MatiereUser() );
            }
            matiereUserDTOToMatiereUser1( calendrierCoursDTO.getMatiereUser(), mappingTarget.getMatiereUser() );
        }
        else {
            mappingTarget.setMatiereUser( null );
        }
        if ( calendrierCoursDTO.getSalle() != null ) {
            if ( mappingTarget.getSalle() == null ) {
                mappingTarget.setSalle( new Salle() );
            }
            salleDTOToSalle1( calendrierCoursDTO.getSalle(), mappingTarget.getSalle() );
        }
        else {
            mappingTarget.setSalle( null );
        }
    }
}
