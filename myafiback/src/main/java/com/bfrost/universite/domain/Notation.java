package com.bfrost.universite.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;

/**
 * A Notation.
 */
@Entity
@Data
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
    private CalendrierCours calendrierCours;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "filiere", "campus" }, allowSetters = true)
    private User etudiant;

    // jhipster-needle-entity-add-field - JHipster will add fields here


}
