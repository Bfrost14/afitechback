package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.Notation;
import com.bfrost.universite.service.dto.NotationDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Notation} and its DTO {@link NotationDTO}.
 */
@Mapper(componentModel = "spring")
public interface NotationMapper extends EntityMapper<NotationDTO, Notation> {

}
