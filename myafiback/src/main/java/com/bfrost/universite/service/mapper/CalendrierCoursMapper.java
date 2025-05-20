package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.CalendrierCours;
import com.bfrost.universite.domain.Cours;
import com.bfrost.universite.domain.Filiere;
import com.bfrost.universite.domain.Salle;
import com.bfrost.universite.service.dto.CalendrierCoursDTO;
import com.bfrost.universite.service.dto.CoursDTO;
import com.bfrost.universite.service.dto.FiliereDTO;
import com.bfrost.universite.service.dto.SalleDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link CalendrierCours} and its DTO {@link CalendrierCoursDTO}.
 */
@Mapper(componentModel = "spring")
public interface CalendrierCoursMapper extends EntityMapper<CalendrierCoursDTO, CalendrierCours> {

}
