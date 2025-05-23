package com.bfrost.universite.service;

import com.bfrost.universite.service.dto.AbsenceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.bfrost.universite.domain.Absence}.
 */
public interface AbsenceService {
    /**
     * Save a absence.
     *
     * @param absenceDTO the entity to save.
     * @return the persisted entity.
     */
    List<AbsenceDTO> save(List<AbsenceDTO> absenceDTO);

    /**
     * Updates a absence.
     *
     * @param absenceDTO the entity to update.
     * @return the persisted entity.
     */
    AbsenceDTO update(AbsenceDTO absenceDTO);

    /**
     * Partially updates a absence.
     *
     * @param absenceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AbsenceDTO> partialUpdate(AbsenceDTO absenceDTO);

    /**
     * Get all the absences.
     *
     * @param pageable     the pagination information.
     * @param etudiant
     * @param idCalendrier
     * @param filiere
     * @return the list of entities.
     */
    Page<AbsenceDTO> findAll(Pageable pageable, String etudiant, Long idCalendrier, String filiere);

    /**
     * Get the "id" absence.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AbsenceDTO> findOne(Long id);

    /**
     * Delete the "id" absence.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
