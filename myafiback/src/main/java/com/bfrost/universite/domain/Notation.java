package com.bfrost.universite.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;

/**
 * A Notation.
 */
@Entity
@Table(name = "notation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Notation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "note")
    private Double note;

    @Column(name = "appreciation")
    private String appreciation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "professeur" }, allowSetters = true)
    private Cours cours;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "filiere", "campus" }, allowSetters = true)
    private User etudiant;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Notation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getNote() {
        return this.note;
    }

    public Notation note(Double note) {
        this.setNote(note);
        return this;
    }

    public void setNote(Double note) {
        this.note = note;
    }

    public String getAppreciation() {
        return this.appreciation;
    }

    public Notation appreciation(String appreciation) {
        this.setAppreciation(appreciation);
        return this;
    }

    public void setAppreciation(String appreciation) {
        this.appreciation = appreciation;
    }

    public Cours getCours() {
        return this.cours;
    }

    public void setCours(Cours cours) {
        this.cours = cours;
    }

    public Notation cours(Cours cours) {
        this.setCours(cours);
        return this;
    }

    public User getEtudiant() {
        return this.etudiant;
    }

    public void setEtudiant(User uuser) {
        this.etudiant = uuser;
    }

    public Notation etudiant(User uuser) {
        this.setEtudiant(uuser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Notation)) {
            return false;
        }
        return getId() != null && getId().equals(((Notation) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Notation{" +
            "id=" + getId() +
            ", note=" + getNote() +
            ", appreciation='" + getAppreciation() + "'" +
            "}";
    }
}
