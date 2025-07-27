package org.petprojects.notificationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.petprojects.notificationservice.config.NotificationProperties;
import org.petprojects.notificationservice.dto.UserEvent;
import org.petprojects.notificationservice.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final NotificationProperties props;

    @Override
    public void sendToAdmins(UserEvent event) {
        String action = event.getEventType().split("_")[1].toLowerCase();
        String subject = String.format("Пользователь %s %s", event.getUsername(), action);
        String text    = String.format(
                "Событие: %s%nПользователь: %s%nПароль (хеш): %s%nEmail: %s",
                event.getEventType(), event.getUsername(), event.getPassword(), event.getEmail()
        );

        props.getAdminEmails().forEach(to -> {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(to);
            msg.setSubject(subject);
            msg.setText(text);
            mailSender.send(msg);
        });
    }
}
