package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.CalendrierCours;
import com.bfrost.universite.service.dto.CalendrierCoursDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link CalendrierCours} and its DTO {@link CalendrierCoursDTO}.
 */
@Mapper(componentModel = "spring")
public interface CalendrierCoursMapper extends EntityMapper<CalendrierCoursDTO, CalendrierCours> {

}
