package org.petprojects.authservice.dto;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateUserRequest {
    private String email;
    private String firstName;
    private String lastName;
}
