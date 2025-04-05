package sn.bfrost.myafiback.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sn.bfrost.myafiback.models.Role;
import sn.bfrost.myafiback.models.User;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE " +
            "(:nom IS NULL OR u.nom LIKE %:nom%) AND " +
            "(:matricule IS NULL OR u.matricule LIKE %:matricule%) AND " +
            "(:prenom IS NULL OR u.prenom LIKE %:prenom%) AND " +
            "(:filiere IS NULL OR u.filiere LIKE %:filiere%) AND " +
            "(:role IS NULL OR u.role = :role) AND " +
            "(:email IS NULL OR u.email LIKE %:email%)")
    Page<User> searchUsers(Pageable pageable, String matricule,String nom, String prenom, String filiere, String email,Role role);

}
