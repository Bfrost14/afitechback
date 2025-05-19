package com.bfrost.universite.repository;

import com.bfrost.universite.domain.CalendrierCours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CalendrierCours entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CalendrierCoursRepository extends JpaRepository<CalendrierCours, Long> {}
