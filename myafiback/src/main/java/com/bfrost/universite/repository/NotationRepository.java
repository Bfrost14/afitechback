package com.bfrost.universite.repository;

import com.bfrost.universite.domain.Notation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Notation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotationRepository extends JpaRepository<Notation, Long> {

    @Query("select n from Notation n where " +
            "(:etudiant is null or n.etudiant.email like %:etudiant%) " +
            "AND (:idCalendrier is null or n.calendrierCours.id = :idCalendrier) " +
            "AND (:matiere is null or n.calendrierCours.matiereUser.matiere.nom = :matiere)")
    Page<Notation> manageNotation(Pageable pageable, String etudiant, Long idCalendrier, String matiere);
}
