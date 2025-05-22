package com.bfrost.universite.repository;

import com.bfrost.universite.domain.User;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link User} entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    String USERS_BY_LOGIN_CACHE = "usersByLogin";

    String USERS_BY_EMAIL_CACHE = "usersByEmail";
    Optional<User> findOneByActivationKey(String activationKey);
    List<User> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant dateTime);
    Optional<User> findOneByResetKey(String resetKey);
    Optional<User> findOneByEmailIgnoreCase(String email);
    Optional<User> findOneByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    @Cacheable(cacheNames = USERS_BY_LOGIN_CACHE, unless = "#result == null")
    Optional<User> findOneWithAuthoritiesByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    @Cacheable(cacheNames = USERS_BY_EMAIL_CACHE, unless = "#result == null")
    Optional<User> findOneWithAuthoritiesByEmailIgnoreCase(String email);

    Page<User> findAllByIdNotNullAndActivatedIsTrue(Pageable pageable);

    @Query("""
    SELECT u FROM User u
    LEFT JOIN u.filiere f
    LEFT JOIN u.profil p
    LEFT JOIN u.campus c
    WHERE (:prenom IS NULL OR LOWER(u.firstName) LIKE LOWER(CONCAT('%', :prenom, '%')))
      AND (:nom IS NULL OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :nom, '%')))
      AND (:email IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%')))
      AND (:telephone IS NULL OR u.telephone LIKE CONCAT('%', :telephone, '%'))
      AND (:filiere IS NULL OR LOWER(f.nom) LIKE LOWER(CONCAT('%', :filiere, '%')))
      AND (:matricule IS NULL OR u.matricule LIKE CONCAT('%', :matricule, '%'))
      AND (:profil IS NULL OR LOWER(p.nom) LIKE LOWER(CONCAT('%', :profil, '%')))
      AND (:campus IS NULL OR LOWER(c.nom) LIKE LOWER(CONCAT('%', :campus, '%')))
      AND (:admin IS NULL OR LOWER(p.nom) NOT IN ('professeur', 'etudiant'))
""")
    Page<User> managedUserBy(Pageable pageable, String prenom, String nom, String email, String telephone, String filiere, String campus, String matricule, String profil, Integer admin);
}
