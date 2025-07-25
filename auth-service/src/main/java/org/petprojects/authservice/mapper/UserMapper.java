package org.petprojects.authservice.mapper;

import org.mapstruct.*;
import org.petprojects.authservice.entity.User;
import org.petprojects.authservice.dto.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", constant = "ROLE_USER")
    User fromCreate(CreateUserRequest dto);

    @Mapping(target = "id",       ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role",     ignore = true)
    void updateFromDto(UpdateUserRequest dto, @MappingTarget User entity);
}
