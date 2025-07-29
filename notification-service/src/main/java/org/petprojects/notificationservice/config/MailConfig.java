package org.petprojects.notificationservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import jakarta.mail.internet.MimeMessage;
import java.io.InputStream;
import java.util.Arrays;

@Slf4j
@Configuration
public class MailConfig {

    /**
     * Заглушка JavaMailSender: вместо реальной отправки — просто логирует содержимое.
     */
    @Bean
    public JavaMailSender javaMailSender() {
        return new JavaMailSender() {

            @Override
            public void send(SimpleMailMessage simpleMessage) throws MailException {
                log.info(
                        "=== EMAIL SENDING (stub) ===\n" +
                                "To: {}\n" +
                                "Subject: {}\n" +
                                "Text:\n{}\n" +
                                "==========================",
                        Arrays.toString(simpleMessage.getTo()),
                        simpleMessage.getSubject(),
                        simpleMessage.getText()
                );
            }

            @Override
            public void send(SimpleMailMessage... simpleMessages) throws MailException {
                for (SimpleMailMessage msg : simpleMessages) {
                    send(msg);
                }
            }

            // --- Заглушки для методов работы с MimeMessage ---

            @Override
            public MimeMessage createMimeMessage() {
                return null;
            }

            @Override
            public MimeMessage createMimeMessage(InputStream contentStream) throws MailException {
                return null;
            }

            @Override
            public void send(MimeMessage mimeMessage) throws MailException {
                // no-op
            }

            @Override
            public void send(MimeMessage... mimeMessages) throws MailException {
                // no-op
            }

            @Override
            public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {
                // no-op
            }

            @Override
            public void send(MimeMessagePreparator... mimeMessagePreparators) throws MailException {
                // no-op
            }
        };
    }
}
