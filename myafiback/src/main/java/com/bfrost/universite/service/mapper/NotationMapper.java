package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.Cours;
import com.bfrost.universite.domain.Notation;
import com.bfrost.universite.domain.User;
import com.bfrost.universite.service.dto.CoursDTO;
import com.bfrost.universite.service.dto.NotationDTO;
import com.bfrost.universite.service.dto.UserDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link Notation} and its DTO {@link NotationDTO}.
 */
@Mapper(componentModel = "spring")
public interface NotationMapper extends EntityMapper<NotationDTO, Notation> {
    @Mapping(target = "cours", source = "cours", qualifiedByName = "coursId")
    @Mapping(target = "etudiant", source = "etudiant", qualifiedByName = "userId")
    NotationDTO toDto(Notation s);

    @Named("coursId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CoursDTO toDtoCoursId(Cours cours);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
