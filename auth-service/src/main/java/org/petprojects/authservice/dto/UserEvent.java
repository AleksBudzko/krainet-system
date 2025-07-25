package org.petprojects.authservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEvent {
    private String eventType;   // USER_CREATED / USER_UPDATED / USER_DELETED
    private String username;
    private String password;
    private String email;
}
