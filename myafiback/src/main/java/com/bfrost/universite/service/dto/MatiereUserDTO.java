package com.bfrost.universite.service.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.bfrost.universite.domain.MatiereUser} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MatiereUserDTO implements Serializable {

    private Long id;

    @NotNull
    private String anneeScolaire;

    private UserDTO user;

    private MatiereDTO matiere;

    private FiliereDTO filiere;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnneeScolaire() {
        return anneeScolaire;
    }

    public void setAnneeScolaire(String anneeScolaire) {
        this.anneeScolaire = anneeScolaire;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public MatiereDTO getMatiere() {
        return matiere;
    }

    public void setMatiere(MatiereDTO matiere) {
        this.matiere = matiere;
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
        if (!(o instanceof MatiereUserDTO)) {
            return false;
        }

        MatiereUserDTO matiereUserDTO = (MatiereUserDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, matiereUserDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatiereUserDTO{" +
            "id=" + getId() +
            ", anneeScolaire='" + getAnneeScolaire() + "'" +
            ", user=" + getUser() +
            ", matiere=" + getMatiere() +
            ", filiere=" + getFiliere() +
            "}";
    }
}
