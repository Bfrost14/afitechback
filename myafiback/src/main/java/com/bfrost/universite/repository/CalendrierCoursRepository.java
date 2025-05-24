package com.bfrost.universite.repository;

import com.bfrost.universite.domain.CalendrierCours;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for the CalendrierCours entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CalendrierCoursRepository extends JpaRepository<CalendrierCours, Long> {

    @Query("""
        SELECT cc from CalendrierCours cc where
        (:professeur is null or (UPPER(cc.matiereUser.user.firstName) like %:professeur%
                                  or UPPER(cc.matiereUser.user.lastName) like %:professeur%
                                  or UPPER(cc.matiereUser.user.email) like %:professeur%))
         AND (:dateDebut IS NULL OR cc.dateDebut >= :dateDebut)
         AND (:dateFin IS NULL OR cc.dateFin <= :dateFin)
         AND (:salle IS NULL OR UPPER(cc.salle.numero) like %:salle%)
         AND (:matiere IS NULL OR UPPER(cc.matiereUser.matiere.nom) like %:matiere%)
         AND (:filiere IS NULL OR UPPER(cc.matiereUser.filiere.nom) like %:filiere%)
         AND (:campus IS NULL OR UPPER(cc.salle.campus.nom) like %:campus%)
        
        """)
    Page<CalendrierCours> managedUser(Pageable pageable, ZonedDateTime dateDebut, ZonedDateTime dateFin, String matiere, String filiere, String salle, String professeur, String campus);

    List<CalendrierCours> findAllByMatiereUserFiliereId(Long id);
}
