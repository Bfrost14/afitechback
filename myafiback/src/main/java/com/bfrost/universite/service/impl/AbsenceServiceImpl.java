package com.bfrost.universite.service.impl;

import com.bfrost.universite.domain.Absence;
import com.bfrost.universite.repository.AbsenceRepository;
import com.bfrost.universite.service.AbsenceService;
import com.bfrost.universite.service.dto.AbsenceDTO;
import com.bfrost.universite.service.mapper.AbsenceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Absence}.
 */
@Service
@Transactional
public class AbsenceServiceImpl implements AbsenceService {

    private static final Logger LOG = LoggerFactory.getLogger(AbsenceServiceImpl.class);

    private final AbsenceRepository absenceRepository;

    private final AbsenceMapper absenceMapper;

    public AbsenceServiceImpl(AbsenceRepository absenceRepository, AbsenceMapper absenceMapper) {
        this.absenceRepository = absenceRepository;
        this.absenceMapper = absenceMapper;
    }

    @Override
    public List<AbsenceDTO> save(List<AbsenceDTO> absenceDTO) {
        LOG.debug("Request to save Absence : {}", absenceDTO);
        return absenceDTO.stream().map(absenceDTO1 -> {
            Absence absence = absenceMapper.toEntity(absenceDTO1);
            absence = absenceRepository.save(absence);
            return absenceMapper.toDto(absence);
        }).toList();

    }

    @Override
    public AbsenceDTO update(AbsenceDTO absenceDTO) {
        LOG.debug("Request to update Absence : {}", absenceDTO);
        Absence absence = absenceMapper.toEntity(absenceDTO);
        absence = absenceRepository.save(absence);
        return absenceMapper.toDto(absence);
    }

    @Override
    public Optional<AbsenceDTO> partialUpdate(AbsenceDTO absenceDTO) {
        LOG.debug("Request to partially update Absence : {}", absenceDTO);

        return absenceRepository
            .findById(absenceDTO.getId())
            .map(existingAbsence -> {
                absenceMapper.partialUpdate(existingAbsence, absenceDTO);

                return existingAbsence;
            })
            .map(absenceRepository::save)
            .map(absenceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AbsenceDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Absences");
        return absenceRepository.findAll(pageable).map(absenceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AbsenceDTO> findOne(Long id) {
        LOG.debug("Request to get Absence : {}", id);
        return absenceRepository.findById(id).map(absenceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Absence : {}", id);
        absenceRepository.deleteById(id);
    }
}
