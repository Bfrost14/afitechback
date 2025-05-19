package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.Filiere;
import com.bfrost.universite.domain.Matiere;
import com.bfrost.universite.domain.MatiereUser;
import com.bfrost.universite.domain.User;
import com.bfrost.universite.service.dto.FiliereDTO;
import com.bfrost.universite.service.dto.MatiereDTO;
import com.bfrost.universite.service.dto.MatiereUserDTO;
import com.bfrost.universite.service.dto.UserDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link MatiereUser} and its DTO {@link MatiereUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface MatiereUserMapper extends EntityMapper<MatiereUserDTO, MatiereUser> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "matiere", source = "matiere", qualifiedByName = "matiereId")
    @Mapping(target = "filiere", source = "filiere", qualifiedByName = "filiereId")
    MatiereUserDTO toDto(MatiereUser s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("matiereId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MatiereDTO toDtoMatiereId(Matiere matiere);

    @Named("filiereId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FiliereDTO toDtoFiliereId(Filiere filiere);
}
