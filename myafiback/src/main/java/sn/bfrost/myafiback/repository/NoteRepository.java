package sn.bfrost.myafiback.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sn.bfrost.myafiback.models.Note;

/**
 * Spring Data JPA repository for the Note entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {


    // Méthode pour récupérer les notes en fonction de différents critères (matière, nom, prénom et semestre)
    @Query("SELECT n FROM Note n " +
            "JOIN n.user u " +
            "WHERE (:matiere IS NULL OR n.matiere LIKE %:matiere%) " +
            "AND (:nom IS NULL OR u.nom LIKE %:nom%) " +
            "AND (:prenom IS NULL OR u.prenom LIKE %:prenom%) " +
            "AND (:filiere IS NULL OR u.filiere LIKE %:filiere%) " +
            "AND (:semestre IS NULL OR n.semestre LIKE %:semestre%)")
    Page<Note> findAllWithFilters(Pageable pageable,
                                  String matiere,
                                  String nom,
                                  String prenom,
                                  String semestre,
                                  String filiere);

    // Méthode pour récupérer les notes d'un étudiant particulier en fonction de son id, matière et semestre
    @Query("SELECT n FROM Note n " +
            "WHERE (:matiere IS NULL OR n.matiere LIKE %:matiere%) " +
            "AND (:semestre IS NULL OR n.semestre LIKE %:semestre%) " +
            "AND n.user.email = :userId")
    Page<Note> findAllByEtudiant(Pageable pageable, String userId, String matiere, String semestre);
}
