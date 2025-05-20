package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.Profil;
import com.bfrost.universite.service.dto.ProfilDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Profil} and its DTO {@link ProfilDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProfilMapper extends EntityMapper<ProfilDTO, Profil> {}
