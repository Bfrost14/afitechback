package com.bfrost.universite.repository;

import com.bfrost.universite.domain.PointageProfesseur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;

/**
 * Spring Data JPA repository for the PointageProfesseur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PointageProfesseurRepository extends JpaRepository<PointageProfesseur, Long> {

    @Query("""
                SELECT p FROM PointageProfesseur p
                WHERE (:professeur is null or (p.professeur.firstName like %:professeur%
                                  or p.professeur.lastName like %:professeur%
                                  or p.professeur.email like %:professeur%))
                AND (:dateDebut IS NULL OR p.heureArrivee >= :dateDebut)
                AND (:dateFin IS NULL OR p.heureDepart <= :dateFin)
            """)
    Page<PointageProfesseur> findAllWithFilters(
            @Param("dateDebut") ZonedDateTime dateDebut,
            @Param("dateFin") ZonedDateTime dateFin,
            @Param("professeur") String professeur,
            Pageable pageable
    );

}
