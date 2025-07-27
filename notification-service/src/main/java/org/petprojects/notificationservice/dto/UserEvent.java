package org.petprojects.notificationservice.dto;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UserEvent {
    private String eventType;
    private String username;
    private String password;
    private String email;
}
