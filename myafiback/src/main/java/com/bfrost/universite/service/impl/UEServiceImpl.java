package com.bfrost.universite.service.impl;

import com.bfrost.universite.domain.UE;
import com.bfrost.universite.repository.UERepository;
import com.bfrost.universite.service.UEService;
import com.bfrost.universite.service.dto.UEDTO;
import com.bfrost.universite.service.mapper.UEMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * Service Implementation for managing {@link UE}.
 */
@Service
@Transactional
public class UEServiceImpl implements UEService {

    private static final Logger LOG = LoggerFactory.getLogger(UEServiceImpl.class);

    private final UERepository uERepository;

    private final UEMapper uEMapper;

    public UEServiceImpl(UERepository uERepository, UEMapper uEMapper) {
        this.uERepository = uERepository;
        this.uEMapper = uEMapper;
    }

    @Override
    public UEDTO save(UEDTO uEDTO) {
        LOG.debug("Request to save UE : {}", uEDTO);
        UE uE = uEMapper.toEntity(uEDTO);
        uE = uERepository.save(uE);
        return uEMapper.toDto(uE);
    }

    @Override
    public UEDTO update(UEDTO uEDTO) {
        LOG.debug("Request to update UE : {}", uEDTO);
        UE uE = uEMapper.toEntity(uEDTO);
        uE = uERepository.save(uE);
        return uEMapper.toDto(uE);
    }

    @Override
    public Optional<UEDTO> partialUpdate(UEDTO uEDTO) {
        LOG.debug("Request to partially update UE : {}", uEDTO);

        return uERepository
            .findById(uEDTO.getId())
            .map(existingUE -> {
                uEMapper.partialUpdate(existingUE, uEDTO);

                return existingUE;
            })
            .map(uERepository::save)
            .map(uEMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UEDTO> findAll(Pageable pageable, String nom) {
        LOG.debug("Request to get all UES");
        if(StringUtils.hasText(nom)){
            return uERepository.findAllByNomContainingIgnoreCase(pageable, nom).map(uEMapper::toDto);
        }else {
            return uERepository.findAll(pageable).map(uEMapper::toDto);
        }

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UEDTO> findOne(Long id) {
        LOG.debug("Request to get UE : {}", id);
        return uERepository.findById(id).map(uEMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete UE : {}", id);
        uERepository.deleteById(id);
    }
}
