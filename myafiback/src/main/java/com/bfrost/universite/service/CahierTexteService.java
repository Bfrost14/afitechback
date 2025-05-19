package com.bfrost.universite.service;

import com.bfrost.universite.service.dto.CahierTexteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.bfrost.universite.domain.CahierTexte}.
 */
public interface CahierTexteService {
    /**
     * Save a cahierTexte.
     *
     * @param cahierTexteDTO the entity to save.
     * @return the persisted entity.
     */
    CahierTexteDTO save(CahierTexteDTO cahierTexteDTO);

    /**
     * Updates a cahierTexte.
     *
     * @param cahierTexteDTO the entity to update.
     * @return the persisted entity.
     */
    CahierTexteDTO update(CahierTexteDTO cahierTexteDTO);

    /**
     * Partially updates a cahierTexte.
     *
     * @param cahierTexteDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CahierTexteDTO> partialUpdate(CahierTexteDTO cahierTexteDTO);

    /**
     * Get all the cahierTextes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CahierTexteDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cahierTexte.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CahierTexteDTO> findOne(Long id);

    /**
     * Delete the "id" cahierTexte.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
