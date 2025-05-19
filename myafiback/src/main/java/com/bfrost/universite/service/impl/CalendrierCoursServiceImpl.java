package com.bfrost.universite.service.impl;

import com.bfrost.universite.domain.CalendrierCours;
import com.bfrost.universite.repository.CalendrierCoursRepository;
import com.bfrost.universite.service.CalendrierCoursService;
import com.bfrost.universite.service.dto.CalendrierCoursDTO;
import com.bfrost.universite.service.mapper.CalendrierCoursMapper;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CalendrierCours}.
 */
@Service
@Transactional
public class CalendrierCoursServiceImpl implements CalendrierCoursService {

    private static final Logger LOG = LoggerFactory.getLogger(CalendrierCoursServiceImpl.class);

    private final CalendrierCoursRepository calendrierCoursRepository;

    private final CalendrierCoursMapper calendrierCoursMapper;

    public CalendrierCoursServiceImpl(CalendrierCoursRepository calendrierCoursRepository, CalendrierCoursMapper calendrierCoursMapper) {
        this.calendrierCoursRepository = calendrierCoursRepository;
        this.calendrierCoursMapper = calendrierCoursMapper;
    }

    @Override
    public List<CalendrierCoursDTO> save(List<CalendrierCoursDTO> calendrierCoursDTO) {
        LOG.debug("Request to save CalendrierCours : {}", calendrierCoursDTO);
        return calendrierCoursDTO.stream().map(calendrierCoursDTO1 -> {
            CalendrierCours calendrierCours = calendrierCoursMapper.toEntity(calendrierCoursDTO1);
            calendrierCours = calendrierCoursRepository.save(calendrierCours);
            return calendrierCoursMapper.toDto(calendrierCours);
        }).toList();

    }

    @Override
    public CalendrierCoursDTO update(CalendrierCoursDTO calendrierCoursDTO) {
        LOG.debug("Request to update CalendrierCours : {}", calendrierCoursDTO);
        CalendrierCours calendrierCours = calendrierCoursMapper.toEntity(calendrierCoursDTO);
        calendrierCours = calendrierCoursRepository.save(calendrierCours);
        return calendrierCoursMapper.toDto(calendrierCours);
    }

    @Override
    public Optional<CalendrierCoursDTO> partialUpdate(CalendrierCoursDTO calendrierCoursDTO) {
        LOG.debug("Request to partially update CalendrierCours : {}", calendrierCoursDTO);

        return calendrierCoursRepository
            .findById(calendrierCoursDTO.getId())
            .map(existingCalendrierCours -> {
                calendrierCoursMapper.partialUpdate(existingCalendrierCours, calendrierCoursDTO);

                return existingCalendrierCours;
            })
            .map(calendrierCoursRepository::save)
            .map(calendrierCoursMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CalendrierCoursDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all CalendrierCours");
        return calendrierCoursRepository.findAll(pageable).map(calendrierCoursMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CalendrierCoursDTO> findOne(Long id) {
        LOG.debug("Request to get CalendrierCours : {}", id);
        return calendrierCoursRepository.findById(id).map(calendrierCoursMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete CalendrierCours : {}", id);
        calendrierCoursRepository.deleteById(id);
    }
}
