package sn.bfrost.myafiback.service.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import sn.bfrost.myafiback.models.Note;
import sn.bfrost.myafiback.models.User;
import sn.bfrost.myafiback.service.dto.NoteDTO;
import sn.bfrost.myafiback.service.dto.UserDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-05T02:57:19+0000",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class NoteMapperImpl implements NoteMapper {

    @Override
    public Note toEntity(NoteDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Note note = new Note();

        note.setId( dto.getId() );
        note.setSemestre( dto.getSemestre() );
        note.setMatiere( dto.getMatiere() );
        note.setCredit( dto.getCredit() );
        note.setValeur( dto.getValeur() );
        note.setUser( userDTOToUser( dto.getUser() ) );

        return note;
    }

    @Override
    public NoteDTO toDto(Note entity) {
        if ( entity == null ) {
            return null;
        }

        NoteDTO.NoteDTOBuilder noteDTO = NoteDTO.builder();

        noteDTO.id( entity.getId() );
        noteDTO.semestre( entity.getSemestre() );
        noteDTO.matiere( entity.getMatiere() );
        noteDTO.credit( entity.getCredit() );
        noteDTO.valeur( entity.getValeur() );
        noteDTO.user( userToUserDTO( entity.getUser() ) );

        return noteDTO.build();
    }

    @Override
    public List<Note> toEntity(List<NoteDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Note> list = new ArrayList<Note>( dtoList.size() );
        for ( NoteDTO noteDTO : dtoList ) {
            list.add( toEntity( noteDTO ) );
        }

        return list;
    }

    @Override
    public List<NoteDTO> toDto(List<Note> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<NoteDTO> list = new ArrayList<NoteDTO>( entityList.size() );
        for ( Note note : entityList ) {
            list.add( toDto( note ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Note entity, NoteDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getSemestre() != null ) {
            entity.setSemestre( dto.getSemestre() );
        }
        if ( dto.getMatiere() != null ) {
            entity.setMatiere( dto.getMatiere() );
        }
        entity.setCredit( dto.getCredit() );
        if ( dto.getValeur() != null ) {
            entity.setValeur( dto.getValeur() );
        }
        if ( dto.getUser() != null ) {
            if ( entity.getUser() == null ) {
                entity.setUser( User.builder().build() );
            }
            userDTOToUser1( dto.getUser(), entity.getUser() );
        }
    }

    protected User userDTOToUser(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( userDTO.getId() );
        user.nom( userDTO.getNom() );
        user.prenom( userDTO.getPrenom() );
        user.password( userDTO.getPassword() );
        user.matricule( userDTO.getMatricule() );
        user.email( userDTO.getEmail() );
        user.filiere( userDTO.getFiliere() );
        user.role( userDTO.getRole() );
        user.dateDeNaissance( userDTO.getDateDeNaissance() );
        user.telephone( userDTO.getTelephone() );

        return user.build();
    }

    protected UserDTO userToUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( user.getId() );
        userDTO.setNom( user.getNom() );
        userDTO.setPrenom( user.getPrenom() );
        userDTO.setMatricule( user.getMatricule() );
        userDTO.setEmail( user.getEmail() );
        userDTO.setPassword( user.getPassword() );
        userDTO.setRole( user.getRole() );
        userDTO.setDateDeNaissance( user.getDateDeNaissance() );
        userDTO.setTelephone( user.getTelephone() );
        userDTO.setFiliere( user.getFiliere() );

        return userDTO;
    }

    protected void userDTOToUser1(UserDTO userDTO, User mappingTarget) {
        if ( userDTO == null ) {
            return;
        }

        mappingTarget.setId( userDTO.getId() );
        mappingTarget.setNom( userDTO.getNom() );
        mappingTarget.setPrenom( userDTO.getPrenom() );
        mappingTarget.setPassword( userDTO.getPassword() );
        mappingTarget.setMatricule( userDTO.getMatricule() );
        mappingTarget.setEmail( userDTO.getEmail() );
        mappingTarget.setFiliere( userDTO.getFiliere() );
        mappingTarget.setRole( userDTO.getRole() );
        mappingTarget.setDateDeNaissance( userDTO.getDateDeNaissance() );
        mappingTarget.setTelephone( userDTO.getTelephone() );
    }
}
