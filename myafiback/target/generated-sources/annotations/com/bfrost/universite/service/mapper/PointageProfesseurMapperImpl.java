package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.Authority;
import com.bfrost.universite.domain.Campus;
import com.bfrost.universite.domain.Filiere;
import com.bfrost.universite.domain.PointageProfesseur;
import com.bfrost.universite.domain.Profil;
import com.bfrost.universite.domain.User;
import com.bfrost.universite.service.dto.AdminUserDTO;
import com.bfrost.universite.service.dto.AuthorityDTO;
import com.bfrost.universite.service.dto.CampusDTO;
import com.bfrost.universite.service.dto.FiliereDTO;
import com.bfrost.universite.service.dto.PointageProfesseurDTO;
import com.bfrost.universite.service.dto.ProfilDTO;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-22T02:38:42+0000",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class PointageProfesseurMapperImpl implements PointageProfesseurMapper {

    @Override
    public PointageProfesseur toEntity(PointageProfesseurDTO dto) {
        if ( dto == null ) {
            return null;
        }

        PointageProfesseur pointageProfesseur = new PointageProfesseur();

        pointageProfesseur.setId( dto.getId() );
        pointageProfesseur.setHeureArrivee( dto.getHeureArrivee() );
        pointageProfesseur.setHeureDepart( dto.getHeureDepart() );
        pointageProfesseur.professeur( adminUserDTOToUser( dto.getProfesseur() ) );

        return pointageProfesseur;
    }

    @Override
    public PointageProfesseurDTO toDto(PointageProfesseur entity) {
        if ( entity == null ) {
            return null;
        }

        PointageProfesseurDTO pointageProfesseurDTO = new PointageProfesseurDTO();

        pointageProfesseurDTO.setId( entity.getId() );
        pointageProfesseurDTO.setHeureArrivee( entity.getHeureArrivee() );
        pointageProfesseurDTO.setHeureDepart( entity.getHeureDepart() );
        pointageProfesseurDTO.setProfesseur( userToAdminUserDTO( entity.getProfesseur() ) );

        return pointageProfesseurDTO;
    }

    @Override
    public List<PointageProfesseur> toEntity(List<PointageProfesseurDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<PointageProfesseur> list = new ArrayList<PointageProfesseur>( dtoList.size() );
        for ( PointageProfesseurDTO pointageProfesseurDTO : dtoList ) {
            list.add( toEntity( pointageProfesseurDTO ) );
        }

        return list;
    }

    @Override
    public List<PointageProfesseurDTO> toDto(List<PointageProfesseur> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<PointageProfesseurDTO> list = new ArrayList<PointageProfesseurDTO>( entityList.size() );
        for ( PointageProfesseur pointageProfesseur : entityList ) {
            list.add( toDto( pointageProfesseur ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(PointageProfesseur entity, PointageProfesseurDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getHeureArrivee() != null ) {
            entity.setHeureArrivee( dto.getHeureArrivee() );
        }
        if ( dto.getHeureDepart() != null ) {
            entity.setHeureDepart( dto.getHeureDepart() );
        }
        if ( dto.getProfesseur() != null ) {
            if ( entity.getProfesseur() == null ) {
                entity.professeur( new User() );
            }
            adminUserDTOToUser1( dto.getProfesseur(), entity.getProfesseur() );
        }
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

    protected CampusDTO campusToCampusDTO(Campus campus) {
        if ( campus == null ) {
            return null;
        }

        CampusDTO campusDTO = new CampusDTO();

        campusDTO.setId( campus.getId() );
        campusDTO.setNom( campus.getNom() );

        return campusDTO;
    }

    protected ProfilDTO profilToProfilDTO(Profil profil) {
        if ( profil == null ) {
            return null;
        }

        ProfilDTO profilDTO = new ProfilDTO();

        profilDTO.setId( profil.getId() );
        profilDTO.setNom( profil.getNom() );
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
}
