package com.bfrost.universite.repository;

import com.bfrost.universite.domain.Notation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Notation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotationRepository extends JpaRepository<Notation, Long> {}
