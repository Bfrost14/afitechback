package com.bfrost.universite.repository;

import com.bfrost.universite.domain.Campus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Campus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CampusRepository extends JpaRepository<Campus, Long> {

    Page<Campus> findAllByNomContainingIgnoreCase(Pageable pageable, String nom);
}
