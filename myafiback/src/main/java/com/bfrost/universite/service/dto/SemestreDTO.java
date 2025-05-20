package com.bfrost.universite.service.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bfrost.universite.domain.Semestre} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SemestreDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    private String annee;

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

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SemestreDTO)) {
            return false;
        }

        SemestreDTO semestreDTO = (SemestreDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, semestreDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SemestreDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
