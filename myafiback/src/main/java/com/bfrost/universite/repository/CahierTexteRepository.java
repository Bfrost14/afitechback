package com.bfrost.universite.repository;

import com.bfrost.universite.domain.CahierTexte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CahierTexte entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CahierTexteRepository extends JpaRepository<CahierTexte, Long> {}
