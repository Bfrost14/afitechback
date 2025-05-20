package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.Absence;
import com.bfrost.universite.domain.Cours;
import com.bfrost.universite.domain.User;
import com.bfrost.universite.service.dto.AbsenceDTO;
import com.bfrost.universite.service.dto.CoursDTO;
import com.bfrost.universite.service.dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-20T19:30:26+0000",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class AbsenceMapperImpl implements AbsenceMapper {

    @Override
    public Absence toEntity(AbsenceDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Absence absence = new Absence();

        absence.setId( dto.getId() );
        absence.setDate( dto.getDate() );
        absence.setJustifie( dto.getJustifie() );
        absence.cours( coursDTOToCours( dto.getCours() ) );
        absence.setUser( userDTOToUser( dto.getUser() ) );

        return absence;
    }

    @Override
    public AbsenceDTO toDto(Absence entity) {
        if ( entity == null ) {
            return null;
        }

        AbsenceDTO absenceDTO = new AbsenceDTO();

        absenceDTO.setId( entity.getId() );
        absenceDTO.setDate( entity.getDate() );
        absenceDTO.setJustifie( entity.getJustifie() );
        absenceDTO.setCours( coursToCoursDTO( entity.getCours() ) );
        absenceDTO.setUser( userToUserDTO( entity.getUser() ) );

        return absenceDTO;
    }

    @Override
    public List<Absence> toEntity(List<AbsenceDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Absence> list = new ArrayList<Absence>( dtoList.size() );
        for ( AbsenceDTO absenceDTO : dtoList ) {
            list.add( toEntity( absenceDTO ) );
        }

        return list;
    }

    @Override
    public List<AbsenceDTO> toDto(List<Absence> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<AbsenceDTO> list = new ArrayList<AbsenceDTO>( entityList.size() );
        for ( Absence absence : entityList ) {
            list.add( toDto( absence ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Absence entity, AbsenceDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getDate() != null ) {
            entity.setDate( dto.getDate() );
        }
        if ( dto.getJustifie() != null ) {
            entity.setJustifie( dto.getJustifie() );
        }
        if ( dto.getCours() != null ) {
            if ( entity.getCours() == null ) {
                entity.cours( new Cours() );
            }
            coursDTOToCours1( dto.getCours(), entity.getCours() );
        }
        if ( dto.getUser() != null ) {
            if ( entity.getUser() == null ) {
                entity.setUser( new User() );
            }
            userDTOToUser1( dto.getUser(), entity.getUser() );
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

    protected UserDTO userToUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( user.getId() );
        userDTO.setLogin( user.getLogin() );

        return userDTO;
    }

    protected CoursDTO coursToCoursDTO(Cours cours) {
        if ( cours == null ) {
            return null;
        }

        CoursDTO coursDTO = new CoursDTO();

        coursDTO.setId( cours.getId() );
        coursDTO.setIntitule( cours.getIntitule() );
        coursDTO.setProfesseur( userToUserDTO( cours.getProfesseur() ) );

        return coursDTO;
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
