package com.bfrost.universite.service.impl;

import com.bfrost.universite.domain.AnneeScolaireUser;
import com.bfrost.universite.repository.AnneeScolaireUserRepository;
import com.bfrost.universite.service.AnneeScolaireUserService;
import com.bfrost.universite.service.dto.AnneeScolaireUserDTO;
import com.bfrost.universite.service.mapper.AnneeScolaireUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link AnneeScolaireUser}.
 */
@Service
@Transactional
public class AnneeScolaireUserServiceImpl implements AnneeScolaireUserService {

    private static final Logger LOG = LoggerFactory.getLogger(AnneeScolaireUserServiceImpl.class);

    private final AnneeScolaireUserRepository anneeScolaireUserRepository;

    private final AnneeScolaireUserMapper anneeScolaireUserMapper;

    public AnneeScolaireUserServiceImpl(
        AnneeScolaireUserRepository anneeScolaireUserRepository,
        AnneeScolaireUserMapper anneeScolaireUserMapper
    ) {
        this.anneeScolaireUserRepository = anneeScolaireUserRepository;
        this.anneeScolaireUserMapper = anneeScolaireUserMapper;
    }

    @Override
    public List<AnneeScolaireUserDTO> save(List<AnneeScolaireUserDTO> anneeScolaireUserDTO) {
        LOG.debug("Request to save AnneeScolaireUser : {}", anneeScolaireUserDTO);
        return anneeScolaireUserDTO.stream().map(anneeScolaireUserDTO1 -> {
            AnneeScolaireUser anneeScolaireUser = anneeScolaireUserMapper.toEntity(anneeScolaireUserDTO1);
            anneeScolaireUser = anneeScolaireUserRepository.save(anneeScolaireUser);
            return anneeScolaireUserMapper.toDto(anneeScolaireUser);
        }).toList();

    }

    @Override
    public AnneeScolaireUserDTO update(AnneeScolaireUserDTO anneeScolaireUserDTO) {
        LOG.debug("Request to update AnneeScolaireUser : {}", anneeScolaireUserDTO);
        AnneeScolaireUser anneeScolaireUser = anneeScolaireUserMapper.toEntity(anneeScolaireUserDTO);
        anneeScolaireUser = anneeScolaireUserRepository.save(anneeScolaireUser);
        return anneeScolaireUserMapper.toDto(anneeScolaireUser);
    }

    @Override
    public Optional<AnneeScolaireUserDTO> partialUpdate(AnneeScolaireUserDTO anneeScolaireUserDTO) {
        LOG.debug("Request to partially update AnneeScolaireUser : {}", anneeScolaireUserDTO);

        return anneeScolaireUserRepository
            .findById(anneeScolaireUserDTO.getId())
            .map(existingAnneeScolaireUser -> {
                anneeScolaireUserMapper.partialUpdate(existingAnneeScolaireUser, anneeScolaireUserDTO);

                return existingAnneeScolaireUser;
            })
            .map(anneeScolaireUserRepository::save)
            .map(anneeScolaireUserMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnneeScolaireUserDTO> findAll(Pageable pageable, String etudiant, String anneeScolaire, String filiere, String semestre) {
        LOG.debug("Request to get all AnneeScolaireUsers");
        return anneeScolaireUserRepository.manageUser(pageable, etudiant, anneeScolaire, filiere, semestre).map(anneeScolaireUserMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AnneeScolaireUserDTO> findOne(Long id) {
        LOG.debug("Request to get AnneeScolaireUser : {}", id);
        return anneeScolaireUserRepository.findById(id).map(anneeScolaireUserMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete AnneeScolaireUser : {}", id);
        anneeScolaireUserRepository.deleteById(id);
    }
}
