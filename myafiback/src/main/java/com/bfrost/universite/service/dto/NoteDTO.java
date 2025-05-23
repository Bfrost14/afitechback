package com.bfrost.universite.service.dto;

import com.bfrost.universite.domain.enumeration.TypeNote;
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

    private TypeNote typeNote;

    private AdminUserDTO user;

    private MatiereUserDTO matiereUser;
}
