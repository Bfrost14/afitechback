package com.bfrost.universite.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;

/**
 * A AnneeScolaireUser.
 */
@Entity
@Table(name = "annee_scolaire_user")
@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AnneeScolaireUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Semestre semestre;

    @ManyToOne(fetch = FetchType.LAZY)
    private AnneeScolaire anneeScolaire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "filiere", "campus" }, allowSetters = true)
    private User user;


}
