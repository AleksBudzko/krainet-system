package org.petprojects.notificationservice.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String EXCHANGE = "user.events";
    public static final String QUEUE    = "admin.notifications";

    @Bean TopicExchange exchange() { return new TopicExchange(EXCHANGE); }
    @Bean Queue queue()             { return new Queue(QUEUE); }
    @Bean Binding bindCreated(Queue q, TopicExchange ex) {
        return BindingBuilder.bind(q).to(ex).with("USER_CREATED");
    }
    @Bean Binding bindUpdated(Queue q, TopicExchange ex) {
        return BindingBuilder.bind(q).to(ex).with("USER_UPDATED");
    }
    @Bean Binding bindDeleted(Queue q, TopicExchange ex) {
        return BindingBuilder.bind(q).to(ex).with("USER_DELETED");
    }
}
