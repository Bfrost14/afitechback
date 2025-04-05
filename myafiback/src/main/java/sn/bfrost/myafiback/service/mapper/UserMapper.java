package sn.bfrost.myafiback.service.mapper;

import org.mapstruct.Mapper;
import sn.bfrost.myafiback.models.User;
import sn.bfrost.myafiback.service.dto.UserDTO;

/**
 * Mapper for the entity {@link User} and its DTO {@link UserDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDTO, User> {

}
