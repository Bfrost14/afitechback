package com.bfrost.universite.web.rest;

import com.bfrost.universite.repository.CoursRepository;
import com.bfrost.universite.service.CoursService;
import com.bfrost.universite.service.PaginationService;
import com.bfrost.universite.service.dto.CoursDTO;
import com.bfrost.universite.service.dto.Pagination;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * REST controller for managing {@link com.bfrost.universite.domain.Cours}.
 */
@RestController
@RequestMapping("/api/cours")
public class CoursResource {

    private static final Logger LOG = LoggerFactory.getLogger(CoursResource.class);

    private static final String ENTITY_NAME = "cours";

    @Value("${spring.application.name}")
    private String applicationName;

    private final CoursService coursService;

    private final CoursRepository coursRepository;

    private final PaginationService paginationService;

    public CoursResource(CoursService coursService, CoursRepository coursRepository, PaginationService paginationService) {
        this.coursService = coursService;
        this.coursRepository = coursRepository;
        this.paginationService = paginationService;
    }

    /**
     * {@code POST  /cours} : Create a new cours.
     *
     * @param coursDTO the coursDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new coursDTO, or with status {@code 400 (Bad Request)} if the cours has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<List<CoursDTO>> createCours(@Valid @RequestBody List<CoursDTO> coursDTO) throws URISyntaxException {
        LOG.debug("REST request to save Cours : {}", coursDTO);
        if (coursDTO.get(0).getId() != null) {
            throw new BadRequestAlertException("A new cours cannot already have an ID", ENTITY_NAME, "idexists");
        }
        coursDTO = coursService.save(coursDTO);
        return ResponseEntity.created(new URI("/api/cours/" + coursDTO.get(0).getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, coursDTO.get(0).getId().toString()))
            .body(coursDTO);
    }

    /**
     * {@code PUT  /cours/:id} : Updates an existing cours.
     *
     * @param id the id of the coursDTO to save.
     * @param coursDTO the coursDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated coursDTO,
     * or with status {@code 400 (Bad Request)} if the coursDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the coursDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CoursDTO> updateCours(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CoursDTO coursDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Cours : {}, {}", id, coursDTO);
        if (coursDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, coursDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!coursRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        coursDTO = coursService.update(coursDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, coursDTO.getId().toString()))
            .body(coursDTO);
    }

    /**
     * {@code PATCH  /cours/:id} : Partial updates given fields of an existing cours, field will ignore if it is null
     *
     * @param id the id of the coursDTO to save.
     * @param coursDTO the coursDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated coursDTO,
     * or with status {@code 400 (Bad Request)} if the coursDTO is not valid,
     * or with status {@code 404 (Not Found)} if the coursDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the coursDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CoursDTO> partialUpdateCours(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CoursDTO coursDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Cours partially : {}, {}", id, coursDTO);
        if (coursDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, coursDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!coursRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CoursDTO> result = coursService.partialUpdate(coursDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, coursDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cours} : get all the cours.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cours in body.
     */
    @GetMapping("")
    public ResponseEntity<Map<String,Object>> getAllCours(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Cours");
        Page<CoursDTO> page = coursService.findAll(pageable);
        Pagination pagination=paginationService.instancierPagination(page);
        Map<String,Object> response=new HashMap<>();
        response.put("data",page.getContent());
        response.put("pagination",pagination);
        return ResponseEntity.ok().body(response);
    }

    /**
     * {@code GET  /cours/:id} : get the "id" cours.
     *
     * @param id the id of the coursDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the coursDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CoursDTO> getCours(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Cours : {}", id);
        Optional<CoursDTO> coursDTO = coursService.findOne(id);
        return ResponseUtil.wrapOrNotFound(coursDTO);
    }

    /**
     * {@code DELETE  /cours/:id} : delete the "id" cours.
     *
     * @param id the id of the coursDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCours(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Cours : {}", id);
        coursService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
