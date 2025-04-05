package sn.bfrost.myafiback.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.bfrost.myafiback.models.Note;
import sn.bfrost.myafiback.repository.NoteRepository;
import sn.bfrost.myafiback.service.NoteService;
import sn.bfrost.myafiback.service.dto.NoteDTO;
import sn.bfrost.myafiback.service.mapper.NoteMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Note}.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {


    private final NoteRepository noteRepository;


    private final NoteMapper noteMapper;


    @Override
    public Optional<NoteDTO> getNoteById(Long id) {
        return noteRepository.findById(id)
                .map(noteMapper::toDto);
    }

    @Override
    public Optional<NoteDTO> updateNote(NoteDTO noteDTO) {
        Note note = noteMapper.toEntity(noteDTO);
        note = noteRepository.save(note);
        return Optional.of(noteMapper.toDto(note));
    }




    @Override
    public Page<NoteDTO> getAllNote(Pageable pageable, String matiere, String nom, String prenom, String semestre, String filiere) {
        return noteRepository.findAllWithFilters(pageable, matiere, nom,prenom, semestre, filiere)
                .map(noteMapper::toDto);
    }

    @Override
    public Page<NoteDTO> getAllNoteForEtudiant(Pageable pageable, String email, String matiere, String semestre) {

            return noteRepository.findAllByEtudiant(pageable, email,matiere,semestre)
                    .map(noteMapper::toDto);
    }

    @Override
    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }

    @Override
    public NoteDTO saveNote(NoteDTO noteDTO) {
        Note note = noteMapper.toEntity(noteDTO);
        note = noteRepository.save(note);
        return noteMapper.toDto(note);
    }

    @Override
    public List<NoteDTO> saveAllNote(List<NoteDTO> notes) {
        List<NoteDTO> newNotes = new ArrayList<>();
        for(NoteDTO noteDTO: notes){
            Note note = noteMapper.toEntity(noteDTO);
            note = noteRepository.save(note);
            newNotes.add(noteMapper.toDto(note));
        }
        return newNotes;
    }


}
