package com.bfrost.universite.service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.bfrost.universite.domain.Absence} entity.
 */
@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AbsenceDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate date;

    private Boolean justifie;

    private Boolean presence;

    private CalendrierCoursDTO calendrierCours;

    private AdminUserDTO user;

}
