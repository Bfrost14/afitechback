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
public class AuthorityDTO implements Serializable {

   private String name;
}
