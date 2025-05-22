package com.bfrost.universite.service;

import com.bfrost.universite.service.dto.PointageProfesseurDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.bfrost.universite.domain.PointageProfesseur}.
 */
public interface PointageProfesseurService {
    /**
     * Save a pointageProfesseur.
     *
     * @param pointageProfesseurDTO the entity to save.
     * @return the persisted entity.
     */
    PointageProfesseurDTO save(PointageProfesseurDTO pointageProfesseurDTO);

    /**
     * Updates a pointageProfesseur.
     *
     * @param pointageProfesseurDTO the entity to update.
     * @return the persisted entity.
     */
    PointageProfesseurDTO update(PointageProfesseurDTO pointageProfesseurDTO);

    /**
     * Partially updates a pointageProfesseur.
     *
     * @param pointageProfesseurDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PointageProfesseurDTO> partialUpdate(PointageProfesseurDTO pointageProfesseurDTO);

    /**
     * Get all the pointageProfesseurs.
     *
     * @param pageable   the pagination information.
     * @param dateDebut
     * @param dateFin
     * @param professeur
     * @return the list of entities.
     */
    Page<PointageProfesseurDTO> findAll(Pageable pageable, ZonedDateTime dateDebut, ZonedDateTime dateFin, String professeur);

    /**
     * Get the "id" pointageProfesseur.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PointageProfesseurDTO> findOne(Long id);

    /**
     * Delete the "id" pointageProfesseur.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
