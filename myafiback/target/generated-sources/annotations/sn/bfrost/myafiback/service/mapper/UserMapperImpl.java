package sn.bfrost.myafiback.service.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import sn.bfrost.myafiback.models.User;
import sn.bfrost.myafiback.service.dto.UserDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-05T02:57:19+0000",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserDTO dto) {
        if ( dto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( dto.getId() );
        user.nom( dto.getNom() );
        user.prenom( dto.getPrenom() );
        user.password( dto.getPassword() );
        user.matricule( dto.getMatricule() );
        user.email( dto.getEmail() );
        user.filiere( dto.getFiliere() );
        user.role( dto.getRole() );
        user.dateDeNaissance( dto.getDateDeNaissance() );
        user.telephone( dto.getTelephone() );

        return user.build();
    }

    @Override
    public UserDTO toDto(User entity) {
        if ( entity == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( entity.getId() );
        userDTO.setNom( entity.getNom() );
        userDTO.setPrenom( entity.getPrenom() );
        userDTO.setMatricule( entity.getMatricule() );
        userDTO.setEmail( entity.getEmail() );
        userDTO.setPassword( entity.getPassword() );
        userDTO.setRole( entity.getRole() );
        userDTO.setDateDeNaissance( entity.getDateDeNaissance() );
        userDTO.setTelephone( entity.getTelephone() );
        userDTO.setFiliere( entity.getFiliere() );

        return userDTO;
    }

    @Override
    public List<User> toEntity(List<UserDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( dtoList.size() );
        for ( UserDTO userDTO : dtoList ) {
            list.add( toEntity( userDTO ) );
        }

        return list;
    }

    @Override
    public List<UserDTO> toDto(List<User> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<UserDTO> list = new ArrayList<UserDTO>( entityList.size() );
        for ( User user : entityList ) {
            list.add( toDto( user ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(User entity, UserDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getNom() != null ) {
            entity.setNom( dto.getNom() );
        }
        if ( dto.getPrenom() != null ) {
            entity.setPrenom( dto.getPrenom() );
        }
        if ( dto.getPassword() != null ) {
            entity.setPassword( dto.getPassword() );
        }
        if ( dto.getMatricule() != null ) {
            entity.setMatricule( dto.getMatricule() );
        }
        if ( dto.getEmail() != null ) {
            entity.setEmail( dto.getEmail() );
        }
        if ( dto.getFiliere() != null ) {
            entity.setFiliere( dto.getFiliere() );
        }
        if ( dto.getRole() != null ) {
            entity.setRole( dto.getRole() );
        }
        if ( dto.getDateDeNaissance() != null ) {
            entity.setDateDeNaissance( dto.getDateDeNaissance() );
        }
        if ( dto.getTelephone() != null ) {
            entity.setTelephone( dto.getTelephone() );
        }
    }
}
