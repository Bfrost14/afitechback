package com.bfrost.universite.web.rest;

import com.bfrost.universite.repository.MatiereUserRepository;
import com.bfrost.universite.service.MatiereUserService;
import com.bfrost.universite.service.PaginationService;
import com.bfrost.universite.service.dto.MatiereUserDTO;
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
 * REST controller for managing {@link com.bfrost.universite.domain.MatiereUser}.
 */
@RestController
@RequestMapping("/api/matiere-users")
public class MatiereUserResource {

    private static final Logger LOG = LoggerFactory.getLogger(MatiereUserResource.class);

    private static final String ENTITY_NAME = "matiereUser";

    @Value("${spring.application.name}")
    private String applicationName;

    private final MatiereUserService matiereUserService;

    private final MatiereUserRepository matiereUserRepository;

    private final PaginationService paginationService;

    public MatiereUserResource(
            MatiereUserService matiereUserService,
            MatiereUserRepository matiereUserRepository, PaginationService paginationService
    ) {
        this.matiereUserService = matiereUserService;
        this.matiereUserRepository = matiereUserRepository;
        this.paginationService = paginationService;
    }

    /**
     * {@code POST  /matiere-users} : Create a new matiereUser.
     *
     * @param matiereUserDTO the matiereUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new matiereUserDTO, or with status {@code 400 (Bad Request)} if the matiereUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('ENREGISTREMENT_MATIERE_USER')")
    public ResponseEntity<List<MatiereUserDTO>> createMatiereUser(@Valid @RequestBody List<MatiereUserDTO> matiereUserDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save MatiereUser : {}", matiereUserDTO);
        if (matiereUserDTO.get(0).getId() != null) {
            throw new BadRequestAlertException("A new matiereUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        matiereUserDTO = matiereUserService.save(matiereUserDTO);
        return ResponseEntity.created(new URI("/api/matiere-users/" + matiereUserDTO.get(0).getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, matiereUserDTO.get(0).getId().toString()))
            .body(matiereUserDTO);
    }

    /**
     * {@code PUT  /matiere-users/:id} : Updates an existing matiereUser.
     *
     * @param id the id of the matiereUserDTO to save.
     * @param matiereUserDTO the matiereUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matiereUserDTO,
     * or with status {@code 400 (Bad Request)} if the matiereUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the matiereUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MODIFICATION_GENERALE_MATIERE_USER')")
    public ResponseEntity<MatiereUserDTO> updateMatiereUser(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MatiereUserDTO matiereUserDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update MatiereUser : {}, {}", id, matiereUserDTO);
        if (matiereUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, matiereUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!matiereUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        matiereUserDTO = matiereUserService.update(matiereUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, matiereUserDTO.getId().toString()))
            .body(matiereUserDTO);
    }

    /**
     * {@code PATCH  /matiere-users/:id} : Partial updates given fields of an existing matiereUser, field will ignore if it is null
     *
     * @param id the id of the matiereUserDTO to save.
     * @param matiereUserDTO the matiereUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matiereUserDTO,
     * or with status {@code 400 (Bad Request)} if the matiereUserDTO is not valid,
     * or with status {@code 404 (Not Found)} if the matiereUserDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the matiereUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("hasAnyAuthority('MODIFICATION_MATIERE_USER')")
    public ResponseEntity<MatiereUserDTO> partialUpdateMatiereUser(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MatiereUserDTO matiereUserDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update MatiereUser partially : {}, {}", id, matiereUserDTO);
        if (matiereUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, matiereUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!matiereUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MatiereUserDTO> result = matiereUserService.partialUpdate(matiereUserDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, matiereUserDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /matiere-users} : get all the matiereUsers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of matiereUsers in body.
     */
    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('LECTURE_LISTE_MATIERE_USER')")
    public ResponseEntity<Map<String,Object>> getAllMatiereUsers(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(value = "professeur", required = false) String professeur,
        @RequestParam(value = "anneeScolaire", required = false) String anneeScolaire,
        @RequestParam(value = "matiere", required = false) String matiere,
        @RequestParam(value = "filiere", required = false) String filiere,
        @RequestParam(value = "semestre", required = false) String semestre
    ) {
        LOG.debug("REST request to get a page of MatiereUsers");
        Page<MatiereUserDTO> page = matiereUserService.findAll(pageable, professeur, anneeScolaire, matiere, filiere, semestre);
        Pagination pagination=paginationService.instancierPagination(page);
        Map<String,Object> response=new HashMap<>();
        response.put("data",page.getContent());
        response.put("pagination",pagination);
        return ResponseEntity.ok().body(response);
    }

    /**
     * {@code GET  /matiere-users/:id} : get the "id" matiereUser.
     *
     * @param id the id of the matiereUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the matiereUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('LECTURE_DETAILLE_MATIERE_USER')")
    public ResponseEntity<MatiereUserDTO> getMatiereUser(@PathVariable("id") Long id) {
        LOG.debug("REST request to get MatiereUser : {}", id);
        Optional<MatiereUserDTO> matiereUserDTO = matiereUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(matiereUserDTO);
    }

    /**
     * {@code DELETE  /matiere-users/:id} : delete the "id" matiereUser.
     *
     * @param id the id of the matiereUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SUPPRESSION_MATIERE_USER')")
    public ResponseEntity<Void> deleteMatiereUser(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete MatiereUser : {}", id);
        matiereUserService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
