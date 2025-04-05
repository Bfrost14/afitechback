package sn.bfrost.myafiback.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.bfrost.myafiback.repository.NoteRepository;
import sn.bfrost.myafiback.service.PaginationService;
import sn.bfrost.myafiback.service.NoteService;
import sn.bfrost.myafiback.service.dto.Pagination;
import sn.bfrost.myafiback.service.dto.NoteDTO;
import sn.bfrost.myafiback.controller.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

    private static final Logger log = LoggerFactory.getLogger(NoteController.class);

    @Value("${spring.application.name}")
    private String applicationName;

    private final NoteService noteService;

    private final PaginationService paginationService;

    @GetMapping("/private/all")
    public ResponseEntity<Map<String,Object>> getAllNote(@PageableDefault Pageable pageable,
                                                         @RequestParam(required = false) String matiere,
                                                         @RequestParam(required = false) String nom,
                                                         @RequestParam(required = false) String prenom,
                                                         @RequestParam(required = false) String filiere,
                                                         @RequestParam(required = false) String semestre){
    Page<NoteDTO> page = noteService.getAllNote(pageable, matiere, nom, prenom, semestre,filiere);
        Pagination pagination=paginationService.instancierPagination(page);
        Map<String,Object> response=new HashMap<>();
        response.put("data",page.getContent());
        response.put("pagination",pagination);
        return ResponseEntity.ok().body(response);
    }



    @GetMapping("/etudiant/all")
    public ResponseEntity<Map<String,Object>> getAllNoteByClient(@PageableDefault Pageable pageable,
                                                                 @RequestParam String email,
                                                                 @RequestParam(required = false) String matiere,
                                                                 @RequestParam(required = false) String semestre) {
        Page<NoteDTO> page = noteService.getAllNoteForEtudiant(pageable, email, matiere, semestre);
        Pagination pagination=paginationService.instancierPagination(page);
        Map<String,Object> response=new HashMap<>();
        response.put("data",page.getContent());
        response.put("pagination",pagination);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/private/{id}")
    public ResponseEntity<NoteDTO> getNoteById(@PathVariable Long id) {
        log.debug("REST request to get Note : {}", id);
        Optional<NoteDTO> noteDTO = noteService.getNoteById(id);
        return ResponseUtil.wrapOrNotFound(noteDTO);
    }

    // Endpoint pour enregistrer un nouveau note
    @PostMapping("/private")
    public ResponseEntity<NoteDTO> create(@RequestBody NoteDTO noteDTO) {
        NoteDTO note = noteService.saveNote(noteDTO);
        return ResponseEntity.ok(note);
    }

    @PostMapping("/private/all")
    public ResponseEntity<List<NoteDTO>> createAll(@RequestBody List<NoteDTO> notes) {
        List<NoteDTO> note = noteService.saveAllNote(notes);
        return ResponseEntity.ok(note);
    }

    // Endpoint pour mettre à jour un note
    @PutMapping("/private/update")
    public ResponseEntity<NoteDTO> updateNote(@RequestBody NoteDTO noteDTO) {
        log.debug("REST request to update Note : {}", noteDTO);
        if (noteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", "note", "idnull");
        }
        Optional<NoteDTO> updatedNote = noteService.updateNote(noteDTO);
        return ResponseUtil.wrapOrNotFound(updatedNote);
    }

    @DeleteMapping("/private/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable("id") Long id) {
        log.debug("REST request to delete NoteUser : {}", id);
        noteService.deleteNote(id);
        return ResponseEntity.noContent()
                    .build();
    }


}