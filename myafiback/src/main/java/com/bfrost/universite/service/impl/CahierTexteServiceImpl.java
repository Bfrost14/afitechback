package com.bfrost.universite.service.impl;

import com.bfrost.universite.domain.CahierTexte;
import com.bfrost.universite.repository.CahierTexteRepository;
import com.bfrost.universite.service.CahierTexteService;
import com.bfrost.universite.service.dto.CahierTexteDTO;
import com.bfrost.universite.service.mapper.CahierTexteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link com.bfrost.universite.domain.CahierTexte}.
 */
@Service
@Transactional
public class CahierTexteServiceImpl implements CahierTexteService {

    private static final Logger LOG = LoggerFactory.getLogger(CahierTexteServiceImpl.class);

    private final CahierTexteRepository cahierTexteRepository;

    private final CahierTexteMapper cahierTexteMapper;

    public CahierTexteServiceImpl(CahierTexteRepository cahierTexteRepository, CahierTexteMapper cahierTexteMapper) {
        this.cahierTexteRepository = cahierTexteRepository;
        this.cahierTexteMapper = cahierTexteMapper;
    }

    @Override
    public CahierTexteDTO save(CahierTexteDTO cahierTexteDTO) {
        LOG.debug("Request to save CahierTexte : {}", cahierTexteDTO);
        CahierTexte cahierTexte = cahierTexteMapper.toEntity(cahierTexteDTO);
        cahierTexte = cahierTexteRepository.save(cahierTexte);
        return cahierTexteMapper.toDto(cahierTexte);
    }

    @Override
    public CahierTexteDTO update(CahierTexteDTO cahierTexteDTO) {
        LOG.debug("Request to update CahierTexte : {}", cahierTexteDTO);
        CahierTexte cahierTexte = cahierTexteMapper.toEntity(cahierTexteDTO);
        cahierTexte = cahierTexteRepository.save(cahierTexte);
        return cahierTexteMapper.toDto(cahierTexte);
    }

    @Override
    public Optional<CahierTexteDTO> partialUpdate(CahierTexteDTO cahierTexteDTO) {
        LOG.debug("Request to partially update CahierTexte : {}", cahierTexteDTO);

        return cahierTexteRepository
            .findById(cahierTexteDTO.getId())
            .map(existingCahierTexte -> {
                cahierTexteMapper.partialUpdate(existingCahierTexte, cahierTexteDTO);

                return existingCahierTexte;
            })
            .map(cahierTexteRepository::save)
            .map(cahierTexteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CahierTexteDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all CahierTextes");
        return cahierTexteRepository.findAll(pageable).map(cahierTexteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CahierTexteDTO> findOne(Long id) {
        LOG.debug("Request to get CahierTexte : {}", id);
        return cahierTexteRepository.findById(id).map(cahierTexteMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete CahierTexte : {}", id);
        cahierTexteRepository.deleteById(id);
    }
}
