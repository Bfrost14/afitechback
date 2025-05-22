package com.bfrost.universite.service.dto;

import com.bfrost.universite.domain.Semestre;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.bfrost.universite.domain.AnneeScolaireUser} entity.
 */
@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AnneeScolaireUserDTO implements Serializable {

    private Long id;

    @NotNull
    private AnneeScolaireDTO anneeScolaire;

    private AdminUserDTO user;

    private SemestreDTO semestre;

}
