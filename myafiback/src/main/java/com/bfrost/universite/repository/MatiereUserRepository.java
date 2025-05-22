package com.bfrost.universite.repository;

import com.bfrost.universite.domain.MatiereUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MatiereUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatiereUserRepository extends JpaRepository<MatiereUser, Long> {

    @Query("select mu from MatiereUser mu where " +
            "(:anneeScolaire is null or mu.anneeScolaire.nom like %:anneeScolaire%) " +
            "AND (:filiere is null or mu.filiere.nom like %:filiere% ) " +
            "AND (:matiere is null or mu.matiere.nom like %:matiere% ) " +
            "AND (:semestre is null or mu.semestre.nom like %:semestre% ) " +
            "AND (:professeur is null or " +
            "(mu.user.firstName like %:professeur% " +
            "or mu.user.lastName like %:professeur%) " +
            "or mu.user.email like %:professeur%)")
    Page<MatiereUser> manageUser(Pageable pageable, String professeur, String anneeScolaire, String matiere, String filiere, String semestre);
}
