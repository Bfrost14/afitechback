package com.bfrost.universite.service.dto;

import com.bfrost.universite.domain.Authority;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * A DTO for the {@link com.bfrost.universite.domain.Profil} entity.
 */
@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProfilDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    @NotNull
    private String redirection;

    private Set<AuthorityDTO> authorities;

}
