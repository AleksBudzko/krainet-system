package org.petprojects.authservice.security;

import lombok.RequiredArgsConstructor;
import org.petprojects.authservice.entity.User;
import org.petprojects.authservice.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        // получаем вашу сущность
        User u = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                u.getUsername(),
                u.getPassword(),
                List.of(new SimpleGrantedAuthority(u.getRole()))
        );
    }
}
