package com.bfrost.universite.repository;

import com.bfrost.universite.domain.Filiere;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Filiere entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FiliereRepository extends JpaRepository<Filiere, Long> {

    Page<Filiere> findAllByNomContainingIgnoreCase(Pageable pageable, String nom);
}
