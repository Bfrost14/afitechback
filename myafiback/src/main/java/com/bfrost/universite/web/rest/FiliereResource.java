package com.bfrost.universite.web.rest;

import com.bfrost.universite.repository.FiliereRepository;
import com.bfrost.universite.service.FiliereService;
import com.bfrost.universite.service.PaginationService;
import com.bfrost.universite.service.dto.FiliereDTO;
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
 * REST controller for managing {@link com.bfrost.universite.domain.Filiere}.
 */
@RestController
@RequestMapping("/api/filieres")
public class FiliereResource {

    private static final Logger LOG = LoggerFactory.getLogger(FiliereResource.class);

    private static final String ENTITY_NAME = "filiere";

    @Value("${spring.application.name}")
    private String applicationName;

    private final FiliereService filiereService;

    private final FiliereRepository filiereRepository;

    private final PaginationService paginationService;

    public FiliereResource(FiliereService filiereService, FiliereRepository filiereRepository, PaginationService paginationService) {
        this.filiereService = filiereService;
        this.filiereRepository = filiereRepository;
        this.paginationService = paginationService;
    }

    /**
     * {@code POST  /filieres} : Create a new filiere.
     *
     * @param filiereDTO the filiereDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new filiereDTO, or with status {@code 400 (Bad Request)} if the filiere has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('ENREGISTREMENT_FILIERE')")
    public ResponseEntity<FiliereDTO> createFiliere(@Valid @RequestBody FiliereDTO filiereDTO) throws URISyntaxException {
        LOG.debug("REST request to save Filiere : {}", filiereDTO);
        if (filiereDTO.getId() != null) {
            throw new BadRequestAlertException("A new filiere cannot already have an ID", ENTITY_NAME, "idexists");
        }
        filiereDTO = filiereService.save(filiereDTO);
        return ResponseEntity.created(new URI("/api/filieres/" + filiereDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, filiereDTO.getId().toString()))
            .body(filiereDTO);
    }

    /**
     * {@code PUT  /filieres/:id} : Updates an existing filiere.
     *
     * @param id the id of the filiereDTO to save.
     * @param filiereDTO the filiereDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated filiereDTO,
     * or with status {@code 400 (Bad Request)} if the filiereDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the filiereDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MODIFICATION_GENERALE_FILIERE')")
    public ResponseEntity<FiliereDTO> updateFiliere(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FiliereDTO filiereDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Filiere : {}, {}", id, filiereDTO);
        if (filiereDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, filiereDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!filiereRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        filiereDTO = filiereService.update(filiereDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, filiereDTO.getId().toString()))
            .body(filiereDTO);
    }

    /**
     * {@code PATCH  /filieres/:id} : Partial updates given fields of an existing filiere, field will ignore if it is null
     *
     * @param id the id of the filiereDTO to save.
     * @param filiereDTO the filiereDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated filiereDTO,
     * or with status {@code 400 (Bad Request)} if the filiereDTO is not valid,
     * or with status {@code 404 (Not Found)} if the filiereDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the filiereDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("hasAnyAuthority('MODIFICATION_FILIERE')")
    public ResponseEntity<FiliereDTO> partialUpdateFiliere(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FiliereDTO filiereDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Filiere partially : {}, {}", id, filiereDTO);
        if (filiereDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, filiereDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!filiereRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FiliereDTO> result = filiereService.partialUpdate(filiereDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, filiereDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /filieres} : get all the filieres.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of filieres in body.
     */
    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('LECTURE_LISTE_FILIERE')")
    public ResponseEntity<Map<String,Object>> getAllFilieres(@org.springdoc.core.annotations.ParameterObject Pageable pageable,
                                                             @RequestParam(value = "nom", required = false) String nom) {
        LOG.debug("REST request to get a page of Filieres");
        Page<FiliereDTO> page = filiereService.findAll(pageable, nom);
        Pagination pagination=paginationService.instancierPagination(page);
        Map<String,Object> response=new HashMap<>();
        response.put("data",page.getContent());
        response.put("pagination",pagination);
        return ResponseEntity.ok().body(response);
    }

    /**
     * {@code GET  /filieres/:id} : get the "id" filiere.
     *
     * @param id the id of the filiereDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the filiereDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('LECTURE_DETAILLE_FILIERE')")
    public ResponseEntity<FiliereDTO> getFiliere(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Filiere : {}", id);
        Optional<FiliereDTO> filiereDTO = filiereService.findOne(id);
        return ResponseUtil.wrapOrNotFound(filiereDTO);
    }

    /**
     * {@code DELETE  /filieres/:id} : delete the "id" filiere.
     *
     * @param id the id of the filiereDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SUPPRESSION_FILIERE')")
    public ResponseEntity<Void> deleteFiliere(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Filiere : {}", id);
        filiereService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
