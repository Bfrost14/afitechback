package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.Authority;
import com.bfrost.universite.domain.Campus;
import com.bfrost.universite.domain.Filiere;
import com.bfrost.universite.domain.Profil;
import com.bfrost.universite.domain.User;
import com.bfrost.universite.service.dto.AdminUserDTO;
import com.bfrost.universite.service.dto.AuthorityDTO;
import com.bfrost.universite.service.dto.CampusDTO;
import com.bfrost.universite.service.dto.FiliereDTO;
import com.bfrost.universite.service.dto.ProfilDTO;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-24T17:00:08+0000",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.4 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(AdminUserDTO dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setCreatedBy( dto.getCreatedBy() );
        user.setCreatedDate( dto.getCreatedDate() );
        user.setLastModifiedBy( dto.getLastModifiedBy() );
        user.setLastModifiedDate( dto.getLastModifiedDate() );
        user.setId( dto.getId() );
        user.setLogin( dto.getLogin() );
        user.setPassword( dto.getPassword() );
        user.setFirstName( dto.getFirstName() );
        user.setLastName( dto.getLastName() );
        user.setEmail( dto.getEmail() );
        user.setActivated( dto.isActivated() );
        user.setLangKey( dto.getLangKey() );
        user.setImageUrl( dto.getImageUrl() );
        user.setDateDeNaissance( dto.getDateDeNaissance() );
        user.setTelephone( dto.getTelephone() );
        user.setFirstConnection( dto.getFirstConnection() );
        user.setMatricule( dto.getMatricule() );
        user.setNationalite( dto.getNationalite() );
        user.setAuthorities( authorityDTOSetToAuthoritySet( dto.getAuthorities() ) );
        user.setFiliere( filiereDTOToFiliere( dto.getFiliere() ) );
        user.setCampus( campusDTOToCampus( dto.getCampus() ) );
        user.setProfil( profilDTOToProfil( dto.getProfil() ) );
        user.setCampuses( campusDTOSetToCampusSet( dto.getCampuses() ) );

        return user;
    }

    @Override
    public AdminUserDTO toDto(User entity) {
        if ( entity == null ) {
            return null;
        }

        AdminUserDTO adminUserDTO = new AdminUserDTO();

        adminUserDTO.setId( entity.getId() );
        adminUserDTO.setLogin( entity.getLogin() );
        adminUserDTO.setFirstName( entity.getFirstName() );
        adminUserDTO.setLastName( entity.getLastName() );
        adminUserDTO.setEmail( entity.getEmail() );
        adminUserDTO.setMatricule( entity.getMatricule() );
        adminUserDTO.setPassword( entity.getPassword() );
        adminUserDTO.setImageUrl( entity.getImageUrl() );
        adminUserDTO.setActivated( entity.isActivated() );
        adminUserDTO.setLangKey( entity.getLangKey() );
        adminUserDTO.setDateDeNaissance( entity.getDateDeNaissance() );
        adminUserDTO.setTelephone( entity.getTelephone() );
        adminUserDTO.setNationalite( entity.getNationalite() );
        adminUserDTO.setFirstConnection( entity.getFirstConnection() );
        adminUserDTO.setCreatedBy( entity.getCreatedBy() );
        adminUserDTO.setCreatedDate( entity.getCreatedDate() );
        adminUserDTO.setLastModifiedBy( entity.getLastModifiedBy() );
        adminUserDTO.setLastModifiedDate( entity.getLastModifiedDate() );
        adminUserDTO.setAuthorities( authoritySetToAuthorityDTOSet( entity.getAuthorities() ) );
        adminUserDTO.setFiliere( filiereToFiliereDTO( entity.getFiliere() ) );
        adminUserDTO.setCampus( campusToCampusDTO( entity.getCampus() ) );
        adminUserDTO.setProfil( profilToProfilDTO( entity.getProfil() ) );
        adminUserDTO.setCampuses( campusSetToCampusDTOSet( entity.getCampuses() ) );

        return adminUserDTO;
    }

    @Override
    public List<User> toEntity(List<AdminUserDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( dtoList.size() );
        for ( AdminUserDTO adminUserDTO : dtoList ) {
            list.add( toEntity( adminUserDTO ) );
        }

        return list;
    }

    @Override
    public List<AdminUserDTO> toDto(List<User> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<AdminUserDTO> list = new ArrayList<AdminUserDTO>( entityList.size() );
        for ( User user : entityList ) {
            list.add( toDto( user ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(User entity, AdminUserDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getCreatedBy() != null ) {
            entity.setCreatedBy( dto.getCreatedBy() );
        }
        if ( dto.getCreatedDate() != null ) {
            entity.setCreatedDate( dto.getCreatedDate() );
        }
        if ( dto.getLastModifiedBy() != null ) {
            entity.setLastModifiedBy( dto.getLastModifiedBy() );
        }
        if ( dto.getLastModifiedDate() != null ) {
            entity.setLastModifiedDate( dto.getLastModifiedDate() );
        }
        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getLogin() != null ) {
            entity.setLogin( dto.getLogin() );
        }
        if ( dto.getPassword() != null ) {
            entity.setPassword( dto.getPassword() );
        }
        if ( dto.getFirstName() != null ) {
            entity.setFirstName( dto.getFirstName() );
        }
        if ( dto.getLastName() != null ) {
            entity.setLastName( dto.getLastName() );
        }
        if ( dto.getEmail() != null ) {
            entity.setEmail( dto.getEmail() );
        }
        entity.setActivated( dto.isActivated() );
        if ( dto.getLangKey() != null ) {
            entity.setLangKey( dto.getLangKey() );
        }
        if ( dto.getImageUrl() != null ) {
            entity.setImageUrl( dto.getImageUrl() );
        }
        if ( dto.getDateDeNaissance() != null ) {
            entity.setDateDeNaissance( dto.getDateDeNaissance() );
        }
        if ( dto.getTelephone() != null ) {
            entity.setTelephone( dto.getTelephone() );
        }
        if ( dto.getFirstConnection() != null ) {
            entity.setFirstConnection( dto.getFirstConnection() );
        }
        if ( dto.getMatricule() != null ) {
            entity.setMatricule( dto.getMatricule() );
        }
        if ( dto.getNationalite() != null ) {
            entity.setNationalite( dto.getNationalite() );
        }
        if ( entity.getAuthorities() != null ) {
            Set<Authority> set = authorityDTOSetToAuthoritySet( dto.getAuthorities() );
            if ( set != null ) {
                entity.getAuthorities().clear();
                entity.getAuthorities().addAll( set );
            }
        }
        else {
            Set<Authority> set = authorityDTOSetToAuthoritySet( dto.getAuthorities() );
            if ( set != null ) {
                entity.setAuthorities( set );
            }
        }
        if ( dto.getFiliere() != null ) {
            if ( entity.getFiliere() == null ) {
                entity.setFiliere( new Filiere() );
            }
            filiereDTOToFiliere1( dto.getFiliere(), entity.getFiliere() );
        }
        if ( dto.getCampus() != null ) {
            if ( entity.getCampus() == null ) {
                entity.setCampus( new Campus() );
            }
            campusDTOToCampus1( dto.getCampus(), entity.getCampus() );
        }
        if ( dto.getProfil() != null ) {
            if ( entity.getProfil() == null ) {
                entity.setProfil( new Profil() );
            }
            profilDTOToProfil1( dto.getProfil(), entity.getProfil() );
        }
        if ( entity.getCampuses() != null ) {
            Set<Campus> set1 = campusDTOSetToCampusSet( dto.getCampuses() );
            if ( set1 != null ) {
                entity.getCampuses().clear();
                entity.getCampuses().addAll( set1 );
            }
        }
        else {
            Set<Campus> set1 = campusDTOSetToCampusSet( dto.getCampuses() );
            if ( set1 != null ) {
                entity.setCampuses( set1 );
            }
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
}
