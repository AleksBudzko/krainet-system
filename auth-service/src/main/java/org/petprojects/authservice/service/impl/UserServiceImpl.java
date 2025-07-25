package org.petprojects.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.petprojects.authservice.config.RabbitConfig;
import org.petprojects.authservice.dto.UserEvent;
import org.petprojects.authservice.dto.CreateUserRequest;
import org.petprojects.authservice.dto.UpdateUserRequest;
import org.petprojects.authservice.dto.UserDto;
import org.petprojects.authservice.entity.User;
import org.petprojects.authservice.mapper.UserMapper;
import org.petprojects.authservice.repository.UserRepository;
import org.petprojects.authservice.service.UserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository  repo;
    private final UserMapper      mapper;
    private final PasswordEncoder encoder;
    private final RabbitTemplate  rmq;

    @Override
    public UserDto create(CreateUserRequest req) {
        User u = mapper.fromCreate(req);
        u.setPassword(encoder.encode(req.getPassword()));
        User saved = repo.save(u);
        if ("ROLE_USER".equals(saved.getRole())) publishEvent("USER_CREATED", saved);
        return mapper.toDto(saved);
    }

    @Override
    public UserDto get(Long id) {
        User u = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return mapper.toDto(u);
    }

    @Override
    public List<UserDto> getAll() {
        return repo.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto update(Long id, UpdateUserRequest req) {
        User u = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        mapper.updateFromDto(req, u);
        User updated = repo.save(u);
        if ("ROLE_USER".equals(updated.getRole())) publishEvent("USER_UPDATED", updated);
        return mapper.toDto(updated);
    }

    @Override
    public void delete(Long id) {
        User u = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        repo.delete(u);
        if ("ROLE_USER".equals(u.getRole())) publishEvent("USER_DELETED", u);
    }

    private void publishEvent(String type, User u) {
        UserEvent evt = UserEvent.builder()
                .eventType(type)
                .username(u.getUsername())
                .password(u.getPassword())
                .email(u.getEmail())
                .build();
        rmq.convertAndSend(RabbitConfig.EXCHANGE, type, evt);
    }
}
