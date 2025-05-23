package com.bfrost.universite.repository;

import com.bfrost.universite.domain.Absence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Absence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long> {

    @Query("select a from Absence a where " +
            "(:etudiant is null or a.user.email like %:etudiant%) " +
            "AND (:idCalendrier is null or a.calendrierCours.id = :idCalendrier) " +
            "AND (:filiere is null or a.calendrierCours.matiereUser.filiere.nom like %:filiere%)")
    Page<Absence> managedAbsence(Pageable pageable, String etudiant, Long idCalendrier, String filiere);
}
