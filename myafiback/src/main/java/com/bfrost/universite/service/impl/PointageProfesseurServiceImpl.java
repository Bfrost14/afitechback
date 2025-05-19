package com.bfrost.universite.service.impl;

import com.bfrost.universite.domain.PointageProfesseur;
import com.bfrost.universite.repository.PointageProfesseurRepository;
import com.bfrost.universite.service.PointageProfesseurService;
import com.bfrost.universite.service.dto.PointageProfesseurDTO;
import com.bfrost.universite.service.mapper.PointageProfesseurMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link com.bfrost.universite.domain.PointageProfesseur}.
 */
@Service
@Transactional
public class PointageProfesseurServiceImpl implements PointageProfesseurService {

    private static final Logger LOG = LoggerFactory.getLogger(PointageProfesseurServiceImpl.class);

    private final PointageProfesseurRepository pointageProfesseurRepository;

    private final PointageProfesseurMapper pointageProfesseurMapper;

    public PointageProfesseurServiceImpl(
        PointageProfesseurRepository pointageProfesseurRepository,
        PointageProfesseurMapper pointageProfesseurMapper
    ) {
        this.pointageProfesseurRepository = pointageProfesseurRepository;
        this.pointageProfesseurMapper = pointageProfesseurMapper;
    }

    @Override
    public PointageProfesseurDTO save(PointageProfesseurDTO pointageProfesseurDTO) {
        LOG.debug("Request to save PointageProfesseur : {}", pointageProfesseurDTO);
        PointageProfesseur pointageProfesseur = pointageProfesseurMapper.toEntity(pointageProfesseurDTO);
        pointageProfesseur = pointageProfesseurRepository.save(pointageProfesseur);
        return pointageProfesseurMapper.toDto(pointageProfesseur);
    }

    @Override
    public PointageProfesseurDTO update(PointageProfesseurDTO pointageProfesseurDTO) {
        LOG.debug("Request to update PointageProfesseur : {}", pointageProfesseurDTO);
        PointageProfesseur pointageProfesseur = pointageProfesseurMapper.toEntity(pointageProfesseurDTO);
        pointageProfesseur = pointageProfesseurRepository.save(pointageProfesseur);
        return pointageProfesseurMapper.toDto(pointageProfesseur);
    }

    @Override
    public Optional<PointageProfesseurDTO> partialUpdate(PointageProfesseurDTO pointageProfesseurDTO) {
        LOG.debug("Request to partially update PointageProfesseur : {}", pointageProfesseurDTO);

        return pointageProfesseurRepository
            .findById(pointageProfesseurDTO.getId())
            .map(existingPointageProfesseur -> {
                pointageProfesseurMapper.partialUpdate(existingPointageProfesseur, pointageProfesseurDTO);

                return existingPointageProfesseur;
            })
            .map(pointageProfesseurRepository::save)
            .map(pointageProfesseurMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PointageProfesseurDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all PointageProfesseurs");
        return pointageProfesseurRepository.findAll(pageable).map(pointageProfesseurMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PointageProfesseurDTO> findOne(Long id) {
        LOG.debug("Request to get PointageProfesseur : {}", id);
        return pointageProfesseurRepository.findById(id).map(pointageProfesseurMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete PointageProfesseur : {}", id);
        pointageProfesseurRepository.deleteById(id);
    }
}
