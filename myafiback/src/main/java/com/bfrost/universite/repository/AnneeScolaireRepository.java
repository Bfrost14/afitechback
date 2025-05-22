package com.bfrost.universite.repository;

import com.bfrost.universite.domain.AnneeScolaire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AnneeScolaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnneeScolaireRepository extends JpaRepository<AnneeScolaire, Long> {

    Page<AnneeScolaire> findAllByNomContainingIgnoreCase(Pageable pageable, String nom);
}
