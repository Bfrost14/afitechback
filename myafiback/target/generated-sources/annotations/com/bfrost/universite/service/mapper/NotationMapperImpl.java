package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.Notation;
import com.bfrost.universite.domain.User;
import com.bfrost.universite.service.dto.NotationDTO;
import com.bfrost.universite.service.dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-22T02:38:40+0000",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
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
        notation.setEtudiant( userDTOToUser( dto.getEtudiant() ) );

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
        notationDTO.setEtudiant( userToUserDTO( entity.getEtudiant() ) );

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
        if ( dto.getEtudiant() != null ) {
            if ( entity.getEtudiant() == null ) {
                entity.setEtudiant( new User() );
            }
            userDTOToUser1( dto.getEtudiant(), entity.getEtudiant() );
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
