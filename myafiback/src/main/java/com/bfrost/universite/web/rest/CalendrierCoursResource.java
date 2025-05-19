package com.bfrost.universite.web.rest;

import com.bfrost.universite.repository.CalendrierCoursRepository;
import com.bfrost.universite.service.CalendrierCoursService;
import com.bfrost.universite.service.PaginationService;
import com.bfrost.universite.service.dto.CalendrierCoursDTO;
import com.bfrost.universite.service.dto.Pagination;
import com.bfrost.universite.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.bfrost.universite.domain.CalendrierCours}.
 */
@RestController
@RequestMapping("/api/calendrier-cours")
public class CalendrierCoursResource {

    private static final Logger LOG = LoggerFactory.getLogger(CalendrierCoursResource.class);

    private static final String ENTITY_NAME = "calendrierCours";

    @Value("${spring.application.name}")
    private String applicationName;

    private final CalendrierCoursService calendrierCoursService;

    private final CalendrierCoursRepository calendrierCoursRepository;

    private final PaginationService paginationService;

    public CalendrierCoursResource(CalendrierCoursService calendrierCoursService, CalendrierCoursRepository calendrierCoursRepository, PaginationService paginationService) {
        this.calendrierCoursService = calendrierCoursService;
        this.calendrierCoursRepository = calendrierCoursRepository;
        this.paginationService = paginationService;
    }

    /**
     * {@code POST  /calendrier-cours} : Create a new calendrierCours.
     *
     * @param calendrierCoursDTO the calendrierCoursDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new calendrierCoursDTO, or with status {@code 400 (Bad Request)} if the calendrierCours has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<List<CalendrierCoursDTO>> createCalendrierCours(@Valid @RequestBody List<CalendrierCoursDTO> calendrierCoursDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save CalendrierCours : {}", calendrierCoursDTO);
        if (calendrierCoursDTO.get(0).getId() != null) {
            throw new BadRequestAlertException("A new calendrierCours cannot already have an ID", ENTITY_NAME, "idexists");
        }
        calendrierCoursDTO = calendrierCoursService.save(calendrierCoursDTO);
        return ResponseEntity.created(new URI("/api/calendrier-cours/" + calendrierCoursDTO.get(0).getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, calendrierCoursDTO.get(0).getId().toString()))
            .body(calendrierCoursDTO);
    }

    /**
     * {@code PUT  /calendrier-cours/:id} : Updates an existing calendrierCours.
     *
     * @param id the id of the calendrierCoursDTO to save.
     * @param calendrierCoursDTO the calendrierCoursDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated calendrierCoursDTO,
     * or with status {@code 400 (Bad Request)} if the calendrierCoursDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the calendrierCoursDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CalendrierCoursDTO> updateCalendrierCours(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CalendrierCoursDTO calendrierCoursDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update CalendrierCours : {}, {}", id, calendrierCoursDTO);
        if (calendrierCoursDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, calendrierCoursDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!calendrierCoursRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        calendrierCoursDTO = calendrierCoursService.update(calendrierCoursDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, calendrierCoursDTO.getId().toString()))
            .body(calendrierCoursDTO);
    }

    /**
     * {@code PATCH  /calendrier-cours/:id} : Partial updates given fields of an existing calendrierCours, field will ignore if it is null
     *
     * @param id the id of the calendrierCoursDTO to save.
     * @param calendrierCoursDTO the calendrierCoursDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated calendrierCoursDTO,
     * or with status {@code 400 (Bad Request)} if the calendrierCoursDTO is not valid,
     * or with status {@code 404 (Not Found)} if the calendrierCoursDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the calendrierCoursDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CalendrierCoursDTO> partialUpdateCalendrierCours(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CalendrierCoursDTO calendrierCoursDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update CalendrierCours partially : {}, {}", id, calendrierCoursDTO);
        if (calendrierCoursDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, calendrierCoursDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!calendrierCoursRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CalendrierCoursDTO> result = calendrierCoursService.partialUpdate(calendrierCoursDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, calendrierCoursDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /calendrier-cours} : get all the calendrierCours.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of calendrierCours in body.
     */
    @GetMapping("")
    public ResponseEntity<Map<String,Object>> getAllCalendrierCours(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of CalendrierCours");
        Page<CalendrierCoursDTO> page = calendrierCoursService.findAll(pageable);
        Pagination pagination=paginationService.instancierPagination(page);
        Map<String,Object> response=new HashMap<>();
        response.put("data",page.getContent());
        response.put("pagination",pagination);
        return ResponseEntity.ok().body(response);
    }

    /**
     * {@code GET  /calendrier-cours/:id} : get the "id" calendrierCours.
     *
     * @param id the id of the calendrierCoursDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the calendrierCoursDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CalendrierCoursDTO> getCalendrierCours(@PathVariable("id") Long id) {
        LOG.debug("REST request to get CalendrierCours : {}", id);
        Optional<CalendrierCoursDTO> calendrierCoursDTO = calendrierCoursService.findOne(id);
        return ResponseUtil.wrapOrNotFound(calendrierCoursDTO);
    }

    /**
     * {@code DELETE  /calendrier-cours/:id} : delete the "id" calendrierCours.
     *
     * @param id the id of the calendrierCoursDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCalendrierCours(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete CalendrierCours : {}", id);
        calendrierCoursService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
