package com.bfrost.universite.repository;

import com.bfrost.universite.domain.UE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UE entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UERepository extends JpaRepository<UE, Long> {}
