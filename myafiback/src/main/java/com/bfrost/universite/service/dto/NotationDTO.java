package com.bfrost.universite.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bfrost.universite.domain.Notation} entity.
 */
@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NotationDTO implements Serializable {

    private Long id;

    private Double note;

    private String appreciation;

    private CalendrierCoursDTO calendrierCoursDTO;

    private UserDTO etudiant;

}
