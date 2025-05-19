package com.bfrost.universite.service.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.bfrost.universite.domain.Absence} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AbsenceDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate date;

    private Boolean justifie;

    private CoursDTO cours;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getJustifie() {
        return justifie;
    }

    public void setJustifie(Boolean justifie) {
        this.justifie = justifie;
    }

    public CoursDTO getCours() {
        return cours;
    }

    public void setCours(CoursDTO cours) {
        this.cours = cours;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbsenceDTO)) {
            return false;
        }

        AbsenceDTO absenceDTO = (AbsenceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, absenceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AbsenceDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", justifie='" + getJustifie() + "'" +
            ", cours=" + getCours() +
            ", user=" + getUser() +
            "}";
    }
}
