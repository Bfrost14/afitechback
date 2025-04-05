package sn.bfrost.myafiback.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.bfrost.myafiback.service.dto.NoteDTO;

/**
 * Service Interface for managing {@link NoteDTO}.
 */
public interface NoteService {

    Optional<NoteDTO> getNoteById(Long id);

    Optional<NoteDTO> updateNote(NoteDTO noteDTO);

    Page<NoteDTO> getAllNote(Pageable pageable, String matiere, String nom, String prenom, String semestre,String filiere);

    NoteDTO saveNote(NoteDTO noteDTO);

    List<NoteDTO> saveAllNote(List<NoteDTO> noteDTO);

    Page<NoteDTO> getAllNoteForEtudiant(Pageable pageable, String email,String matiere, String semestre);

    void deleteNote(Long id);
}
