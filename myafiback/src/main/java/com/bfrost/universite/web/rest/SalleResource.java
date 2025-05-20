package com.bfrost.universite.web.rest;

import com.bfrost.universite.repository.SalleRepository;
import com.bfrost.universite.service.PaginationService;
import com.bfrost.universite.service.SalleService;
import com.bfrost.universite.service.dto.Pagination;
import com.bfrost.universite.service.dto.SalleDTO;
import com.bfrost.universite.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * REST controller for managing {@link com.bfrost.universite.domain.Salle}.
 */
@RestController
@RequestMapping("/api/salles")
public class SalleResource {

    private static final Logger LOG = LoggerFactory.getLogger(SalleResource.class);

    private static final String ENTITY_NAME = "salle";

    @Value("${spring.application.name}")
    private String applicationName;

    private final SalleService salleService;

    private final SalleRepository salleRepository;

    private final PaginationService paginationService;

    public SalleResource(SalleService salleService, SalleRepository salleRepository, PaginationService paginationService) {
        this.salleService = salleService;
        this.salleRepository = salleRepository;
        this.paginationService = paginationService;
    }

    /**
     * {@code POST  /salles} : Create a new salle.
     *
     * @param salleDTO the salleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new salleDTO, or with status {@code 400 (Bad Request)} if the salle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('ENREGISTREMENT_SALLE')")
    public ResponseEntity<SalleDTO> createSalle(@Valid @RequestBody SalleDTO salleDTO) throws URISyntaxException {
        LOG.debug("REST request to save Salle : {}", salleDTO);
        if (salleDTO.getId() != null) {
            throw new BadRequestAlertException("A new salle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        salleDTO = salleService.save(salleDTO);
        return ResponseEntity.created(new URI("/api/salles/" + salleDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, salleDTO.getId().toString()))
            .body(salleDTO);
    }

    /**
     * {@code PUT  /salles/:id} : Updates an existing salle.
     *
     * @param id the id of the salleDTO to save.
     * @param salleDTO the salleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salleDTO,
     * or with status {@code 400 (Bad Request)} if the salleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the salleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MODIFICATION_GENERALE_SALLE')")
    public ResponseEntity<SalleDTO> updateSalle(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SalleDTO salleDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Salle : {}, {}", id, salleDTO);
        if (salleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, salleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!salleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        salleDTO = salleService.update(salleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, salleDTO.getId().toString()))
            .body(salleDTO);
    }

    /**
     * {@code PATCH  /salles/:id} : Partial updates given fields of an existing salle, field will ignore if it is null
     *
     * @param id the id of the salleDTO to save.
     * @param salleDTO the salleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salleDTO,
     * or with status {@code 400 (Bad Request)} if the salleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the salleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the salleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("hasAnyAuthority('MODIFICATION_SALLE')")
    public ResponseEntity<SalleDTO> partialUpdateSalle(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SalleDTO salleDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Salle partially : {}, {}", id, salleDTO);
        if (salleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, salleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!salleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SalleDTO> result = salleService.partialUpdate(salleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, salleDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /salles} : get all the salles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of salles in body.
     */
    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('LECTURE_LISTE_SALLE')")
    public ResponseEntity<Map<String,Object>> getAllSalles(@org.springdoc.core.annotations.ParameterObject Pageable pageable,
                                                           @RequestParam(value = "numero", required = false) String numero,
                                                           @RequestParam(value = "campus", required = false) String campus) {
        LOG.debug("REST request to get a page of Salles");
        Page<SalleDTO> page = salleService.findAll(pageable, numero, campus);
        Pagination pagination=paginationService.instancierPagination(page);
        Map<String,Object> response=new HashMap<>();
        response.put("data",page.getContent());
        response.put("pagination",pagination);
        return ResponseEntity.ok().body(response);
    }

    /**
     * {@code GET  /salles/:id} : get the "id" salle.
     *
     * @param id the id of the salleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the salleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('LECTURE_DETAILLE_SALLE')")
    public ResponseEntity<SalleDTO> getSalle(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Salle : {}", id);
        Optional<SalleDTO> salleDTO = salleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(salleDTO);
    }

    /**
     * {@code DELETE  /salles/:id} : delete the "id" salle.
     *
     * @param id the id of the salleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SUPPRESSION_SALLE')")
    public ResponseEntity<Void> deleteSalle(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Salle : {}", id);
        salleService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
