package com.bfrost.universite.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bfrost.universite.domain.Cours} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CoursDTO implements Serializable {

    private Long id;

    @NotNull
    private String intitule;

    private UserDTO professeur;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public UserDTO getProfesseur() {
        return professeur;
    }

    public void setProfesseur(UserDTO professeur) {
        this.professeur = professeur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CoursDTO)) {
            return false;
        }

        CoursDTO coursDTO = (CoursDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, coursDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CoursDTO{" +
            "id=" + getId() +
            ", intitule='" + getIntitule() + "'" +
            ", professeur=" + getProfesseur() +
            "}";
    }
}
