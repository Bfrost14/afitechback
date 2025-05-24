package com.bfrost.universite.web.rest;

import com.bfrost.universite.repository.AnneeScolaireUserRepository;
import com.bfrost.universite.service.AnneeScolaireUserService;
import com.bfrost.universite.service.PaginationService;
import com.bfrost.universite.service.dto.AnneeScolaireUserDTO;
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
import java.util.*;

/**
 * REST controller for managing {@link com.bfrost.universite.domain.AnneeScolaireUser}.
 */
@RestController
@RequestMapping("/api/annee-scolaire-users")
public class AnneeScolaireUserResource {

    private static final Logger LOG = LoggerFactory.getLogger(AnneeScolaireUserResource.class);

    private static final String ENTITY_NAME = "anneeScolaireUser";

    @Value("${spring.application.name}")
    private String applicationName;

    private final AnneeScolaireUserService anneeScolaireUserService;

    private final AnneeScolaireUserRepository anneeScolaireUserRepository;

    private final PaginationService paginationService;

    public AnneeScolaireUserResource(
            AnneeScolaireUserService anneeScolaireUserService,
            AnneeScolaireUserRepository anneeScolaireUserRepository, PaginationService paginationService
    ) {
        this.anneeScolaireUserService = anneeScolaireUserService;
        this.anneeScolaireUserRepository = anneeScolaireUserRepository;
        this.paginationService = paginationService;
    }

    /**
     * {@code POST  /anneeScolaire-users} : Create a new anneeScolaireUser.
     *
     * @param anneeScolaireUserDTO the anneeScolaireUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new anneeScolaireUserDTO, or with status {@code 400 (Bad Request)} if the anneeScolaireUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('ENREGISTREMENT_USER')")
    public ResponseEntity<List<AnneeScolaireUserDTO>> createAnneeScolaireUser(@Valid @RequestBody List<AnneeScolaireUserDTO> anneeScolaireUserDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save AnneeScolaireUser : {}", anneeScolaireUserDTO);
        if (anneeScolaireUserDTO.get(0).getId() != null) {
            throw new BadRequestAlertException("A new anneeScolaireUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        anneeScolaireUserDTO = anneeScolaireUserService.save(anneeScolaireUserDTO);
        return ResponseEntity.created(new URI("/api/anneeScolaire-users/" + anneeScolaireUserDTO.get(0).getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, anneeScolaireUserDTO.get(0).getId().toString()))
            .body(anneeScolaireUserDTO);
    }

    /**
     * {@code PUT  /anneeScolaire-users/:id} : Updates an existing anneeScolaireUser.
     *
     * @param id the id of the anneeScolaireUserDTO to save.
     * @param anneeScolaireUserDTO the anneeScolaireUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anneeScolaireUserDTO,
     * or with status {@code 400 (Bad Request)} if the anneeScolaireUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the anneeScolaireUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MODIFICATION_GENERALE_USER')")
    public ResponseEntity<AnneeScolaireUserDTO> updateAnneeScolaireUser(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AnneeScolaireUserDTO anneeScolaireUserDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update AnneeScolaireUser : {}, {}", id, anneeScolaireUserDTO);
        if (anneeScolaireUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anneeScolaireUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anneeScolaireUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        anneeScolaireUserDTO = anneeScolaireUserService.update(anneeScolaireUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anneeScolaireUserDTO.getId().toString()))
            .body(anneeScolaireUserDTO);
    }

    /**
     * {@code PATCH  /anneeScolaire-users/:id} : Partial updates given fields of an existing anneeScolaireUser, field will ignore if it is null
     *
     * @param id the id of the anneeScolaireUserDTO to save.
     * @param anneeScolaireUserDTO the anneeScolaireUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anneeScolaireUserDTO,
     * or with status {@code 400 (Bad Request)} if the anneeScolaireUserDTO is not valid,
     * or with status {@code 404 (Not Found)} if the anneeScolaireUserDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the anneeScolaireUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("hasAnyAuthority('MODIFICATION_USER')")
    public ResponseEntity<AnneeScolaireUserDTO> partialUpdateAnneeScolaireUser(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AnneeScolaireUserDTO anneeScolaireUserDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update AnneeScolaireUser partially : {}, {}", id, anneeScolaireUserDTO);
        if (anneeScolaireUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anneeScolaireUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anneeScolaireUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AnneeScolaireUserDTO> result = anneeScolaireUserService.partialUpdate(anneeScolaireUserDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anneeScolaireUserDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /anneeScolaire-users} : get all the anneeScolaireUsers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of anneeScolaireUsers in body.
     */
    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('LECTURE_LISTE_USER')")
    public ResponseEntity<Map<String,Object>> getAllAnneeScolaireUsers(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(value = "etudiant", required = false) String etudiant,
        @RequestParam(value = "anneeScolaire", required = false) String anneeScolaire,
        @RequestParam(value = "filiere", required = false) String filiere,
        @RequestParam(value = "semestre", required = false) String semestre
    ) {
        LOG.debug("REST request to get a page of AnneeScolaireUsers");
        Page<AnneeScolaireUserDTO> page = anneeScolaireUserService.findAll(pageable, etudiant, anneeScolaire, filiere, semestre);
        Pagination pagination=paginationService.instancierPagination(page);
        Map<String,Object> response=new HashMap<>();
        response.put("data",page.getContent());
        response.put("pagination",pagination);
        return ResponseEntity.ok().body(response);
    }

    /**
     * {@code GET  /anneeScolaire-users/:id} : get the "id" anneeScolaireUser.
     *
     * @param id the id of the anneeScolaireUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the anneeScolaireUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('LECTURE_DETAILLE_USER')")
    public ResponseEntity<AnneeScolaireUserDTO> getAnneeScolaireUser(@PathVariable("id") Long id) {
        LOG.debug("REST request to get AnneeScolaireUser : {}", id);
        Optional<AnneeScolaireUserDTO> anneeScolaireUserDTO = anneeScolaireUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(anneeScolaireUserDTO);
    }

    /**
     * {@code DELETE  /anneeScolaire-users/:id} : delete the "id" anneeScolaireUser.
     *
     * @param id the id of the anneeScolaireUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SUPPRESSION_USER')")
    public ResponseEntity<Void> deleteAnneeScolaireUser(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete AnneeScolaireUser : {}", id);
        anneeScolaireUserService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
