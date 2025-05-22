package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.CahierTexte;
import com.bfrost.universite.service.dto.CahierTexteDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link CahierTexte} and its DTO {@link CahierTexteDTO}.
 */
@Mapper(componentModel = "spring")
public interface CahierTexteMapper extends EntityMapper<CahierTexteDTO, CahierTexte> {

}
