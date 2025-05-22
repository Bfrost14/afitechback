package com.bfrost.universite.service;

import com.bfrost.universite.service.dto.AnneeScolaireDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.bfrost.universite.domain.AnneeScolaire}.
 */
public interface AnneeScolaireService {
    /**
     * Save a anneeScolaire.
     *
     * @param anneeScolaireDTO the entity to save.
     * @return the persisted entity.
     */
    AnneeScolaireDTO save(AnneeScolaireDTO anneeScolaireDTO);

    /**
     * Updates a anneeScolaire.
     *
     * @param anneeScolaireDTO the entity to update.
     * @return the persisted entity.
     */
    AnneeScolaireDTO update(AnneeScolaireDTO anneeScolaireDTO);

    /**
     * Partially updates a anneeScolaire.
     *
     * @param anneeScolaireDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AnneeScolaireDTO> partialUpdate(AnneeScolaireDTO anneeScolaireDTO);

    /**
     * Get all the anneeScolaires.
     *
     * @param pageable the pagination information.
     * @param nom
     * @return the list of entities.
     */
    Page<AnneeScolaireDTO> findAll(Pageable pageable, String nom);

    /**
     * Get the "id" anneeScolaire.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnneeScolaireDTO> findOne(Long id);

    /**
     * Delete the "id" anneeScolaire.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
