package zhedron.shop.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import zhedron.shop.dto.UserDTO;
import zhedron.shop.models.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserDTO toDTO (User user);

    User toEntity (UserDTO userDTO);
}
