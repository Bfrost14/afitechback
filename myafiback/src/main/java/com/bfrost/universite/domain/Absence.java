package com.bfrost.universite.domain;

import com.bfrost.universite.service.dto.CalendrierCoursDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Absence.
 */
@Entity
@Data
@Table(name = "absence")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Absence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "justifie")
    private Boolean justifie;

    private Boolean presence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "professeur" }, allowSetters = true)
    private CalendrierCours calendrierCours;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "filiere", "campus" }, allowSetters = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here



}
