package org.petprojects.authservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long   id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
}
