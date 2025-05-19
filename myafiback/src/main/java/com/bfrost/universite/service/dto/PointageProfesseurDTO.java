package com.bfrost.universite.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.bfrost.universite.domain.PointageProfesseur} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PointageProfesseurDTO implements Serializable {

    private Long id;

    private ZonedDateTime heureArrivee;

    private ZonedDateTime heureDepart;

    private UserDTO professeur;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getHeureArrivee() {
        return heureArrivee;
    }

    public void setHeureArrivee(ZonedDateTime heureArrivee) {
        this.heureArrivee = heureArrivee;
    }

    public ZonedDateTime getHeureDepart() {
        return heureDepart;
    }

    public void setHeureDepart(ZonedDateTime heureDepart) {
        this.heureDepart = heureDepart;
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
        if (!(o instanceof PointageProfesseurDTO)) {
            return false;
        }

        PointageProfesseurDTO pointageProfesseurDTO = (PointageProfesseurDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pointageProfesseurDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PointageProfesseurDTO{" +
            "id=" + getId() +
            ", heureArrivee='" + getHeureArrivee() + "'" +
            ", heureDepart='" + getHeureDepart() + "'" +
            ", professeur=" + getProfesseur() +
            "}";
    }
}
