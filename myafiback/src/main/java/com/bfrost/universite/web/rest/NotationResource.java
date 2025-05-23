package com.bfrost.universite.web.rest;

import com.bfrost.universite.repository.NotationRepository;
import com.bfrost.universite.service.NotationService;
import com.bfrost.universite.service.PaginationService;
import com.bfrost.universite.service.dto.NotationDTO;
import com.bfrost.universite.service.dto.Pagination;
import com.bfrost.universite.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.bfrost.universite.domain.Notation}.
 */
@RestController
@RequestMapping("/api/notations")
public class NotationResource {

    private static final Logger LOG = LoggerFactory.getLogger(NotationResource.class);

    private static final String ENTITY_NAME = "notation";

    @Value("${spring.application.name}")
    private String applicationName;

    private final NotationService notationService;

    private final NotationRepository notationRepository;

    private final PaginationService paginationService;

    public NotationResource(NotationService notationService, NotationRepository notationRepository, PaginationService paginationService) {
        this.notationService = notationService;
        this.notationRepository = notationRepository;
        this.paginationService = paginationService;
    }

    /**
     * {@code POST  /notations} : Create a new notation.
     *
     * @param notationDTO the notationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new notationDTO, or with status {@code 400 (Bad Request)} if the notation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('ENREGISTREMENT_NOTATION')")
    public ResponseEntity<NotationDTO> createNotation(@RequestBody NotationDTO notationDTO) throws URISyntaxException {
        LOG.debug("REST request to save Notation : {}", notationDTO);
        if (notationDTO.getId() != null) {
            throw new BadRequestAlertException("A new notation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        notationDTO = notationService.save(notationDTO);
        return ResponseEntity.created(new URI("/api/notations/" + notationDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, notationDTO.getId().toString()))
            .body(notationDTO);
    }

    /**
     * {@code PUT  /notations/:id} : Updates an existing notation.
     *
     * @param id the id of the notationDTO to save.
     * @param notationDTO the notationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notationDTO,
     * or with status {@code 400 (Bad Request)} if the notationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the notationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MODIFICATION_GENERALE_NOTATION')")
    public ResponseEntity<NotationDTO> updateNotation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NotationDTO notationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Notation : {}, {}", id, notationDTO);
        if (notationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, notationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!notationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        notationDTO = notationService.update(notationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, notationDTO.getId().toString()))
            .body(notationDTO);
    }

    /**
     * {@code PATCH  /notations/:id} : Partial updates given fields of an existing notation, field will ignore if it is null
     *
     * @param id the id of the notationDTO to save.
     * @param notationDTO the notationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notationDTO,
     * or with status {@code 400 (Bad Request)} if the notationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the notationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the notationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("hasAnyAuthority('MODIFICATION_NOTATION')")
    public ResponseEntity<NotationDTO> partialUpdateNotation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NotationDTO notationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Notation partially : {}, {}", id, notationDTO);
        if (notationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, notationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!notationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NotationDTO> result = notationService.partialUpdate(notationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, notationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /notations} : get all the notations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of notations in body.
     */
    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('LECTURE_LISTE_NOTATION')")
    public ResponseEntity<Map<String,Object>> getAllNotations(@org.springdoc.core.annotations.ParameterObject Pageable pageable,
                                                              @RequestParam(required = false) String etudiant,
                                                              @RequestParam(required = false) Long idCalendrier,
                                                              @RequestParam(required = false) String matiere
    ) {
        LOG.debug("REST request to get a page of Notations");
        Page<NotationDTO> page = notationService.findAll(pageable, etudiant, idCalendrier, matiere);
        Pagination pagination=paginationService.instancierPagination(page);
        Map<String,Object> response=new HashMap<>();
        response.put("data",page.getContent());
        response.put("pagination",pagination);
        return ResponseEntity.ok().body(response);
    }

    /**
     * {@code GET  /notations/:id} : get the "id" notation.
     *
     * @param id the id of the notationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the notationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('LECTURE_DETAILLE_NOTATION')")
    public ResponseEntity<NotationDTO> getNotation(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Notation : {}", id);
        Optional<NotationDTO> notationDTO = notationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(notationDTO);
    }

    /**
     * {@code DELETE  /notations/:id} : delete the "id" notation.
     *
     * @param id the id of the notationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SUPPRESSION_NOTATION')")
    public ResponseEntity<Void> deleteNotation(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Notation : {}", id);
        notationService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
