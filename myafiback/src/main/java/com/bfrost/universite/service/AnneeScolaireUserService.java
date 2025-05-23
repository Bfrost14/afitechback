package com.bfrost.universite.service;

import com.bfrost.universite.service.dto.AnneeScolaireUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.bfrost.universite.domain.AnneeScolaireUser}.
 */
public interface AnneeScolaireUserService {
    /**
     * Save a anneeScolaireUser.
     *
     * @param anneeScolaireUserDTO the entity to save.
     * @return the persisted entity.
     */
    List<AnneeScolaireUserDTO> save(List<AnneeScolaireUserDTO> anneeScolaireUserDTO);

    /**
     * Updates a anneeScolaireUser.
     *
     * @param anneeScolaireUserDTO the entity to update.
     * @return the persisted entity.
     */
    AnneeScolaireUserDTO update(AnneeScolaireUserDTO anneeScolaireUserDTO);

    /**
     * Partially updates a anneeScolaireUser.
     *
     * @param anneeScolaireUserDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AnneeScolaireUserDTO> partialUpdate(AnneeScolaireUserDTO anneeScolaireUserDTO);

    /**
     * Get all the anneeScolaireUsers.
     *
     * @param pageable      the pagination information.
     * @param etudiant
     * @param anneeScolaire
     * @param filiere
     * @param semestre
     * @return the list of entities.
     */
    Page<AnneeScolaireUserDTO> findAll(Pageable pageable, String etudiant, String anneeScolaire, String filiere, String semestre);

    /**
     * Get the "id" anneeScolaireUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnneeScolaireUserDTO> findOne(Long id);

    /**
     * Delete the "id" anneeScolaireUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
