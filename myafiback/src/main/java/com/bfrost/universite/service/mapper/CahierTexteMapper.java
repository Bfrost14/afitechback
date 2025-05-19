package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.CahierTexte;
import com.bfrost.universite.domain.Cours;
import com.bfrost.universite.domain.User;
import com.bfrost.universite.service.dto.CahierTexteDTO;
import com.bfrost.universite.service.dto.CoursDTO;
import com.bfrost.universite.service.dto.UserDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link CahierTexte} and its DTO {@link CahierTexteDTO}.
 */
@Mapper(componentModel = "spring")
public interface CahierTexteMapper extends EntityMapper<CahierTexteDTO, CahierTexte> {
    @Mapping(target = "cours", source = "cours", qualifiedByName = "coursId")
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    CahierTexteDTO toDto(CahierTexte s);

    @Named("coursId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CoursDTO toDtoCoursId(Cours cours);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
