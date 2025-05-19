package com.bfrost.universite.service.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.bfrost.universite.domain.CalendrierCours} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CalendrierCoursDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime dateDebut;

    @NotNull
    private ZonedDateTime dateFin;

    private CoursDTO cours;

    private SalleDTO salle;

    private FiliereDTO filiere;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(ZonedDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public ZonedDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(ZonedDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public CoursDTO getCours() {
        return cours;
    }

    public void setCours(CoursDTO cours) {
        this.cours = cours;
    }

    public SalleDTO getSalle() {
        return salle;
    }

    public void setSalle(SalleDTO salle) {
        this.salle = salle;
    }

    public FiliereDTO getFiliere() {
        return filiere;
    }

    public void setFiliere(FiliereDTO filiere) {
        this.filiere = filiere;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CalendrierCoursDTO)) {
            return false;
        }

        CalendrierCoursDTO calendrierCoursDTO = (CalendrierCoursDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, calendrierCoursDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CalendrierCoursDTO{" +
            "id=" + getId() +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", cours=" + getCours() +
            ", salle=" + getSalle() +
            ", filiere=" + getFiliere() +
            "}";
    }
}
