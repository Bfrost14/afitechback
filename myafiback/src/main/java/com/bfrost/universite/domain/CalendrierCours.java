package com.bfrost.universite.domain;

import com.bfrost.universite.service.dto.MatiereUserDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A CalendrierCours.
 */
@Entity
@Table(name = "calendrier_cours")
@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CalendrierCours implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String lien;

    @NotNull
    @Column(name = "date_debut", nullable = false)
    private ZonedDateTime dateDebut;

    @NotNull
    @Column(name = "date_fin", nullable = false)
    private ZonedDateTime dateFin;

    @ManyToOne(fetch = FetchType.LAZY)
    private MatiereUser matiereUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "campus" }, allowSetters = true)
    private Salle salle;



    // jhipster-needle-entity-add-field - JHipster will add fields here

}
