package com.bfrost.universite.web.rest;

import com.bfrost.universite.repository.AnneeScolaireRepository;
import com.bfrost.universite.service.AnneeScolaireService;
import com.bfrost.universite.service.PaginationService;
import com.bfrost.universite.service.dto.AnneeScolaireDTO;
import com.bfrost.universite.service.dto.Pagination;
import com.bfrost.universite.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link com.bfrost.universite.domain.AnneeScolaire}.
 */
@RestController
@RequestMapping("/api/annee-scolaire")
public class AnneeScolaireResource {

    private static final Logger LOG = LoggerFactory.getLogger(AnneeScolaireResource.class);

    private static final String ENTITY_NAME = "anneeScolaire";

    @Value("${spring.application.name}")
    private String applicationName;

    private final AnneeScolaireService anneeScolaireService;

    private final AnneeScolaireRepository anneeScolaireRepository;

    private final PaginationService paginationService;

    public AnneeScolaireResource(AnneeScolaireService anneeScolaireService, AnneeScolaireRepository anneeScolaireRepository, PaginationService paginationService) {
        this.anneeScolaireService = anneeScolaireService;
        this.anneeScolaireRepository = anneeScolaireRepository;
        this.paginationService = paginationService;
    }

    /**
     * {@code POST  /anneeScolaires} : Create a new anneeScolaire.
     *
     * @param anneeScolaireDTO the anneeScolaireDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new anneeScolaireDTO, or with status {@code 400 (Bad Request)} if the anneeScolaire has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('ENREGISTREMENT_ANNEESCOLAIRE')")
    public ResponseEntity<AnneeScolaireDTO> createAnneeScolaire(@Valid @RequestBody AnneeScolaireDTO anneeScolaireDTO) throws URISyntaxException {
        LOG.debug("REST request to save AnneeScolaire : {}", anneeScolaireDTO);
        if (anneeScolaireDTO.getId() != null) {
            throw new BadRequestAlertException("A new anneeScolaire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        anneeScolaireDTO = anneeScolaireService.save(anneeScolaireDTO);
        return ResponseEntity.created(new URI("/api/anneeScolaires/" + anneeScolaireDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, anneeScolaireDTO.getId().toString()))
            .body(anneeScolaireDTO);
    }

    /**
     * {@code PUT  /anneeScolaires/:id} : Updates an existing anneeScolaire.
     *
     * @param id the id of the anneeScolaireDTO to save.
     * @param anneeScolaireDTO the anneeScolaireDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anneeScolaireDTO,
     * or with status {@code 400 (Bad Request)} if the anneeScolaireDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the anneeScolaireDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MODIFICATION_GENERALE_ANNEESCOLAIRE')")
    public ResponseEntity<AnneeScolaireDTO> updateAnneeScolaire(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AnneeScolaireDTO anneeScolaireDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update AnneeScolaire : {}, {}", id, anneeScolaireDTO);
        if (anneeScolaireDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anneeScolaireDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anneeScolaireRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        anneeScolaireDTO = anneeScolaireService.update(anneeScolaireDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anneeScolaireDTO.getId().toString()))
            .body(anneeScolaireDTO);
    }

    /**
     * {@code PATCH  /anneeScolaires/:id} : Partial updates given fields of an existing anneeScolaire, field will ignore if it is null
     *
     * @param id the id of the anneeScolaireDTO to save.
     * @param anneeScolaireDTO the anneeScolaireDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anneeScolaireDTO,
     * or with status {@code 400 (Bad Request)} if the anneeScolaireDTO is not valid,
     * or with status {@code 404 (Not Found)} if the anneeScolaireDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the anneeScolaireDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("hasAnyAuthority('MODIFICATION_ANNEESCOLAIRE')")
    public ResponseEntity<AnneeScolaireDTO> partialUpdateAnneeScolaire(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AnneeScolaireDTO anneeScolaireDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update AnneeScolaire partially : {}, {}", id, anneeScolaireDTO);
        if (anneeScolaireDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anneeScolaireDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anneeScolaireRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AnneeScolaireDTO> result = anneeScolaireService.partialUpdate(anneeScolaireDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anneeScolaireDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /anneeScolaires} : get all the anneeScolaires.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of anneeScolaires in body.
     */
    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('LECTURE_LISTE_ANNEESCOLAIRE')")
    public ResponseEntity<Map<String,Object>> getAllAnneeScolaires(@org.springdoc.core.annotations.ParameterObject Pageable pageable,
                                                             @RequestParam(value = "nom", required = false) String nom) {
        LOG.debug("REST request to get a page of AnneeScolaires");
        Page<AnneeScolaireDTO> page = anneeScolaireService.findAll(pageable, nom);
        Pagination pagination=paginationService.instancierPagination(page);
        Map<String,Object> response=new HashMap<>();
        response.put("data",page.getContent());
        response.put("pagination",pagination);
        return ResponseEntity.ok().body(response);
    }

    /**
     * {@code GET  /anneeScolaires/:id} : get the "id" anneeScolaire.
     *
     * @param id the id of the anneeScolaireDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the anneeScolaireDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('LECTURE_DETAILLE_ANNEESCOLAIRE')")
    public ResponseEntity<AnneeScolaireDTO> getAnneeScolaire(@PathVariable("id") Long id) {
        LOG.debug("REST request to get AnneeScolaire : {}", id);
        Optional<AnneeScolaireDTO> anneeScolaireDTO = anneeScolaireService.findOne(id);
        return ResponseUtil.wrapOrNotFound(anneeScolaireDTO);
    }

    /**
     * {@code DELETE  /anneeScolaires/:id} : delete the "id" anneeScolaire.
     *
     * @param id the id of the anneeScolaireDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SUPPRESSION_ANNEESCOLAIRE')")
    public ResponseEntity<Void> deleteAnneeScolaire(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete AnneeScolaire : {}", id);
        anneeScolaireService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
