package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.UE;
import com.bfrost.universite.service.dto.UEDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link UE} and its DTO {@link UEDTO}.
 */
@Mapper(componentModel = "spring")
public interface UEMapper extends EntityMapper<UEDTO, UE> {}
