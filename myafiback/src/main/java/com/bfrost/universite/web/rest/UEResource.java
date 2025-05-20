package com.bfrost.universite.web.rest;

import com.bfrost.universite.repository.UERepository;
import com.bfrost.universite.service.PaginationService;
import com.bfrost.universite.service.UEService;
import com.bfrost.universite.service.dto.Pagination;
import com.bfrost.universite.service.dto.UEDTO;
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
 * REST controller for managing {@link com.bfrost.universite.domain.UE}.
 */
@RestController
@RequestMapping("/api/ues")
public class UEResource {

    private static final Logger LOG = LoggerFactory.getLogger(UEResource.class);

    private static final String ENTITY_NAME = "uE";

    @Value("${spring.application.name}")
    private String applicationName;

    private final UEService uEService;

    private final UERepository uERepository;

    private final PaginationService paginationService;

    public UEResource(UEService uEService, UERepository uERepository, PaginationService paginationService) {
        this.uEService = uEService;
        this.uERepository = uERepository;
        this.paginationService = paginationService;
    }

    /**
     * {@code POST  /ues} : Create a new uE.
     *
     * @param uEDTO the uEDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new uEDTO, or with status {@code 400 (Bad Request)} if the uE has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('ENREGISTREMENT_UE')")
    public ResponseEntity<UEDTO> createUE(@Valid @RequestBody UEDTO uEDTO) throws URISyntaxException {
        LOG.debug("REST request to save UE : {}", uEDTO);
        if (uEDTO.getId() != null) {
            throw new BadRequestAlertException("A new uE cannot already have an ID", ENTITY_NAME, "idexists");
        }
        uEDTO = uEService.save(uEDTO);
        return ResponseEntity.created(new URI("/api/ues/" + uEDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, uEDTO.getId().toString()))
            .body(uEDTO);
    }

    /**
     * {@code PUT  /ues/:id} : Updates an existing uE.
     *
     * @param id the id of the uEDTO to save.
     * @param uEDTO the uEDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uEDTO,
     * or with status {@code 400 (Bad Request)} if the uEDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the uEDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MODIFICATION_GENERALE_UE')")
    public ResponseEntity<UEDTO> updateUE(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody UEDTO uEDTO)
        throws URISyntaxException {
        LOG.debug("REST request to update UE : {}, {}", id, uEDTO);
        if (uEDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, uEDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!uERepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        uEDTO = uEService.update(uEDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, uEDTO.getId().toString()))
            .body(uEDTO);
    }

    /**
     * {@code PATCH  /ues/:id} : Partial updates given fields of an existing uE, field will ignore if it is null
     *
     * @param id the id of the uEDTO to save.
     * @param uEDTO the uEDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uEDTO,
     * or with status {@code 400 (Bad Request)} if the uEDTO is not valid,
     * or with status {@code 404 (Not Found)} if the uEDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the uEDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("hasAnyAuthority('MODIFICATION_UE')")
    public ResponseEntity<UEDTO> partialUpdateUE(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UEDTO uEDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update UE partially : {}, {}", id, uEDTO);
        if (uEDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, uEDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!uERepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UEDTO> result = uEService.partialUpdate(uEDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, uEDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ues} : get all the uES.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of uES in body.
     */
    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('LECTURE_LISTE_UE')")
    public ResponseEntity<Map<String,Object>> getAllUES(@org.springdoc.core.annotations.ParameterObject Pageable pageable,
                                                        @RequestParam(value = "nom", required = false) String nom) {
        LOG.debug("REST request to get a page of UES");
        Page<UEDTO> page = uEService.findAll(pageable, nom);
        Pagination pagination=paginationService.instancierPagination(page);
        Map<String,Object> response=new HashMap<>();
        response.put("data",page.getContent());
        response.put("pagination",pagination);
        return ResponseEntity.ok().body(response);
    }

    /**
     * {@code GET  /ues/:id} : get the "id" uE.
     *
     * @param id the id of the uEDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the uEDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('LECTURE_DETAILLE_UE')")
    public ResponseEntity<UEDTO> getUE(@PathVariable("id") Long id) {
        LOG.debug("REST request to get UE : {}", id);
        Optional<UEDTO> uEDTO = uEService.findOne(id);
        return ResponseUtil.wrapOrNotFound(uEDTO);
    }

    /**
     * {@code DELETE  /ues/:id} : delete the "id" uE.
     *
     * @param id the id of the uEDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SUPPRESSION_UE')")
    public ResponseEntity<Void> deleteUE(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete UE : {}", id);
        uEService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
