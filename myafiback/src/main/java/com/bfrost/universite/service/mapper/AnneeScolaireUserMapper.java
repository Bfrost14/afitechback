package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.AnneeScolaireUser;
import com.bfrost.universite.service.dto.AnneeScolaireUserDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link AnneeScolaireUser} and its DTO {@link AnneeScolaireUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface AnneeScolaireUserMapper extends EntityMapper<AnneeScolaireUserDTO, AnneeScolaireUser> {

}
