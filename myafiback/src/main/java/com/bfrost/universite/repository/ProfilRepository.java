package com.bfrost.universite.repository;

import com.bfrost.universite.domain.Profil;
import com.bfrost.universite.domain.enumeration.TypeProfil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Profil entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfilRepository extends JpaRepository<Profil, Long> {


    @Query("""
        select p from Profil p where
        (:nom is null or upper(p.nom) like %:nom%)
        AND (:profil is null or p.typeProfil = :profil)
        """)
    Page<Profil> manageProfil(Pageable pageable, String nom, TypeProfil profil);
}
