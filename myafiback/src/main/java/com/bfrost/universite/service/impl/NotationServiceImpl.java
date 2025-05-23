package com.bfrost.universite.service.impl;

import com.bfrost.universite.domain.Notation;
import com.bfrost.universite.repository.NotationRepository;
import com.bfrost.universite.service.NotationService;
import com.bfrost.universite.service.dto.NotationDTO;
import com.bfrost.universite.service.mapper.NotationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link com.bfrost.universite.domain.Notation}.
 */
@Service
@Transactional
public class NotationServiceImpl implements NotationService {

    private static final Logger LOG = LoggerFactory.getLogger(NotationServiceImpl.class);

    private final NotationRepository notationRepository;

    private final NotationMapper notationMapper;

    public NotationServiceImpl(NotationRepository notationRepository, NotationMapper notationMapper) {
        this.notationRepository = notationRepository;
        this.notationMapper = notationMapper;
    }

    @Override
    public NotationDTO save(NotationDTO notationDTO) {
        LOG.debug("Request to save Notation : {}", notationDTO);
        Notation notation = notationMapper.toEntity(notationDTO);
        notation = notationRepository.save(notation);
        return notationMapper.toDto(notation);
    }

    @Override
    public NotationDTO update(NotationDTO notationDTO) {
        LOG.debug("Request to update Notation : {}", notationDTO);
        Notation notation = notationMapper.toEntity(notationDTO);
        notation = notationRepository.save(notation);
        return notationMapper.toDto(notation);
    }

    @Override
    public Optional<NotationDTO> partialUpdate(NotationDTO notationDTO) {
        LOG.debug("Request to partially update Notation : {}", notationDTO);

        return notationRepository
            .findById(notationDTO.getId())
            .map(existingNotation -> {
                notationMapper.partialUpdate(existingNotation, notationDTO);

                return existingNotation;
            })
            .map(notationRepository::save)
            .map(notationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotationDTO> findAll(Pageable pageable, String etudiant, Long idCalendrier, String matiere) {
        LOG.debug("Request to get all Notations");
        return notationRepository.manageNotation(pageable,etudiant,idCalendrier,matiere).map(notationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NotationDTO> findOne(Long id) {
        LOG.debug("Request to get Notation : {}", id);
        return notationRepository.findById(id).map(notationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Notation : {}", id);
        notationRepository.deleteById(id);
    }
}
