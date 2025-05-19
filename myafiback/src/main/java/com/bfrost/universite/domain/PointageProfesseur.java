package com.bfrost.universite.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A PointageProfesseur.
 */
@Entity
@Table(name = "pointage_professeur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PointageProfesseur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "heure_arrivee")
    private ZonedDateTime heureArrivee;

    @Column(name = "heure_depart")
    private ZonedDateTime heureDepart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "filiere", "campus" }, allowSetters = true)
    private User professeur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PointageProfesseur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getHeureArrivee() {
        return this.heureArrivee;
    }

    public PointageProfesseur heureArrivee(ZonedDateTime heureArrivee) {
        this.setHeureArrivee(heureArrivee);
        return this;
    }

    public void setHeureArrivee(ZonedDateTime heureArrivee) {
        this.heureArrivee = heureArrivee;
    }

    public ZonedDateTime getHeureDepart() {
        return this.heureDepart;
    }

    public PointageProfesseur heureDepart(ZonedDateTime heureDepart) {
        this.setHeureDepart(heureDepart);
        return this;
    }

    public void setHeureDepart(ZonedDateTime heureDepart) {
        this.heureDepart = heureDepart;
    }

    public User getProfesseur() {
        return this.professeur;
    }

    public void setProfesseur(User uuser) {
        this.professeur = uuser;
    }

    public PointageProfesseur professeur(User uuser) {
        this.setProfesseur(uuser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PointageProfesseur)) {
            return false;
        }
        return getId() != null && getId().equals(((PointageProfesseur) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PointageProfesseur{" +
            "id=" + getId() +
            ", heureArrivee='" + getHeureArrivee() + "'" +
            ", heureDepart='" + getHeureDepart() + "'" +
            "}";
    }
}
