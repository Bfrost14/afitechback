package com.bfrost.universite.service.impl;

import com.bfrost.universite.domain.Matiere;
import com.bfrost.universite.repository.MatiereRepository;
import com.bfrost.universite.service.MatiereService;
import com.bfrost.universite.service.dto.MatiereDTO;
import com.bfrost.universite.service.mapper.MatiereMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link com.bfrost.universite.domain.Matiere}.
 */
@Service
@Transactional
public class MatiereServiceImpl implements MatiereService {

    private static final Logger LOG = LoggerFactory.getLogger(MatiereServiceImpl.class);

    private final MatiereRepository matiereRepository;

    private final MatiereMapper matiereMapper;

    public MatiereServiceImpl(MatiereRepository matiereRepository, MatiereMapper matiereMapper) {
        this.matiereRepository = matiereRepository;
        this.matiereMapper = matiereMapper;
    }

    @Override
    public List<MatiereDTO> save(List<MatiereDTO> matiereDTO) {
        LOG.debug("Request to save Matiere : {}", matiereDTO);
        return matiereDTO.stream().map(
                matiereDTO1 -> {
                    Matiere matiere = matiereMapper.toEntity(matiereDTO1);
                    matiere = matiereRepository.save(matiere);
                    return matiereMapper.toDto(matiere);
                }
        ).toList();


    }

    @Override
    public MatiereDTO update(MatiereDTO matiereDTO) {
        LOG.debug("Request to update Matiere : {}", matiereDTO);
        Matiere matiere = matiereMapper.toEntity(matiereDTO);
        matiere = matiereRepository.save(matiere);
        return matiereMapper.toDto(matiere);
    }

    @Override
    public Optional<MatiereDTO> partialUpdate(MatiereDTO matiereDTO) {
        LOG.debug("Request to partially update Matiere : {}", matiereDTO);

        return matiereRepository
            .findById(matiereDTO.getId())
            .map(existingMatiere -> {
                matiereMapper.partialUpdate(existingMatiere, matiereDTO);

                return existingMatiere;
            })
            .map(matiereRepository::save)
            .map(matiereMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MatiereDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Matieres");
        return matiereRepository.findAll(pageable).map(matiereMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MatiereDTO> findOne(Long id) {
        LOG.debug("Request to get Matiere : {}", id);
        return matiereRepository.findById(id).map(matiereMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Matiere : {}", id);
        matiereRepository.deleteById(id);
    }
}
