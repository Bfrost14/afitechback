package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.Cours;
import com.bfrost.universite.domain.User;
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
public class CoursMapperImpl implements CoursMapper {

    @Override
    public Cours toEntity(CoursDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Cours cours = new Cours();

        cours.setId( dto.getId() );
        cours.setIntitule( dto.getIntitule() );
        cours.professeur( userDTOToUser( dto.getProfesseur() ) );

        return cours;
    }

    @Override
    public List<Cours> toEntity(List<CoursDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Cours> list = new ArrayList<Cours>( dtoList.size() );
        for ( CoursDTO coursDTO : dtoList ) {
            list.add( toEntity( coursDTO ) );
        }

        return list;
    }

    @Override
    public List<CoursDTO> toDto(List<Cours> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CoursDTO> list = new ArrayList<CoursDTO>( entityList.size() );
        for ( Cours cours : entityList ) {
            list.add( toDto( cours ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Cours entity, CoursDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getIntitule() != null ) {
            entity.setIntitule( dto.getIntitule() );
        }
        if ( dto.getProfesseur() != null ) {
            if ( entity.getProfesseur() == null ) {
                entity.professeur( new User() );
            }
            userDTOToUser1( dto.getProfesseur(), entity.getProfesseur() );
        }
    }

    @Override
    public CoursDTO toDto(Cours s) {
        if ( s == null ) {
            return null;
        }

        CoursDTO coursDTO = new CoursDTO();

        coursDTO.setProfesseur( toDtoUserId( s.getProfesseur() ) );
        coursDTO.setId( s.getId() );
        coursDTO.setIntitule( s.getIntitule() );

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

    protected void userDTOToUser1(UserDTO userDTO, User mappingTarget) {
        if ( userDTO == null ) {
            return;
        }

        mappingTarget.setId( userDTO.getId() );
        mappingTarget.setLogin( userDTO.getLogin() );
    }
}
