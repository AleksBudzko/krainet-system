package org.petprojects.notificationservice.listener;

import lombok.RequiredArgsConstructor;
import org.petprojects.notificationservice.config.RabbitConfig;
import org.petprojects.notificationservice.dto.UserEvent;
import org.petprojects.notificationservice.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationListener {
    private final EmailService emailService;

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void onUserEvent(UserEvent event) {
        emailService.sendToAdmins(event);
    }
}
