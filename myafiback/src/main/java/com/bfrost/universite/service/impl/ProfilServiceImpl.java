package com.bfrost.universite.service.impl;

import com.bfrost.universite.domain.Authority;
import com.bfrost.universite.domain.Profil;
import com.bfrost.universite.domain.enumeration.TypeProfil;
import com.bfrost.universite.repository.ProfilRepository;
import com.bfrost.universite.repository.UserRepository;
import com.bfrost.universite.service.ProfilService;
import com.bfrost.universite.service.dto.ProfilDTO;
import com.bfrost.universite.service.mapper.ProfilMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Service Implementation for managing {@link Profil}.
 */
@Service
@Transactional
public class ProfilServiceImpl implements ProfilService {

    private static final Logger LOG = LoggerFactory.getLogger(ProfilServiceImpl.class);

    private final ProfilRepository profilRepository;

    private final ProfilMapper profilMapper;
    private final UserRepository userRepository;

    public ProfilServiceImpl(ProfilRepository profilRepository, ProfilMapper profilMapper, UserRepository userRepository) {
        this.profilRepository = profilRepository;
        this.profilMapper = profilMapper;
        this.userRepository = userRepository;
    }

    @Override
    public ProfilDTO save(ProfilDTO profilDTO) {
        LOG.debug("Request to save Profil : {}", profilDTO);
        Profil profil = profilMapper.toEntity(profilDTO);
        profil = profilRepository.save(profil);
        return profilMapper.toDto(profil);
    }

    @Override
    public ProfilDTO update(ProfilDTO profilDTO) {
        LOG.debug("Request to update Profil : {}", profilDTO);
        Profil profil = profilMapper.toEntity(profilDTO);
        profil = profilRepository.save(profil);

        Set<Authority> profilAuthorities = profil.getAuthorities();

        userRepository.findAllByProfilId(profil.getId()).forEach(user -> {
            // ⚠️ Créer une nouvelle instance de Set pour éviter les références partagées
            Set<Authority> copiedAuthorities = new HashSet<>(profilAuthorities);
            user.setAuthorities(copiedAuthorities);
            userRepository.save(user);
        });



        return profilMapper.toDto(profil);
    }

    @Override
    public Optional<ProfilDTO> partialUpdate(ProfilDTO profilDTO) {
        LOG.debug("Request to partially update Profil : {}", profilDTO);

        return profilRepository
            .findById(profilDTO.getId())
            .map(existingProfil -> {
                profilMapper.partialUpdate(existingProfil, profilDTO);

                return existingProfil;
            })
            .map(profilRepository::save)
            .map(profilMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProfilDTO> findAll(Pageable pageable, String nom, String typeProfil) {
        LOG.debug("Request to get all Profiles");

        TypeProfil profil = StringUtils.hasText(typeProfil) ? TypeProfil.valueOf(typeProfil) : null;
        return profilRepository.manageProfil(pageable, nom, profil).map(profilMapper::toDto);

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProfilDTO> findOne(Long id) {
        LOG.debug("Request to get Profil : {}", id);
        return profilRepository.findById(id).map(profilMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Profil : {}", id);
        profilRepository.deleteById(id);
    }
}
