package com.bfrost.universite.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bfrost.universite.domain.Notation} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NotationDTO implements Serializable {

    private Long id;

    private Double note;

    private String appreciation;

    private CoursDTO cours;

    private UserDTO etudiant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getNote() {
        return note;
    }

    public void setNote(Double note) {
        this.note = note;
    }

    public String getAppreciation() {
        return appreciation;
    }

    public void setAppreciation(String appreciation) {
        this.appreciation = appreciation;
    }

    public CoursDTO getCours() {
        return cours;
    }

    public void setCours(CoursDTO cours) {
        this.cours = cours;
    }

    public UserDTO getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(UserDTO etudiant) {
        this.etudiant = etudiant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NotationDTO)) {
            return false;
        }

        NotationDTO notationDTO = (NotationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, notationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotationDTO{" +
            "id=" + getId() +
            ", note=" + getNote() +
            ", appreciation='" + getAppreciation() + "'" +
            ", cours=" + getCours() +
            ", etudiant=" + getEtudiant() +
            "}";
    }
}
