package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.Cours;
import com.bfrost.universite.domain.Notation;
import com.bfrost.universite.domain.User;
import com.bfrost.universite.service.dto.CoursDTO;
import com.bfrost.universite.service.dto.NotationDTO;
import com.bfrost.universite.service.dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-18T16:20:55+0000",
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
        notation.cours( coursDTOToCours( dto.getCours() ) );
        notation.etudiant( userDTOToUser( dto.getEtudiant() ) );

        return notation;
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
        if ( dto.getCours() != null ) {
            if ( entity.getCours() == null ) {
                entity.cours( new Cours() );
            }
            coursDTOToCours1( dto.getCours(), entity.getCours() );
        }
        if ( dto.getEtudiant() != null ) {
            if ( entity.getEtudiant() == null ) {
                entity.etudiant( new User() );
            }
            userDTOToUser1( dto.getEtudiant(), entity.getEtudiant() );
        }
    }

    @Override
    public NotationDTO toDto(Notation s) {
        if ( s == null ) {
            return null;
        }

        NotationDTO notationDTO = new NotationDTO();

        notationDTO.setCours( toDtoCoursId( s.getCours() ) );
        notationDTO.setEtudiant( toDtoUserId( s.getEtudiant() ) );
        notationDTO.setId( s.getId() );
        notationDTO.setNote( s.getNote() );
        notationDTO.setAppreciation( s.getAppreciation() );

        return notationDTO;
    }

    @Override
    public CoursDTO toDtoCoursId(Cours cours) {
        if ( cours == null ) {
            return null;
        }

        CoursDTO coursDTO = new CoursDTO();

        coursDTO.setId( cours.getId() );

        return coursDTO;
    }

    @Override
    public UserDTO toDtoUserId(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( user.getId() );

        return userDTO;
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

    protected Cours coursDTOToCours(CoursDTO coursDTO) {
        if ( coursDTO == null ) {
            return null;
        }

        Cours cours = new Cours();

        cours.setId( coursDTO.getId() );
        cours.setIntitule( coursDTO.getIntitule() );
        cours.professeur( userDTOToUser( coursDTO.getProfesseur() ) );

        return cours;
    }

    protected void userDTOToUser1(UserDTO userDTO, User mappingTarget) {
        if ( userDTO == null ) {
            return;
        }

        mappingTarget.setId( userDTO.getId() );
        mappingTarget.setLogin( userDTO.getLogin() );
    }

    protected void coursDTOToCours1(CoursDTO coursDTO, Cours mappingTarget) {
        if ( coursDTO == null ) {
            return;
        }

        mappingTarget.setId( coursDTO.getId() );
        mappingTarget.setIntitule( coursDTO.getIntitule() );
        if ( coursDTO.getProfesseur() != null ) {
            if ( mappingTarget.getProfesseur() == null ) {
                mappingTarget.professeur( new User() );
            }
            userDTOToUser1( coursDTO.getProfesseur(), mappingTarget.getProfesseur() );
        }
        else {
            mappingTarget.professeur( null );
        }
    }
}
