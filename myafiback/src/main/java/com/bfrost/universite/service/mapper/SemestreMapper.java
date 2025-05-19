package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.Semestre;
import com.bfrost.universite.service.dto.SemestreDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Semestre} and its DTO {@link SemestreDTO}.
 */
@Mapper(componentModel = "spring")
public interface SemestreMapper extends EntityMapper<SemestreDTO, Semestre> {}
