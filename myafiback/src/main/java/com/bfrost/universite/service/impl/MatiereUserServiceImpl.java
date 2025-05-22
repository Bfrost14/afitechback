package com.bfrost.universite.service.impl;

import com.bfrost.universite.domain.MatiereUser;
import com.bfrost.universite.repository.MatiereUserRepository;
import com.bfrost.universite.service.MatiereUserService;
import com.bfrost.universite.service.dto.MatiereUserDTO;
import com.bfrost.universite.service.mapper.MatiereUserMapper;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MatiereUser}.
 */
@Service
@Transactional
public class MatiereUserServiceImpl implements MatiereUserService {

    private static final Logger LOG = LoggerFactory.getLogger(MatiereUserServiceImpl.class);

    private final MatiereUserRepository matiereUserRepository;

    private final MatiereUserMapper matiereUserMapper;

    public MatiereUserServiceImpl(
        MatiereUserRepository matiereUserRepository,
        MatiereUserMapper matiereUserMapper
    ) {
        this.matiereUserRepository = matiereUserRepository;
        this.matiereUserMapper = matiereUserMapper;
    }

    @Override
    public List<MatiereUserDTO> save(List<MatiereUserDTO> matiereUserDTO) {
        LOG.debug("Request to save MatiereUser : {}", matiereUserDTO);
        return matiereUserDTO.stream().map(matiereUserDTO1 -> {
            MatiereUser matiereUser = matiereUserMapper.toEntity(matiereUserDTO1);
            matiereUser = matiereUserRepository.save(matiereUser);
            return matiereUserMapper.toDto(matiereUser);
        }).toList();

    }

    @Override
    public MatiereUserDTO update(MatiereUserDTO matiereUserDTO) {
        LOG.debug("Request to update MatiereUser : {}", matiereUserDTO);
        MatiereUser matiereUser = matiereUserMapper.toEntity(matiereUserDTO);
        matiereUser = matiereUserRepository.save(matiereUser);
        return matiereUserMapper.toDto(matiereUser);
    }

    @Override
    public Optional<MatiereUserDTO> partialUpdate(MatiereUserDTO matiereUserDTO) {
        LOG.debug("Request to partially update MatiereUser : {}", matiereUserDTO);

        return matiereUserRepository
            .findById(matiereUserDTO.getId())
            .map(existingMatiereUser -> {
                matiereUserMapper.partialUpdate(existingMatiereUser, matiereUserDTO);

                return existingMatiereUser;
            })
            .map(matiereUserRepository::save)
            .map(matiereUserMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MatiereUserDTO> findAll(Pageable pageable, String professeur, String anneeScolaire, String matiere, String filiere, String semestre) {
        LOG.debug("Request to get all MatiereUsers");
        return matiereUserRepository.manageUser(pageable, professeur, anneeScolaire,matiere, filiere, semestre).map(matiereUserMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MatiereUserDTO> findOne(Long id) {
        LOG.debug("Request to get MatiereUser : {}", id);
        return matiereUserRepository.findById(id).map(matiereUserMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete MatiereUser : {}", id);
        matiereUserRepository.deleteById(id);
    }
}
