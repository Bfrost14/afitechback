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

}
