package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.Filiere;
import com.bfrost.universite.service.dto.FiliereDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Filiere} and its DTO {@link FiliereDTO}.
 */
@Mapper(componentModel = "spring")
public interface FiliereMapper extends EntityMapper<FiliereDTO, Filiere> {}
