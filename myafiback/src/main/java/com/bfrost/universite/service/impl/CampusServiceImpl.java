package com.bfrost.universite.service.impl;

import com.bfrost.universite.domain.Campus;
import com.bfrost.universite.repository.CampusRepository;
import com.bfrost.universite.service.CampusService;
import com.bfrost.universite.service.dto.CampusDTO;
import com.bfrost.universite.service.mapper.CampusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * Service Implementation for managing {@link com.bfrost.universite.domain.Campus}.
 */
@Service
@Transactional
public class CampusServiceImpl implements CampusService {

    private static final Logger LOG = LoggerFactory.getLogger(CampusServiceImpl.class);

    private final CampusRepository campusRepository;

    private final CampusMapper campusMapper;

    public CampusServiceImpl(CampusRepository campusRepository, CampusMapper campusMapper) {
        this.campusRepository = campusRepository;
        this.campusMapper = campusMapper;
    }

    @Override
    public CampusDTO save(CampusDTO campusDTO) {
        LOG.debug("Request to save Campus : {}", campusDTO);
        Campus campus = campusMapper.toEntity(campusDTO);
        campus = campusRepository.save(campus);
        return campusMapper.toDto(campus);
    }

    @Override
    public CampusDTO update(CampusDTO campusDTO) {
        LOG.debug("Request to update Campus : {}", campusDTO);
        Campus campus = campusMapper.toEntity(campusDTO);
        campus = campusRepository.save(campus);
        return campusMapper.toDto(campus);
    }

    @Override
    public Optional<CampusDTO> partialUpdate(CampusDTO campusDTO) {
        LOG.debug("Request to partially update Campus : {}", campusDTO);

        return campusRepository
            .findById(campusDTO.getId())
            .map(existingCampus -> {
                campusMapper.partialUpdate(existingCampus, campusDTO);

                return existingCampus;
            })
            .map(campusRepository::save)
            .map(campusMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CampusDTO> findAll(Pageable pageable, String nom) {
        LOG.debug("Request to get all Campuses");
        if (StringUtils.hasText(nom)){
            return campusRepository.findAllByNomContainingIgnoreCase(pageable,nom).map(campusMapper::toDto);
        }
        return campusRepository.findAll(pageable).map(campusMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CampusDTO> findOne(Long id) {
        LOG.debug("Request to get Campus : {}", id);
        return campusRepository.findById(id).map(campusMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Campus : {}", id);
        campusRepository.deleteById(id);
    }
}
