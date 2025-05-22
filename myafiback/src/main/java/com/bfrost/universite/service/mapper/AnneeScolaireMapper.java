package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.AnneeScolaire;
import com.bfrost.universite.service.dto.AnneeScolaireDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link AnneeScolaire} and its DTO {@link AnneeScolaireDTO}.
 */
@Mapper(componentModel = "spring")
public interface AnneeScolaireMapper extends EntityMapper<AnneeScolaireDTO, AnneeScolaire> {}
