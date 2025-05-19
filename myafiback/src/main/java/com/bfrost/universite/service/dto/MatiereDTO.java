package com.bfrost.universite.service.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bfrost.universite.domain.Matiere} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MatiereDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    @NotNull
    private Integer credit;

    private UEDTO ue;

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

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public UEDTO getUe() {
        return ue;
    }

    public void setUe(UEDTO ue) {
        this.ue = ue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatiereDTO)) {
            return false;
        }

        MatiereDTO matiereDTO = (MatiereDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, matiereDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatiereDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", credit=" + getCredit() +
            ", ue=" + getUe() +
            "}";
    }
}
