package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.Campus;
import com.bfrost.universite.service.dto.CampusDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Campus} and its DTO {@link CampusDTO}.
 */
@Mapper(componentModel = "spring")
public interface CampusMapper extends EntityMapper<CampusDTO, Campus> {}
