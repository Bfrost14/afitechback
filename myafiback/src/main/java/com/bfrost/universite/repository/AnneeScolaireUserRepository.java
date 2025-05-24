package com.bfrost.universite.repository;

import com.bfrost.universite.domain.AnneeScolaireUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the AnneeScolaireUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnneeScolaireUserRepository extends JpaRepository<AnneeScolaireUser, Long> {

    @Query("select mu from AnneeScolaireUser mu where " +
            "(:anneeScolaire is null or mu.anneeScolaire.nom like %:anneeScolaire%) " +
            "AND (:filiere is null or mu.user.filiere.nom like %:filiere% ) " +
            "AND (:semestre is null or mu.semestre.nom like %:semestre% ) " +
            "AND (:etudiant is null or " +
            "(mu.user.firstName like %:etudiant% " +
            "or mu.user.lastName like %:etudiant%) " +
            "or mu.user.email like %:etudiant%)")
    Page<AnneeScolaireUser> manageUser(Pageable pageable, String etudiant, String anneeScolaire, String filiere, String semestre);

    @Query("select mu from AnneeScolaireUser mu where " +
            "(:anneeScolaire is null or mu.anneeScolaire.nom like %:anneeScolaire%) " +
            "AND (:filiere is null or mu.user.filiere.nom like %:filiere% ) " +
            "AND (:semestre is null or mu.semestre.nom like %:semestre% ) ")
    List<AnneeScolaireUser> searchAllEtudiantfiliere(String anneeScolaire, String filiere, String semestre);

    List<AnneeScolaireUser> findAllByUserId(Long id);
}
