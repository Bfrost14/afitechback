package com.bfrost.universite.service.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bfrost.universite.domain.Filiere} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AnneeScolaireDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnneeScolaireDTO)) {
            return false;
        }

        AnneeScolaireDTO anneeScolaireDTO = (AnneeScolaireDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, anneeScolaireDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FiliereDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
