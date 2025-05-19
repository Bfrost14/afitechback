package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.PointageProfesseur;
import com.bfrost.universite.domain.User;
import com.bfrost.universite.service.dto.PointageProfesseurDTO;
import com.bfrost.universite.service.dto.UserDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link PointageProfesseur} and its DTO {@link PointageProfesseurDTO}.
 */
@Mapper(componentModel = "spring")
public interface PointageProfesseurMapper extends EntityMapper<PointageProfesseurDTO, PointageProfesseur> {
    @Mapping(target = "professeur", source = "professeur", qualifiedByName = "userId")
    PointageProfesseurDTO toDto(PointageProfesseur s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
