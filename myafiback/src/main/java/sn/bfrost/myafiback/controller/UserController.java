package sn.bfrost.myafiback.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.bfrost.myafiback.repository.UserRepository;
import sn.bfrost.myafiback.service.PaginationService;
import sn.bfrost.myafiback.service.UserService;
import sn.bfrost.myafiback.service.dto.ChangePasswordRequest;
import sn.bfrost.myafiback.service.dto.Pagination;
import sn.bfrost.myafiback.service.dto.UserDTO;
import sn.bfrost.myafiback.controller.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.net.URISyntaxException;
import java.security.Principal;
import java.util.*;

/**
 * REST controller for managing {@link sn.bfrost.myafiback.models.User}.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private static final String ENTITY_NAME = "user";

    @Value("${spring.application.name}")
    private String applicationName;

    private final UserService userService;

    private final UserRepository userRepository;

    private final PaginationService paginationService;



    @PostMapping("")
    public ResponseEntity<UserDTO> createUser(
            @RequestBody UserDTO userDTO) {

        UserDTO result = userService.create(userDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UserDTO userDTO
    ) throws URISyntaxException {
        log.debug("REST request to update User : {}, {}", id, userDTO);
        if (userDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        userDTO = userService.update(id,userDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userDTO.getId().toString()))
            .body(userDTO);
    }


    @GetMapping("")
    public ResponseEntity<Map<String,Object>> getAllUsers(@PageableDefault Pageable pageable,
                                                          @RequestParam(required = false) String matricule,
                                                          @RequestParam(required = false) String nom,
                                                          @RequestParam(required = false) String prenom,
                                                          @RequestParam(required = false) String filiere,
                                                          @RequestParam(required = false) String role,
                                                          @RequestParam(required = false) String email) {
        log.debug("REST request to get a page of Users");
        Page<UserDTO> page = userService.searchUsers(pageable,matricule,nom, prenom, filiere, email,role);
        Pagination pagination=paginationService.instancierPagination(page);
        Map<String,Object> response=new HashMap<>();
        response.put("data",page.getContent());
        response.put("pagination",pagination);
        return ResponseEntity.ok().body(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") Long id) {
        log.debug("REST request to get User : {}", id);
        Optional<UserDTO> userDTO = userService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userDTO);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserEmail(@PathVariable("email") String email) {
        log.debug("REST request to get User : {}", email);
        Optional<UserDTO> userDTO = userService.findByEmail(email);
        return ResponseUtil.wrapOrNotFound(userDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        log.debug("REST request to delete User : {}", id);
        userService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser) {

        userService.changePassword(request
                ,connectedUser);
        return ResponseEntity.ok("Mot de passe changé avec succès.");
    }



}
