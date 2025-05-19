package com.bfrost.universite.service;

import com.bfrost.universite.service.dto.CoursDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.bfrost.universite.domain.Cours}.
 */
public interface CoursService {
    /**
     * Save a cours.
     *
     * @param coursDTO the entity to save.
     * @return the persisted entity.
     */
    List<CoursDTO> save(List<CoursDTO> coursDTO);

    /**
     * Updates a cours.
     *
     * @param coursDTO the entity to update.
     * @return the persisted entity.
     */
    CoursDTO update(CoursDTO coursDTO);

    /**
     * Partially updates a cours.
     *
     * @param coursDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CoursDTO> partialUpdate(CoursDTO coursDTO);

    /**
     * Get all the cours.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CoursDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cours.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CoursDTO> findOne(Long id);

    /**
     * Delete the "id" cours.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
