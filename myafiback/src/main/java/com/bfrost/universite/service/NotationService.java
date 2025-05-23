package com.bfrost.universite.service;

import com.bfrost.universite.service.dto.NotationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.bfrost.universite.domain.Notation}.
 */
public interface NotationService {
    /**
     * Save a notation.
     *
     * @param notationDTO the entity to save.
     * @return the persisted entity.
     */
    NotationDTO save(NotationDTO notationDTO);

    /**
     * Updates a notation.
     *
     * @param notationDTO the entity to update.
     * @return the persisted entity.
     */
    NotationDTO update(NotationDTO notationDTO);

    /**
     * Partially updates a notation.
     *
     * @param notationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NotationDTO> partialUpdate(NotationDTO notationDTO);

    /**
     * Get all the notations.
     *
     * @param pageable     the pagination information.
     * @param etudiant
     * @param idCalendrier
     * @param matiere
     * @return the list of entities.
     */
    Page<NotationDTO> findAll(Pageable pageable, String etudiant, Long idCalendrier, String matiere);

    /**
     * Get the "id" notation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NotationDTO> findOne(Long id);

    /**
     * Delete the "id" notation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
