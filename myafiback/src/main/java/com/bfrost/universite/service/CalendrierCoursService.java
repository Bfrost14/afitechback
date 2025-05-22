package com.bfrost.universite.service;

import com.bfrost.universite.service.dto.CalendrierCoursDTO;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

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
     * @param pageable   the pagination information.
     * @param dateDebut
     * @param dateFin
     * @param matiere
     * @param filiere
     * @param salle
     * @param professeur
     * @param campus
     * @return the list of entities.
     */
    Page<CalendrierCoursDTO> findAll(Pageable pageable,
                                     ZonedDateTime dateDebut,
                                     ZonedDateTime dateFin,
                                     String matiere,
                                     String filiere,
                                     String salle,
                                     String professeur,
                                     String campus);

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
