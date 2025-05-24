package com.bfrost.universite.service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bfrost.universite.domain.UE} entity.
 */
@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UEDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    @NotNull
    private String credit;

}
