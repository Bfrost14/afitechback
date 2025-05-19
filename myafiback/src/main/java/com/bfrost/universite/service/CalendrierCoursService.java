package com.bfrost.universite.service;

import com.bfrost.universite.service.dto.CalendrierCoursDTO;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.bfrost.universite.domain.CalendrierCours}.
 */
public interface CalendrierCoursService {
    /**
     * Save a calendrierCours.
     *
     * @param calendrierCoursDTO the entity to save.
     * @return the persisted entity.
     */
    List<CalendrierCoursDTO> save(List<CalendrierCoursDTO> calendrierCoursDTO);

    /**
     * Updates a calendrierCours.
     *
     * @param calendrierCoursDTO the entity to update.
     * @return the persisted entity.
     */
    CalendrierCoursDTO update(CalendrierCoursDTO calendrierCoursDTO);

    /**
     * Partially updates a calendrierCours.
     *
     * @param calendrierCoursDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CalendrierCoursDTO> partialUpdate(CalendrierCoursDTO calendrierCoursDTO);

    /**
     * Get all the calendrierCours.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CalendrierCoursDTO> findAll(Pageable pageable);

    /**
     * Get the "id" calendrierCours.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CalendrierCoursDTO> findOne(Long id);

    /**
     * Delete the "id" calendrierCours.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
