package com.bfrost.universite.repository;

import com.bfrost.universite.domain.PointageProfesseur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PointageProfesseur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PointageProfesseurRepository extends JpaRepository<PointageProfesseur, Long> {}
