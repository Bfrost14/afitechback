package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.CahierTexte;
import com.bfrost.universite.domain.Cours;
import com.bfrost.universite.domain.User;
import com.bfrost.universite.service.dto.CahierTexteDTO;
import com.bfrost.universite.service.dto.CoursDTO;
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
public class CahierTexteMapperImpl implements CahierTexteMapper {

    @Override
    public CahierTexte toEntity(CahierTexteDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CahierTexte cahierTexte = new CahierTexte();

        cahierTexte.setId( dto.getId() );
        cahierTexte.setDate( dto.getDate() );
        cahierTexte.setContenu( dto.getContenu() );
        cahierTexte.cours( coursDTOToCours( dto.getCours() ) );
        cahierTexte.setUser( userDTOToUser( dto.getUser() ) );

        return cahierTexte;
    }

    @Override
    public List<CahierTexte> toEntity(List<CahierTexteDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<CahierTexte> list = new ArrayList<CahierTexte>( dtoList.size() );
        for ( CahierTexteDTO cahierTexteDTO : dtoList ) {
            list.add( toEntity( cahierTexteDTO ) );
        }

        return list;
    }

    @Override
    public List<CahierTexteDTO> toDto(List<CahierTexte> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CahierTexteDTO> list = new ArrayList<CahierTexteDTO>( entityList.size() );
        for ( CahierTexte cahierTexte : entityList ) {
            list.add( toDto( cahierTexte ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(CahierTexte entity, CahierTexteDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getDate() != null ) {
            entity.setDate( dto.getDate() );
        }
        if ( dto.getContenu() != null ) {
            entity.setContenu( dto.getContenu() );
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

    @Override
    public CahierTexteDTO toDto(CahierTexte s) {
        if ( s == null ) {
            return null;
        }

        CahierTexteDTO cahierTexteDTO = new CahierTexteDTO();

        cahierTexteDTO.setCours( toDtoCoursId( s.getCours() ) );
        cahierTexteDTO.setUser( toDtoUserId( s.getUser() ) );
        cahierTexteDTO.setId( s.getId() );
        cahierTexteDTO.setDate( s.getDate() );
        cahierTexteDTO.setContenu( s.getContenu() );

        return cahierTexteDTO;
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
