package com.bfrost.universite.service;

import com.bfrost.universite.service.dto.MatiereDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.bfrost.universite.domain.Matiere}.
 */
public interface MatiereService {
    /**
     * Save a matiere.
     *
     * @param matiereDTO the entity to save.
     * @return the persisted entity.
     */
    List<MatiereDTO> save(List<MatiereDTO> matiereDTO);

    /**
     * Updates a matiere.
     *
     * @param matiereDTO the entity to update.
     * @return the persisted entity.
     */
    MatiereDTO update(MatiereDTO matiereDTO);

    /**
     * Partially updates a matiere.
     *
     * @param matiereDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MatiereDTO> partialUpdate(MatiereDTO matiereDTO);

    /**
     * Get all the matieres.
     *
     * @param pageable the pagination information.
     * @param nom
     * @param ue
     * @return the list of entities.
     */
    Page<MatiereDTO> findAll(Pageable pageable, String nom, String ue);

    /**
     * Get the "id" matiere.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MatiereDTO> findOne(Long id);

    /**
     * Delete the "id" matiere.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
