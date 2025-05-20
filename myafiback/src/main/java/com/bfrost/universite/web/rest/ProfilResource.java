package com.bfrost.universite.web.rest;

import com.bfrost.universite.repository.ProfilRepository;
import com.bfrost.universite.service.ProfilService;
import com.bfrost.universite.service.PaginationService;
import com.bfrost.universite.service.dto.ProfilDTO;
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
 * REST controller for managing {@link com.bfrost.universite.domain.Profil}.
 */
@RestController
@RequestMapping("/api/profiles")
public class ProfilResource {

    private static final Logger LOG = LoggerFactory.getLogger(ProfilResource.class);

    private static final String ENTITY_NAME = "profil";

    @Value("${spring.application.name}")
    private String applicationName;

    private final ProfilService profilService;

    private final ProfilRepository profilRepository;

    private final PaginationService paginationService;

    public ProfilResource(ProfilService profilService, ProfilRepository profilRepository, PaginationService paginationService) {
        this.profilService = profilService;
        this.profilRepository = profilRepository;
        this.paginationService = paginationService;
    }

    /**
     * {@code POST  /profiles} : Create a new profil.
     *
     * @param profilDTO the profilDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new profilDTO, or with status {@code 400 (Bad Request)} if the profil has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('ENREGISTREMENT_PROFIL')")
    public ResponseEntity<ProfilDTO> createProfil(@Valid @RequestBody ProfilDTO profilDTO) throws URISyntaxException {
        LOG.debug("REST request to save Profil : {}", profilDTO);
        if (profilDTO.getId() != null) {
            throw new BadRequestAlertException("A new profil cannot already have an ID", ENTITY_NAME, "idexists");
        }
        profilDTO = profilService.save(profilDTO);
        return ResponseEntity.created(new URI("/api/profiles/" + profilDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, profilDTO.getId().toString()))
            .body(profilDTO);
    }

    /**
     * {@code PUT  /profiles/:id} : Updates an existing profil.
     *
     * @param id the id of the profilDTO to save.
     * @param profilDTO the profilDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated profilDTO,
     * or with status {@code 400 (Bad Request)} if the profilDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the profilDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MODIFICATION_GENERALE_PROFIL')")
    public ResponseEntity<ProfilDTO> updateProfil(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProfilDTO profilDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Profil : {}, {}", id, profilDTO);
        if (profilDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, profilDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!profilRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        profilDTO = profilService.update(profilDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, profilDTO.getId().toString()))
            .body(profilDTO);
    }

    /**
     * {@code PATCH  /profiles/:id} : Partial updates given fields of an existing profil, field will ignore if it is null
     *
     * @param id the id of the profilDTO to save.
     * @param profilDTO the profilDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated profilDTO,
     * or with status {@code 400 (Bad Request)} if the profilDTO is not valid,
     * or with status {@code 404 (Not Found)} if the profilDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the profilDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("hasAnyAuthority('MODIFICATION_PROFIL')")
    public ResponseEntity<ProfilDTO> partialUpdateProfil(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProfilDTO profilDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Profil partially : {}, {}", id, profilDTO);
        if (profilDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, profilDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!profilRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProfilDTO> result = profilService.partialUpdate(profilDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, profilDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /profiles} : get all the profiles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of profiles in body.
     */
    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('LECTURE_LISTE_PROFIL')")
    public ResponseEntity<Map<String,Object>> getAllProfiles(@org.springdoc.core.annotations.ParameterObject Pageable pageable,
                                                             @RequestParam(value = "nom", required = false) String nom) {
        LOG.debug("REST request to get a page of Profiles");
        Page<ProfilDTO> page = profilService.findAll(pageable, nom);
        Pagination pagination=paginationService.instancierPagination(page);
        Map<String,Object> response=new HashMap<>();
        response.put("data",page.getContent());
        response.put("pagination",pagination);
        return ResponseEntity.ok().body(response);
    }

    /**
     * {@code GET  /profiles/:id} : get the "id" profil.
     *
     * @param id the id of the profilDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the profilDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('LECTURE_DETAILLE_PROFIL')")
    public ResponseEntity<ProfilDTO> getProfil(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Profil : {}", id);
        Optional<ProfilDTO> profilDTO = profilService.findOne(id);
        return ResponseUtil.wrapOrNotFound(profilDTO);
    }

    /**
     * {@code DELETE  /profiles/:id} : delete the "id" profil.
     *
     * @param id the id of the profilDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SUPPRESSION_PROFIL')")
    public ResponseEntity<Void> deleteProfil(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Profil : {}", id);
        profilService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
