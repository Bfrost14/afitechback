package com.bfrost.universite.service.dto;

import com.bfrost.universite.config.Constants;
import com.bfrost.universite.domain.Authority;
import com.bfrost.universite.domain.Campus;
import com.bfrost.universite.domain.User;
import com.bfrost.universite.domain.enumeration.Sexe;
import com.bfrost.universite.service.mapper.CampusMapper;
import com.bfrost.universite.service.mapper.FiliereMapper;
import com.bfrost.universite.service.mapper.ProfilMapper;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A DTO representing a user, with his authorities.
 */
@Data
public class AdminUserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String login;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 254)
    private String email;


    private String matricule;

    private String password;

    @Size(max = 256)
    private String imageUrl;

    private boolean activated = false;

    @Size(min = 2, max = 10)
    private String langKey;

    private LocalDate dateDeNaissance;

    private String telephone;

    private Sexe sexe;

    private String nationalite;

    private Boolean firstConnection;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private Set<AuthorityDTO> authorities;

    private FiliereDTO filiere;

    private CampusDTO campus;

    private ProfilDTO profil;

    private Set<CampusDTO> campuses;

    private CalendrierCoursDTO calendrierCours;

    private List<StatParSemestreDTO> statsParSemestre;

    private Double progressionAcademique;



}
