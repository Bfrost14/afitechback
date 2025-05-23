package com.bfrost.universite.web.rest;

import com.bfrost.universite.repository.CahierTexteRepository;
import com.bfrost.universite.service.CahierTexteService;
import com.bfrost.universite.service.PaginationService;
import com.bfrost.universite.service.dto.CahierTexteDTO;
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
 * REST controller for managing {@link com.bfrost.universite.domain.CahierTexte}.
 */
@RestController
@RequestMapping("/api/cahier-textes")
public class CahierTexteResource {

    private static final Logger LOG = LoggerFactory.getLogger(CahierTexteResource.class);

    private static final String ENTITY_NAME = "cahierTexte";

    @Value("${spring.application.name}")
    private String applicationName;

    private final CahierTexteService cahierTexteService;

    private final CahierTexteRepository cahierTexteRepository;

    private final PaginationService paginationService;


    public CahierTexteResource(CahierTexteService cahierTexteService, CahierTexteRepository cahierTexteRepository, PaginationService paginationService) {
        this.cahierTexteService = cahierTexteService;
        this.cahierTexteRepository = cahierTexteRepository;
        this.paginationService = paginationService;
    }

    /**
     * {@code POST  /cahier-textes} : Create a new cahierTexte.
     *
     * @param cahierTexteDTO the cahierTexteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cahierTexteDTO, or with status {@code 400 (Bad Request)} if the cahierTexte has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('ENREGISTREMENT_CAHIER_TEXTE')")
    public ResponseEntity<CahierTexteDTO> createCahierTexte(@Valid @RequestBody CahierTexteDTO cahierTexteDTO) throws URISyntaxException {
        LOG.debug("REST request to save CahierTexte : {}", cahierTexteDTO);
        if (cahierTexteDTO.getId() != null) {
            throw new BadRequestAlertException("A new cahierTexte cannot already have an ID", ENTITY_NAME, "idexists");
        }
        cahierTexteDTO = cahierTexteService.save(cahierTexteDTO);
        return ResponseEntity.created(new URI("/api/cahier-textes/" + cahierTexteDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, cahierTexteDTO.getId().toString()))
            .body(cahierTexteDTO);
    }

    /**
     * {@code PUT  /cahier-textes/:id} : Updates an existing cahierTexte.
     *
     * @param id the id of the cahierTexteDTO to save.
     * @param cahierTexteDTO the cahierTexteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cahierTexteDTO,
     * or with status {@code 400 (Bad Request)} if the cahierTexteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cahierTexteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MODIFICATION_CAHIER_TEXTE')")
    public ResponseEntity<CahierTexteDTO> updateCahierTexte(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CahierTexteDTO cahierTexteDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update CahierTexte : {}, {}", id, cahierTexteDTO);
        if (cahierTexteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cahierTexteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cahierTexteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cahierTexteDTO = cahierTexteService.update(cahierTexteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cahierTexteDTO.getId().toString()))
            .body(cahierTexteDTO);
    }

    /**
     * {@code PATCH  /cahier-textes/:id} : Partial updates given fields of an existing cahierTexte, field will ignore if it is null
     *
     * @param id the id of the cahierTexteDTO to save.
     * @param cahierTexteDTO the cahierTexteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cahierTexteDTO,
     * or with status {@code 400 (Bad Request)} if the cahierTexteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cahierTexteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cahierTexteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("hasAnyAuthority('MODIFICATION_CAHIER_TEXTE')")
    public ResponseEntity<CahierTexteDTO> partialUpdateCahierTexte(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CahierTexteDTO cahierTexteDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update CahierTexte partially : {}, {}", id, cahierTexteDTO);
        if (cahierTexteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cahierTexteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cahierTexteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CahierTexteDTO> result = cahierTexteService.partialUpdate(cahierTexteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cahierTexteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cahier-textes} : get all the cahierTextes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cahierTextes in body.
     */
    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('LECTURE_LISTE_CAHIER_TEXTE')")
    public ResponseEntity<Map<String,Object>> getAllCahierTextes(@org.springdoc.core.annotations.ParameterObject Pageable pageable,
                                                                 @RequestParam(required = false) String professeur,
                                                                 @RequestParam(required = false) Long idCalendrier,
                                                                 @RequestParam(required = false) String matiere

                                                                 ) {
        LOG.debug("REST request to get a page of CahierTextes");
        Page<CahierTexteDTO> page = cahierTexteService.findAll(pageable, professeur, idCalendrier, matiere);
        Pagination pagination=paginationService.instancierPagination(page);
        Map<String,Object> response=new HashMap<>();
        response.put("data",page.getContent());
        response.put("pagination",pagination);
        return ResponseEntity.ok().body(response);
    }

    /**
     * {@code GET  /cahier-textes/:id} : get the "id" cahierTexte.
     *
     * @param id the id of the cahierTexteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cahierTexteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('LECTURE_DETAILLE_CAHIER_TEXTE')")
    public ResponseEntity<CahierTexteDTO> getCahierTexte(@PathVariable("id") Long id) {
        LOG.debug("REST request to get CahierTexte : {}", id);
        Optional<CahierTexteDTO> cahierTexteDTO = cahierTexteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cahierTexteDTO);
    }

    /**
     * {@code DELETE  /cahier-textes/:id} : delete the "id" cahierTexte.
     *
     * @param id the id of the cahierTexteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SUPPRESSION_CAHIER_TEXTE')")
    public ResponseEntity<Void> deleteCahierTexte(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete CahierTexte : {}", id);
        cahierTexteService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
