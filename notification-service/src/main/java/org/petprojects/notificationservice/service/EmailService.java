package org.petprojects.notificationservice.service;

import org.petprojects.notificationservice.dto.UserEvent;

public interface EmailService {
    void sendToAdmins(UserEvent event);
}
