package org.petprojects.authservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    private String token;
}
