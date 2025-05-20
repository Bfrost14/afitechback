package com.bfrost.universite.service;

import com.bfrost.universite.service.dto.SemestreDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.bfrost.universite.domain.Semestre}.
 */
public interface SemestreService {
    /**
     * Save a semestre.
     *
     * @param semestreDTO the entity to save.
     * @return the persisted entity.
     */
    SemestreDTO save(SemestreDTO semestreDTO);

    /**
     * Updates a semestre.
     *
     * @param semestreDTO the entity to update.
     * @return the persisted entity.
     */
    SemestreDTO update(SemestreDTO semestreDTO);

    /**
     * Partially updates a semestre.
     *
     * @param semestreDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SemestreDTO> partialUpdate(SemestreDTO semestreDTO);

    /**
     * Get all the semestres.
     *
     * @param pageable the pagination information.
     * @param nom
     * @return the list of entities.
     */
    Page<SemestreDTO> findAll(Pageable pageable, String nom);

    /**
     * Get the "id" semestre.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SemestreDTO> findOne(Long id);

    /**
     * Delete the "id" semestre.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
