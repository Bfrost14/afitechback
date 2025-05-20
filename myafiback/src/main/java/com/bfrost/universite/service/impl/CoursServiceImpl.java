package com.bfrost.universite.service.impl;

import com.bfrost.universite.domain.Cours;
import com.bfrost.universite.repository.CoursRepository;
import com.bfrost.universite.service.CoursService;
import com.bfrost.universite.service.dto.CoursDTO;
import com.bfrost.universite.service.mapper.CoursMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link com.bfrost.universite.domain.Cours}.
 */
@Service
@Transactional
public class CoursServiceImpl implements CoursService {

    private static final Logger LOG = LoggerFactory.getLogger(CoursServiceImpl.class);

    private final CoursRepository coursRepository;

    private final CoursMapper coursMapper;

    public CoursServiceImpl(CoursRepository coursRepository, CoursMapper coursMapper) {
        this.coursRepository = coursRepository;
        this.coursMapper = coursMapper;
    }

    @Override
    public List<CoursDTO> save(List<CoursDTO> coursDTO) {
        LOG.debug("Request to save Cours : {}", coursDTO);
        return coursDTO.stream().map(coursDTO1 -> {
            Cours cours = coursMapper.toEntity(coursDTO1);
            cours = coursRepository.save(cours);
            return coursMapper.toDto(cours);
        }).toList();

    }

    @Override
    public CoursDTO update(CoursDTO coursDTO) {
        LOG.debug("Request to update Cours : {}", coursDTO);
        Cours cours = coursMapper.toEntity(coursDTO);
        cours = coursRepository.save(cours);
        return coursMapper.toDto(cours);
    }

    @Override
    public Optional<CoursDTO> partialUpdate(CoursDTO coursDTO) {
        LOG.debug("Request to partially update Cours : {}", coursDTO);

        return coursRepository
            .findById(coursDTO.getId())
            .map(existingCours -> {
                coursMapper.partialUpdate(existingCours, coursDTO);

                return existingCours;
            })
            .map(coursRepository::save)
            .map(coursMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CoursDTO> findAll(Pageable pageable, String intitule, String professeur) {
        LOG.debug("Request to get all Cours");
        return coursRepository.findAll(pageable).map(coursMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CoursDTO> findOne(Long id) {
        LOG.debug("Request to get Cours : {}", id);
        return coursRepository.findById(id).map(coursMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Cours : {}", id);
        coursRepository.deleteById(id);
    }
}
