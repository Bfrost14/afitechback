package com.bfrost.universite.repository;

import com.bfrost.universite.domain.Profil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Profil entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfilRepository extends JpaRepository<Profil, Long> {

    Page<Profil> findAllByNomContainingIgnoreCase(Pageable pageable, String nom);
}
