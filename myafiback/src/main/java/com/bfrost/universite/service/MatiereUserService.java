package com.bfrost.universite.service;

import com.bfrost.universite.service.dto.MatiereUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.bfrost.universite.domain.MatiereUser}.
 */
public interface MatiereUserService {
    /**
     * Save a matiereUser.
     *
     * @param matiereUserDTO the entity to save.
     * @return the persisted entity.
     */
    List<MatiereUserDTO> save(List<MatiereUserDTO> matiereUserDTO);

    /**
     * Updates a matiereUser.
     *
     * @param matiereUserDTO the entity to update.
     * @return the persisted entity.
     */
    MatiereUserDTO update(MatiereUserDTO matiereUserDTO);

    /**
     * Partially updates a matiereUser.
     *
     * @param matiereUserDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MatiereUserDTO> partialUpdate(MatiereUserDTO matiereUserDTO);

    /**
     * Get all the matiereUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MatiereUserDTO> findAll(Pageable pageable);

    /**
     * Get the "id" matiereUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MatiereUserDTO> findOne(Long id);

    /**
     * Delete the "id" matiereUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
