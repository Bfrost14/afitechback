package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.Campus;
import com.bfrost.universite.domain.Salle;
import com.bfrost.universite.service.dto.CampusDTO;
import com.bfrost.universite.service.dto.SalleDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link Salle} and its DTO {@link SalleDTO}.
 */
@Mapper(componentModel = "spring")
public interface SalleMapper extends EntityMapper<SalleDTO, Salle> {
}
