package org.petprojects.notificationservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "notification")
public class NotificationProperties {
    private List<String> adminEmails;
}
