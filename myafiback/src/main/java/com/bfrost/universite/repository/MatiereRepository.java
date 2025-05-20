package com.bfrost.universite.repository;

import com.bfrost.universite.domain.Matiere;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Matiere entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatiereRepository extends JpaRepository<Matiere, Long> {

    @Query("select m from Matiere m where (:nom is null or m.nom like %:nom%) and (:ue is null or m.ue.nom like %:ue%)")
    Page<Matiere> searchAllByNomAndUe(Pageable pageable, String nom, String ue);
}
