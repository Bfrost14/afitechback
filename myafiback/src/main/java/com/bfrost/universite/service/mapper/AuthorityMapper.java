package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.Authority;
import com.bfrost.universite.service.dto.AuthorityDTO;
import org.mapstruct.Mapper;

import java.util.Set;

/**
 * Mapper for the entity {@link Authority} and its DTO {@link AuthorityDTO}.
 */
@Mapper(componentModel = "spring")
public interface AuthorityMapper extends EntityMapper<AuthorityDTO, Authority> {

    Set<Authority> toEntity(Set<AuthorityDTO> authorities);
}
