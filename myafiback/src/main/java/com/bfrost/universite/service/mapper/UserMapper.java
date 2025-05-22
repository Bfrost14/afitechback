package com.bfrost.universite.service.mapper;

import com.bfrost.universite.domain.User;
import com.bfrost.universite.service.dto.AdminUserDTO;
import com.bfrost.universite.service.dto.UserDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link User} and its DTO {@link UserDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<AdminUserDTO, User> {}
