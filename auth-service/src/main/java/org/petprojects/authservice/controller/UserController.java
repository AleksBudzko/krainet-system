package org.petprojects.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.petprojects.authservice.dto.*;
import org.petprojects.authservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll(Authentication auth) {
        // only ADMIN can list all
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getOne(@PathVariable Long id, Authentication auth) {
        // USER only their own
        checkAccess(id, auth);
        return ResponseEntity.ok(service.get(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(
            @PathVariable Long id,
            @RequestBody UpdateUserRequest req,
            Authentication auth) {
        checkAccess(id, auth);
        return ResponseEntity.ok(service.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id, Authentication auth) {
        checkAccess(id, auth);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    private void checkAccess(Long id, Authentication auth) {
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        String current = auth.getName();
        if (!isAdmin && !service.get(id).getUsername().equals(current)) {
            throw new SecurityException("Access denied");
        }
    }
}
