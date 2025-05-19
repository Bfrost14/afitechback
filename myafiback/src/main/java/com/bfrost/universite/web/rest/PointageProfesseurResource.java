package com.bfrost.universite.web.rest;

import com.bfrost.universite.repository.PointageProfesseurRepository;
import com.bfrost.universite.service.PaginationService;
import com.bfrost.universite.service.PointageProfesseurService;
import com.bfrost.universite.service.dto.Pagination;
import com.bfrost.universite.service.dto.PointageProfesseurDTO;
import com.bfrost.universite.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

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

/**
 * REST controller for managing {@link com.bfrost.universite.domain.PointageProfesseur}.
 */
@RestController
@RequestMapping("/api/pointage-professeurs")
public class PointageProfesseurResource {

    private static final Logger LOG = LoggerFactory.getLogger(PointageProfesseurResource.class);

    private static final String ENTITY_NAME = "pointageProfesseur";

    @Value("${spring.application.name}")
    private String applicationName;

    private final PointageProfesseurService pointageProfesseurService;

    private final PointageProfesseurRepository pointageProfesseurRepository;

    private final PaginationService paginationService;

    public PointageProfesseurResource(
            PointageProfesseurService pointageProfesseurService,
            PointageProfesseurRepository pointageProfesseurRepository, PaginationService paginationService
    ) {
        this.pointageProfesseurService = pointageProfesseurService;
        this.pointageProfesseurRepository = pointageProfesseurRepository;
        this.paginationService = paginationService;
    }

    /**
     * {@code POST  /pointage-professeurs} : Create a new pointageProfesseur.
     *
     * @param pointageProfesseurDTO the pointageProfesseurDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pointageProfesseurDTO, or with status {@code 400 (Bad Request)} if the pointageProfesseur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PointageProfesseurDTO> createPointageProfesseur(@RequestBody PointageProfesseurDTO pointageProfesseurDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save PointageProfesseur : {}", pointageProfesseurDTO);
        if (pointageProfesseurDTO.getId() != null) {
            throw new BadRequestAlertException("A new pointageProfesseur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        pointageProfesseurDTO = pointageProfesseurService.save(pointageProfesseurDTO);
        return ResponseEntity.created(new URI("/api/pointage-professeurs/" + pointageProfesseurDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, pointageProfesseurDTO.getId().toString()))
            .body(pointageProfesseurDTO);
    }

    /**
     * {@code PUT  /pointage-professeurs/:id} : Updates an existing pointageProfesseur.
     *
     * @param id the id of the pointageProfesseurDTO to save.
     * @param pointageProfesseurDTO the pointageProfesseurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pointageProfesseurDTO,
     * or with status {@code 400 (Bad Request)} if the pointageProfesseurDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pointageProfesseurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PointageProfesseurDTO> updatePointageProfesseur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PointageProfesseurDTO pointageProfesseurDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update PointageProfesseur : {}, {}", id, pointageProfesseurDTO);
        if (pointageProfesseurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pointageProfesseurDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pointageProfesseurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        pointageProfesseurDTO = pointageProfesseurService.update(pointageProfesseurDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pointageProfesseurDTO.getId().toString()))
            .body(pointageProfesseurDTO);
    }

    /**
     * {@code PATCH  /pointage-professeurs/:id} : Partial updates given fields of an existing pointageProfesseur, field will ignore if it is null
     *
     * @param id the id of the pointageProfesseurDTO to save.
     * @param pointageProfesseurDTO the pointageProfesseurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pointageProfesseurDTO,
     * or with status {@code 400 (Bad Request)} if the pointageProfesseurDTO is not valid,
     * or with status {@code 404 (Not Found)} if the pointageProfesseurDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the pointageProfesseurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PointageProfesseurDTO> partialUpdatePointageProfesseur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PointageProfesseurDTO pointageProfesseurDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update PointageProfesseur partially : {}, {}", id, pointageProfesseurDTO);
        if (pointageProfesseurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pointageProfesseurDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pointageProfesseurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PointageProfesseurDTO> result = pointageProfesseurService.partialUpdate(pointageProfesseurDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pointageProfesseurDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /pointage-professeurs} : get all the pointageProfesseurs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pointageProfesseurs in body.
     */
    @GetMapping("")
    public ResponseEntity<Map<String,Object>> getAllPointageProfesseurs(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of PointageProfesseurs");
        Page<PointageProfesseurDTO> page = pointageProfesseurService.findAll(pageable);
        Pagination pagination=paginationService.instancierPagination(page);
        Map<String,Object> response=new HashMap<>();
        response.put("data",page.getContent());
        response.put("pagination",pagination);
        return ResponseEntity.ok().body(response);
    }

    /**
     * {@code GET  /pointage-professeurs/:id} : get the "id" pointageProfesseur.
     *
     * @param id the id of the pointageProfesseurDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pointageProfesseurDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PointageProfesseurDTO> getPointageProfesseur(@PathVariable("id") Long id) {
        LOG.debug("REST request to get PointageProfesseur : {}", id);
        Optional<PointageProfesseurDTO> pointageProfesseurDTO = pointageProfesseurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pointageProfesseurDTO);
    }

    /**
     * {@code DELETE  /pointage-professeurs/:id} : delete the "id" pointageProfesseur.
     *
     * @param id the id of the pointageProfesseurDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePointageProfesseur(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete PointageProfesseur : {}", id);
        pointageProfesseurService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
