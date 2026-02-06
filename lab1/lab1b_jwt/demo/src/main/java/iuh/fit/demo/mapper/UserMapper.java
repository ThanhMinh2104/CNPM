package iuh.fit.demo.mapper;

import iuh.fit.demo.dto.request.CreateUserRequest;
import iuh.fit.demo.dto.request.UpdateUserRequest;
import iuh.fit.demo.dto.response.UserResponseDTO;
import iuh.fit.demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    public UserResponseDTO toDTO(User user);
    public User toUser(CreateUserRequest request);
    public User toUser(@MappingTarget User user , UpdateUserRequest request);
}
