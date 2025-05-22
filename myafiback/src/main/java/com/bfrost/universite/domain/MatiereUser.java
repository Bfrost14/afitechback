package com.bfrost.universite.domain;

import com.bfrost.universite.service.dto.AnneeScolaireDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MatiereUser.
 */
@Entity
@Table(name = "matiere_user")
@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MatiereUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private AnneeScolaire anneeScolaire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "filiere", "campus" }, allowSetters = true)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "ue" }, allowSetters = true)
    private Matiere matiere;

    @ManyToOne(fetch = FetchType.LAZY)
    private Filiere filiere;

    @ManyToOne(fetch = FetchType.LAZY)
    private Semestre semestre;

    // jhipster-needle-entity-add-field - JHipster will add fields here

}
