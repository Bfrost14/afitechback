package com.bfrost.universite.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.bfrost.universite.domain.CahierTexte} entity.
 */
@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CahierTexteDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate date;

    @Lob
    private String contenu;

    private CalendrierCoursDTO calendrierCours;

    private UserDTO user;

}
