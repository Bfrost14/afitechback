package com.bfrost.universite.repository;

import com.bfrost.universite.domain.Note;
import com.bfrost.universite.domain.enumeration.TypeNote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Note entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    @Query("""
        select n from Note n where
        (:idMatiereUser is null or n.matiereUser.id = :idMatiereUser)
        AND(:etudiant is null or n.user.email like %:etudiant%)
        AND (:semestre is null or n.matiereUser.semestre.nom like %:semestre%)
        AND (:matiere is null or n.matiereUser.matiere.nom like %:matiere%)
        AND (:typeNote is null or n.typeNote = :typeNote)
        """)
    Page<Note> manageNote(Pageable pageable, String etudiant, String semestre, String matiere, TypeNote typeNote, Long idMatiereUser);

    List<Note> findAllByUserId(Long id);
}
