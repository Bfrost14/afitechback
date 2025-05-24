package com.bfrost.universite.service.impl;

import com.bfrost.universite.domain.Note;
import com.bfrost.universite.domain.enumeration.TypeNote;
import com.bfrost.universite.repository.NoteRepository;
import com.bfrost.universite.service.MailService;
import com.bfrost.universite.service.NoteService;
import com.bfrost.universite.service.dto.NoteDTO;
import com.bfrost.universite.service.mapper.NoteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Note}.
 */
@Service
@Transactional
public class NoteServiceImpl implements NoteService {

    private static final Logger LOG = LoggerFactory.getLogger(NoteServiceImpl.class);

    private final NoteRepository noteRepository;

    private final NoteMapper noteMapper;

    private final MailService mailService;

    public NoteServiceImpl(NoteRepository noteRepository, NoteMapper noteMapper, MailService mailService) {
        this.noteRepository = noteRepository;
        this.noteMapper = noteMapper;
        this.mailService = mailService;
    }

    @Override
    public List<NoteDTO> save(List<NoteDTO> noteDTO) {
        LOG.debug("Request to save Note : {}", noteDTO);
        return noteDTO.stream().map(noteDTO1 -> {
            Note note = noteMapper.toEntity(noteDTO1);
            note = noteRepository.save(note);
            String content = "Votre note est "+ note.getValeur()+ ".\nVous pouvez aller le vérifier dans votre espace étudiant";
            mailService.sendEmail(note.getUser().getEmail(),"NOTE "+ note.getTypeNote().name() + " " + note.getMatiereUser().getMatiere().getNom().toUpperCase(),content,false,false);

            return noteMapper.toDto(note);
        }).toList();

    }

    @Override
    public NoteDTO update(NoteDTO noteDTO) {
        LOG.debug("Request to update Note : {}", noteDTO);
        Note note = noteMapper.toEntity(noteDTO);
        note = noteRepository.save(note);
        return noteMapper.toDto(note);
    }

    @Override
    public Optional<NoteDTO> partialUpdate(NoteDTO noteDTO) {
        LOG.debug("Request to partially update Note : {}", noteDTO);

        return noteRepository
            .findById(noteDTO.getId())
            .map(existingNote -> {
                noteMapper.partialUpdate(existingNote, noteDTO);

                return existingNote;
            })
            .map(noteRepository::save)
            .map(noteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NoteDTO> findAll(Pageable pageable, String etudiant, String semestre, String matiere, String typeNote,Long idMatiereUser) {
        LOG.debug("Request to get all Notes");
        final TypeNote[] typeNote1 = {null};
        Optional.ofNullable(typeNote).ifPresent(value ->
            typeNote1[0] = TypeNote.valueOf(value)
        );
        return noteRepository.manageNote(pageable,etudiant,semestre,matiere, typeNote1[0], idMatiereUser).map(noteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NoteDTO> findOne(Long id) {
        LOG.debug("Request to get Note : {}", id);
        return noteRepository.findById(id).map(noteMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Note : {}", id);
        noteRepository.deleteById(id);
    }

    @Override
    public List<NoteDTO> findByUserId(Long id) {
        return noteRepository.findAllByUserId(id).stream().map(noteMapper::toDto).toList();
    }
}
