package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.Filiere;
import com.bfrost.universite.domain.Matiere;
import com.bfrost.universite.domain.MatiereUser;
import com.bfrost.universite.domain.UE;
import com.bfrost.universite.domain.User;
import com.bfrost.universite.service.dto.FiliereDTO;
import com.bfrost.universite.service.dto.MatiereDTO;
import com.bfrost.universite.service.dto.MatiereUserDTO;
import com.bfrost.universite.service.dto.UEDTO;
import com.bfrost.universite.service.dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-20T19:29:14+0000",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.4 (Oracle Corporation)"
)
@Component
public class MatiereUserMapperImpl implements MatiereUserMapper {

    @Override
    public MatiereUser toEntity(MatiereUserDTO dto) {
        if ( dto == null ) {
            return null;
        }

        MatiereUser matiereUser = new MatiereUser();

        matiereUser.setId( dto.getId() );
        matiereUser.setAnneeScolaire( dto.getAnneeScolaire() );
        matiereUser.setUser( userDTOToUser( dto.getUser() ) );
        matiereUser.matiere( matiereDTOToMatiere( dto.getMatiere() ) );
        matiereUser.filiere( filiereDTOToFiliere( dto.getFiliere() ) );

        return matiereUser;
    }

    @Override
    public MatiereUserDTO toDto(MatiereUser entity) {
        if ( entity == null ) {
            return null;
        }

        MatiereUserDTO matiereUserDTO = new MatiereUserDTO();

        matiereUserDTO.setId( entity.getId() );
        matiereUserDTO.setAnneeScolaire( entity.getAnneeScolaire() );
        matiereUserDTO.setUser( userToUserDTO( entity.getUser() ) );
        matiereUserDTO.setMatiere( matiereToMatiereDTO( entity.getMatiere() ) );
        matiereUserDTO.setFiliere( filiereToFiliereDTO( entity.getFiliere() ) );

        return matiereUserDTO;
    }

    @Override
    public List<MatiereUser> toEntity(List<MatiereUserDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<MatiereUser> list = new ArrayList<MatiereUser>( dtoList.size() );
        for ( MatiereUserDTO matiereUserDTO : dtoList ) {
            list.add( toEntity( matiereUserDTO ) );
        }

        return list;
    }

    @Override
    public List<MatiereUserDTO> toDto(List<MatiereUser> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<MatiereUserDTO> list = new ArrayList<MatiereUserDTO>( entityList.size() );
        for ( MatiereUser matiereUser : entityList ) {
            list.add( toDto( matiereUser ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(MatiereUser entity, MatiereUserDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getAnneeScolaire() != null ) {
            entity.setAnneeScolaire( dto.getAnneeScolaire() );
        }
        if ( dto.getUser() != null ) {
            if ( entity.getUser() == null ) {
                entity.setUser( new User() );
            }
            userDTOToUser1( dto.getUser(), entity.getUser() );
        }
        if ( dto.getMatiere() != null ) {
            if ( entity.getMatiere() == null ) {
                entity.matiere( new Matiere() );
            }
            matiereDTOToMatiere1( dto.getMatiere(), entity.getMatiere() );
        }
        if ( dto.getFiliere() != null ) {
            if ( entity.getFiliere() == null ) {
                entity.filiere( new Filiere() );
            }
            filiereDTOToFiliere1( dto.getFiliere(), entity.getFiliere() );
        }
    }

    protected User userDTOToUser(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User user = new User();

        user.setId( userDTO.getId() );
        user.setLogin( userDTO.getLogin() );

        return user;
    }

    protected UE uEDTOToUE(UEDTO uEDTO) {
        if ( uEDTO == null ) {
            return null;
        }

        UE uE = new UE();

        uE.setId( uEDTO.getId() );
        uE.setNom( uEDTO.getNom() );

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

    protected Filiere filiereDTOToFiliere(FiliereDTO filiereDTO) {
        if ( filiereDTO == null ) {
            return null;
        }

        Filiere filiere = new Filiere();

        filiere.setId( filiereDTO.getId() );
        filiere.setNom( filiereDTO.getNom() );

        return filiere;
    }

    protected UserDTO userToUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( user.getId() );
        userDTO.setLogin( user.getLogin() );

        return userDTO;
    }

    protected UEDTO uEToUEDTO(UE uE) {
        if ( uE == null ) {
            return null;
        }

        UEDTO uEDTO = new UEDTO();

        uEDTO.setId( uE.getId() );
        uEDTO.setNom( uE.getNom() );

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

    protected FiliereDTO filiereToFiliereDTO(Filiere filiere) {
        if ( filiere == null ) {
            return null;
        }

        FiliereDTO filiereDTO = new FiliereDTO();

        filiereDTO.setId( filiere.getId() );
        filiereDTO.setNom( filiere.getNom() );

        return filiereDTO;
    }

    protected void userDTOToUser1(UserDTO userDTO, User mappingTarget) {
        if ( userDTO == null ) {
            return;
        }

        mappingTarget.setId( userDTO.getId() );
        mappingTarget.setLogin( userDTO.getLogin() );
    }

    protected void uEDTOToUE1(UEDTO uEDTO, UE mappingTarget) {
        if ( uEDTO == null ) {
            return;
        }

        mappingTarget.setId( uEDTO.getId() );
        mappingTarget.setNom( uEDTO.getNom() );
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

    protected void filiereDTOToFiliere1(FiliereDTO filiereDTO, Filiere mappingTarget) {
        if ( filiereDTO == null ) {
            return;
        }

        mappingTarget.setId( filiereDTO.getId() );
        mappingTarget.setNom( filiereDTO.getNom() );
    }
}
