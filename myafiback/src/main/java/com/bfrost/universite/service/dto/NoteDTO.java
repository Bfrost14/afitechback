package com.bfrost.universite.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bfrost.universite.domain.Note} entity.
 */
@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NoteDTO implements Serializable {

    private Long id;

    private Float valeur;

    private UserDTO user;

    private MatiereUserDTO matiereUser;
}
