package com.bfrost.universite.service.impl;

import com.bfrost.universite.domain.Salle;
import com.bfrost.universite.repository.SalleRepository;
import com.bfrost.universite.service.SalleService;
import com.bfrost.universite.service.dto.SalleDTO;
import com.bfrost.universite.service.mapper.SalleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link com.bfrost.universite.domain.Salle}.
 */
@Service
@Transactional
public class SalleServiceImpl implements SalleService {

    private static final Logger LOG = LoggerFactory.getLogger(SalleServiceImpl.class);

    private final SalleRepository salleRepository;

    private final SalleMapper salleMapper;

    public SalleServiceImpl(SalleRepository salleRepository, SalleMapper salleMapper) {
        this.salleRepository = salleRepository;
        this.salleMapper = salleMapper;
    }

    @Override
    public SalleDTO save(SalleDTO salleDTO) {
        LOG.debug("Request to save Salle : {}", salleDTO);
        Salle salle = salleMapper.toEntity(salleDTO);
        salle = salleRepository.save(salle);
        return salleMapper.toDto(salle);
    }

    @Override
    public SalleDTO update(SalleDTO salleDTO) {
        LOG.debug("Request to update Salle : {}", salleDTO);
        Salle salle = salleMapper.toEntity(salleDTO);
        salle = salleRepository.save(salle);
        return salleMapper.toDto(salle);
    }

    @Override
    public Optional<SalleDTO> partialUpdate(SalleDTO salleDTO) {
        LOG.debug("Request to partially update Salle : {}", salleDTO);

        return salleRepository
            .findById(salleDTO.getId())
            .map(existingSalle -> {
                salleMapper.partialUpdate(existingSalle, salleDTO);

                return existingSalle;
            })
            .map(salleRepository::save)
            .map(salleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SalleDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Salles");
        return salleRepository.findAll(pageable).map(salleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SalleDTO> findOne(Long id) {
        LOG.debug("Request to get Salle : {}", id);
        return salleRepository.findById(id).map(salleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Salle : {}", id);
        salleRepository.deleteById(id);
    }
}
