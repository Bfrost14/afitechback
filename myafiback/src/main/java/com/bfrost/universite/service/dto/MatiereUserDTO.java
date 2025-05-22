package com.bfrost.universite.service.dto;

import com.bfrost.universite.domain.Semestre;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bfrost.universite.domain.MatiereUser} entity.
 */
@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MatiereUserDTO implements Serializable {

    private Long id;

    @NotNull
    private AnneeScolaireDTO anneeScolaire;

    private AdminUserDTO user;

    private MatiereDTO matiere;

    private FiliereDTO filiere;

    private SemestreDTO semestre;

}
