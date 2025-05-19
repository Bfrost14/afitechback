package com.bfrost.universite.repository;

import com.bfrost.universite.domain.MatiereUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MatiereUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatiereUserRepository extends JpaRepository<MatiereUser, Long> {}
