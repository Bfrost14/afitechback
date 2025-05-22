package com.bfrost.universite.service.dto;

import com.bfrost.universite.domain.Salle;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.bfrost.universite.domain.CalendrierCours} entity.
 */
@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CalendrierCoursDTO implements Serializable {

    private Long id;

    private String lien;

    @NotNull
    private ZonedDateTime dateDebut;

    @NotNull
    private ZonedDateTime dateFin;

    private SalleDTO salle;

    private MatiereUserDTO matiereUser;

}
