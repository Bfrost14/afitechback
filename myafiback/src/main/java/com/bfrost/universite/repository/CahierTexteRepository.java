package com.bfrost.universite.repository;

import com.bfrost.universite.domain.CahierTexte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CahierTexte entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CahierTexteRepository extends JpaRepository<CahierTexte, Long> {

    @Query("""
        SELECT c from CahierTexte c where 
        (:professeur is null or c.user.email like %:professeur% )
        AND (:idCalendrier is null or c.calendrierCours.id = :idCalendrier)
        AND (:matiere is null or c.calendrierCours.matiereUser.matiere.nom like %:matiere%)
    """)
    Page<CahierTexte> manageCahier(Pageable pageable, String professeur, Long idCalendrier, String matiere);
}
