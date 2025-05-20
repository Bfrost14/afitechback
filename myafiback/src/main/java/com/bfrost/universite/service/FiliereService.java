package com.bfrost.universite.service;

import com.bfrost.universite.service.dto.FiliereDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.bfrost.universite.domain.Filiere}.
 */
public interface FiliereService {
    /**
     * Save a filiere.
     *
     * @param filiereDTO the entity to save.
     * @return the persisted entity.
     */
    FiliereDTO save(FiliereDTO filiereDTO);

    /**
     * Updates a filiere.
     *
     * @param filiereDTO the entity to update.
     * @return the persisted entity.
     */
    FiliereDTO update(FiliereDTO filiereDTO);

    /**
     * Partially updates a filiere.
     *
     * @param filiereDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FiliereDTO> partialUpdate(FiliereDTO filiereDTO);

    /**
     * Get all the filieres.
     *
     * @param pageable the pagination information.
     * @param nom
     * @return the list of entities.
     */
    Page<FiliereDTO> findAll(Pageable pageable, String nom);

    /**
     * Get the "id" filiere.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FiliereDTO> findOne(Long id);

    /**
     * Delete the "id" filiere.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
