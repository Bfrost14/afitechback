package com.bfrost.universite.service;

import com.bfrost.universite.service.dto.ProfilDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.bfrost.universite.domain.Profil}.
 */
public interface ProfilService {
    /**
     * Save a profil.
     *
     * @param profilDTO the entity to save.
     * @return the persisted entity.
     */
    ProfilDTO save(ProfilDTO profilDTO);

    /**
     * Updates a profil.
     *
     * @param profilDTO the entity to update.
     * @return the persisted entity.
     */
    ProfilDTO update(ProfilDTO profilDTO);

    /**
     * Partially updates a profil.
     *
     * @param profilDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProfilDTO> partialUpdate(ProfilDTO profilDTO);

    /**
     * Get all the profiles.
     *
     * @param pageable   the pagination information.
     * @param nom
     * @param typeProfil
     * @return the list of entities.
     */
    Page<ProfilDTO> findAll(Pageable pageable, String nom, String typeProfil);

    /**
     * Get the "id" profil.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProfilDTO> findOne(Long id);

    /**
     * Delete the "id" profil.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
