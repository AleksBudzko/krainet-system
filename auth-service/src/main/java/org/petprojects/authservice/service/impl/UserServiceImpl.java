package org.petprojects.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.petprojects.authservice.config.RabbitConfig;
import org.petprojects.authservice.dto.*;
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
    private final UserRepository repo;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;
    private final RabbitTemplate rmq;

    @Override
    public UserDto create(CreateUserRequest req) {
        User u = mapper.fromCreate(req);
        u.setPassword(req.getPassword());
        u.setRole("ROLE_USER");
        User saved = repo.save(u);
        if ("ROLE_USER".equals(saved.getRole())) publish("USER_CREATED", saved);
        return mapper.toDto(saved);
    }

    @Override
    public UserDto get(Long id) {
        User u = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found"));
        return mapper.toDto(u);
    }

    @Override
    public List<UserDto> getAll() {
        return repo.findAll().stream()
                .map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public UserDto update(Long id, UpdateUserRequest req) {
        User u = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found"));
        mapper.updateFromDto(req, u);
        User up = repo.save(u);
        if ("ROLE_USER".equals(up.getRole())) publish("USER_UPDATED", up);
        return mapper.toDto(up);
    }

    @Override
    public void delete(Long id) {
        User u = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found"));
        repo.delete(u);
        if ("ROLE_USER".equals(u.getRole())) publish("USER_DELETED", u);
    }

    private void publish(String type, User u) {
        UserEvent ev = UserEvent.builder()
                .eventType(type)
                .username(u.getUsername())
                .password(u.getPassword())
                .email(u.getEmail())
                .build();
        rmq.convertAndSend(RabbitConfig.EXCHANGE, type, ev);
    }
}
