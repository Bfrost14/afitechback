package com.bfrost.universite.service.impl;

import com.bfrost.universite.domain.AnneeScolaire;
import com.bfrost.universite.repository.AnneeScolaireRepository;
import com.bfrost.universite.service.AnneeScolaireService;
import com.bfrost.universite.service.dto.AnneeScolaireDTO;
import com.bfrost.universite.service.mapper.AnneeScolaireMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AnneeScolaire}.
 */
@Service
@Transactional
public class AnneeScolaireServiceImpl implements AnneeScolaireService {

    private static final Logger LOG = LoggerFactory.getLogger(AnneeScolaireServiceImpl.class);

    private final AnneeScolaireRepository anneeScolaireRepository;

    private final AnneeScolaireMapper anneeScolaireMapper;

    public AnneeScolaireServiceImpl(AnneeScolaireRepository anneeScolaireRepository, AnneeScolaireMapper anneeScolaireMapper) {
        this.anneeScolaireRepository = anneeScolaireRepository;
        this.anneeScolaireMapper = anneeScolaireMapper;
    }

    @Override
    public AnneeScolaireDTO save(AnneeScolaireDTO anneeScolaireDTO) {
        LOG.debug("Request to save AnneeScolaire : {}", anneeScolaireDTO);
        AnneeScolaire anneeScolaire = anneeScolaireMapper.toEntity(anneeScolaireDTO);
        anneeScolaire = anneeScolaireRepository.save(anneeScolaire);
        return anneeScolaireMapper.toDto(anneeScolaire);
    }

    @Override
    public AnneeScolaireDTO update(AnneeScolaireDTO anneeScolaireDTO) {
        LOG.debug("Request to update AnneeScolaire : {}", anneeScolaireDTO);
        AnneeScolaire anneeScolaire = anneeScolaireMapper.toEntity(anneeScolaireDTO);
        anneeScolaire = anneeScolaireRepository.save(anneeScolaire);
        return anneeScolaireMapper.toDto(anneeScolaire);
    }

    @Override
    public Optional<AnneeScolaireDTO> partialUpdate(AnneeScolaireDTO anneeScolaireDTO) {
        LOG.debug("Request to partially update AnneeScolaire : {}", anneeScolaireDTO);

        return anneeScolaireRepository
            .findById(anneeScolaireDTO.getId())
            .map(existingAnneeScolaire -> {
                anneeScolaireMapper.partialUpdate(existingAnneeScolaire, anneeScolaireDTO);

                return existingAnneeScolaire;
            })
            .map(anneeScolaireRepository::save)
            .map(anneeScolaireMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnneeScolaireDTO> findAll(Pageable pageable, String nom) {
        LOG.debug("Request to get all AnneeScolaires");
        if(StringUtils.hasText(nom)){
            return anneeScolaireRepository.findAllByNomContainingIgnoreCase(pageable,nom).map(anneeScolaireMapper::toDto);
        }
        return anneeScolaireRepository.findAll(pageable).map(anneeScolaireMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AnneeScolaireDTO> findOne(Long id) {
        LOG.debug("Request to get AnneeScolaire : {}", id);
        return anneeScolaireRepository.findById(id).map(anneeScolaireMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete AnneeScolaire : {}", id);
        anneeScolaireRepository.deleteById(id);
    }
}
