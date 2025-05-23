package com.bfrost.universite.domain;

import com.bfrost.universite.domain.enumeration.TypeNote;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;

/**
 * A Note.
 */
@Entity
@Table(name = "note")
@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Note implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "valeur")
    private Float valeur;

    private TypeNote typeNote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "filiere", "campus" }, allowSetters = true)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "ue" }, allowSetters = true)
    private MatiereUser matiereUser;


}
