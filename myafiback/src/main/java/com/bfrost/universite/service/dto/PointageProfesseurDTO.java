package com.bfrost.universite.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.bfrost.universite.domain.PointageProfesseur} entity.
 */
@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PointageProfesseurDTO implements Serializable {

    private Long id;

    private ZonedDateTime heureArrivee;

    private ZonedDateTime heureDepart;

    private AdminUserDTO professeur;


}
