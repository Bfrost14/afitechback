package com.bfrost.universite.service.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bfrost.universite.domain.Salle} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SalleDTO implements Serializable {

    private Long id;

    @NotNull
    private String numero;

    private CampusDTO campus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public CampusDTO getCampus() {
        return campus;
    }

    public void setCampus(CampusDTO campus) {
        this.campus = campus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SalleDTO)) {
            return false;
        }

        SalleDTO salleDTO = (SalleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, salleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalleDTO{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", campus=" + getCampus() +
            "}";
    }
}
