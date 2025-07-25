package org.petprojects.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.petprojects.authservice.dto.*;
import org.petprojects.authservice.security.JwtUtil;
import org.petprojects.authservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtUtil               jwtUtil;
    private final org.petprojects.authservice.security.CustomUserDetailsService uds;
    private final UserService           userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody CreateUserRequest req) {
        UserDto created = userService.create(req);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest req) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getUsername(), req.getPassword()
                )
        );
        UserDetails ud = uds.loadUserByUsername(req.getUsername());
        String token = jwtUtil.generateToken(ud);
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
}
