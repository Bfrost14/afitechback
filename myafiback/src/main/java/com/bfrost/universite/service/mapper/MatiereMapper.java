package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.Matiere;
import com.bfrost.universite.domain.UE;
import com.bfrost.universite.service.dto.MatiereDTO;
import com.bfrost.universite.service.dto.UEDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link Matiere} and its DTO {@link MatiereDTO}.
 */
@Mapper(componentModel = "spring")
public interface MatiereMapper extends EntityMapper<MatiereDTO, Matiere> {
    @Mapping(target = "ue", source = "ue", qualifiedByName = "uEId")
    MatiereDTO toDto(Matiere s);

    @Named("uEId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UEDTO toDtoUEId(UE uE);
}
