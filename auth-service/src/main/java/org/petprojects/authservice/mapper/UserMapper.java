package org.petprojects.authservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.petprojects.authservice.dto.CreateUserRequest;
import org.petprojects.authservice.dto.UpdateUserRequest;
import org.petprojects.authservice.dto.UserDto;
import org.petprojects.authservice.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User entity);
    User fromCreate(CreateUserRequest dto);
    void updateFromDto(UpdateUserRequest dto, @MappingTarget User entity);
}
