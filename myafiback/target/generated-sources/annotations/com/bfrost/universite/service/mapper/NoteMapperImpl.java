package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.Matiere;
import com.bfrost.universite.domain.Note;
import com.bfrost.universite.domain.Semestre;
import com.bfrost.universite.domain.UE;
import com.bfrost.universite.domain.User;
import com.bfrost.universite.service.dto.MatiereDTO;
import com.bfrost.universite.service.dto.NoteDTO;
import com.bfrost.universite.service.dto.SemestreDTO;
import com.bfrost.universite.service.dto.UEDTO;
import com.bfrost.universite.service.dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-20T19:29:16+0000",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.4 (Oracle Corporation)"
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
        note.setValeur( dto.getValeur() );
        note.setUser( userDTOToUser( dto.getUser() ) );
        note.matiere( matiereDTOToMatiere( dto.getMatiere() ) );
        note.semestre( semestreDTOToSemestre( dto.getSemestre() ) );

        return note;
    }

    @Override
    public NoteDTO toDto(Note entity) {
        if ( entity == null ) {
            return null;
        }

        NoteDTO noteDTO = new NoteDTO();

        noteDTO.setId( entity.getId() );
        noteDTO.setValeur( entity.getValeur() );
        noteDTO.setUser( userToUserDTO( entity.getUser() ) );
        noteDTO.setMatiere( matiereToMatiereDTO( entity.getMatiere() ) );
        noteDTO.setSemestre( semestreToSemestreDTO( entity.getSemestre() ) );

        return noteDTO;
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
        if ( dto.getValeur() != null ) {
            entity.setValeur( dto.getValeur() );
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
        if ( dto.getSemestre() != null ) {
            if ( entity.getSemestre() == null ) {
                entity.semestre( new Semestre() );
            }
            semestreDTOToSemestre1( dto.getSemestre(), entity.getSemestre() );
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

    protected Semestre semestreDTOToSemestre(SemestreDTO semestreDTO) {
        if ( semestreDTO == null ) {
            return null;
        }

        Semestre semestre = new Semestre();

        semestre.setId( semestreDTO.getId() );
        semestre.setNom( semestreDTO.getNom() );
        semestre.setAnnee( semestreDTO.getAnnee() );

        return semestre;
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

    protected SemestreDTO semestreToSemestreDTO(Semestre semestre) {
        if ( semestre == null ) {
            return null;
        }

        SemestreDTO semestreDTO = new SemestreDTO();

        semestreDTO.setId( semestre.getId() );
        semestreDTO.setNom( semestre.getNom() );
        semestreDTO.setAnnee( semestre.getAnnee() );

        return semestreDTO;
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

    protected void semestreDTOToSemestre1(SemestreDTO semestreDTO, Semestre mappingTarget) {
        if ( semestreDTO == null ) {
            return;
        }

        mappingTarget.setId( semestreDTO.getId() );
        mappingTarget.setNom( semestreDTO.getNom() );
        mappingTarget.setAnnee( semestreDTO.getAnnee() );
    }
}
