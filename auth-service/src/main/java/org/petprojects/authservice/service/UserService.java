package org.petprojects.authservice.service;

import org.petprojects.authservice.dto.*;
import java.util.List;

public interface UserService {
    UserDto create(CreateUserRequest req);
    UserDto get(Long id);
    List<UserDto> getAll();
    UserDto update(Long id, UpdateUserRequest req);
    void delete(Long id);
}
