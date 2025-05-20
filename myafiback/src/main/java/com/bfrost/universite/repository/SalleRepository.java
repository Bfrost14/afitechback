package com.bfrost.universite.repository;

import com.bfrost.universite.domain.Salle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Salle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SalleRepository extends JpaRepository<Salle, Long> {

    @Query("select s from Salle s where (:numero IS NULL or s.numero like %:numero%) and (:campus IS NULL or s.campus.nom like %:campus%)")
    Page<Salle> searchAllByCampusAndNumero(Pageable pageable, String numero, String campus);
}
