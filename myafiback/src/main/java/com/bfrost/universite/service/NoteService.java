package com.bfrost.universite.service;

import com.bfrost.universite.service.dto.NoteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.bfrost.universite.domain.Note}.
 */
public interface NoteService {
    /**
     * Save a note.
     *
     * @param noteDTO the entity to save.
     * @return the persisted entity.
     */
    List<NoteDTO> save(List<NoteDTO> noteDTO);

    /**
     * Updates a note.
     *
     * @param noteDTO the entity to update.
     * @return the persisted entity.
     */
    NoteDTO update(NoteDTO noteDTO);

    /**
     * Partially updates a note.
     *
     * @param noteDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NoteDTO> partialUpdate(NoteDTO noteDTO);

    /**
     * Get all the notes.
     *
     * @param pageable      the pagination information.
     * @param etudiant
     * @param semestre
     * @param matiere
     * @param typeNote
     * @param idMatiereUser
     * @return the list of entities.
     */
    Page<NoteDTO> findAll(Pageable pageable, String etudiant, String semestre, String matiere, String typeNote, Long idMatiereUser);

    /**
     * Get the "id" note.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NoteDTO> findOne(Long id);

    /**
     * Delete the "id" note.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
