package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.PointageProfesseur;
import com.bfrost.universite.domain.User;
import com.bfrost.universite.service.dto.PointageProfesseurDTO;
import com.bfrost.universite.service.dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-20T19:29:15+0000",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.4 (Oracle Corporation)"
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
        pointageProfesseur.professeur( userDTOToUser( dto.getProfesseur() ) );

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
        pointageProfesseurDTO.setProfesseur( userToUserDTO( entity.getProfesseur() ) );

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
            userDTOToUser1( dto.getProfesseur(), entity.getProfesseur() );
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

    protected UserDTO userToUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( user.getId() );
        userDTO.setLogin( user.getLogin() );

        return userDTO;
    }

    protected void userDTOToUser1(UserDTO userDTO, User mappingTarget) {
        if ( userDTO == null ) {
            return;
        }

        mappingTarget.setId( userDTO.getId() );
        mappingTarget.setLogin( userDTO.getLogin() );
    }
}
