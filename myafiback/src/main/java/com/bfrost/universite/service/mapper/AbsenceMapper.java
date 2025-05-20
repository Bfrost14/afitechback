package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.Absence;
import com.bfrost.universite.service.dto.AbsenceDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Absence} and its DTO {@link AbsenceDTO}.
 */
@Mapper(componentModel = "spring")
public interface AbsenceMapper extends EntityMapper<AbsenceDTO, Absence> {

}
