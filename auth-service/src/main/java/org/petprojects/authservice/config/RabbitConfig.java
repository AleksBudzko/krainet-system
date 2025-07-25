package org.petprojects.authservice.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.*;

@Configuration
public class RabbitConfig {
    public static final String EXCHANGE = "user.events";
    public static final String QUEUE    = "admin.notifications";

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    Queue queue() {
        return new Queue(QUEUE);
    }

    @Bean
    Binding bindCreated(Queue queue, TopicExchange ex) {
        return BindingBuilder.bind(queue).to(ex).with("USER_CREATED");
    }

    @Bean
    Binding bindUpdated(Queue queue, TopicExchange ex) {
        return BindingBuilder.bind(queue).to(ex).with("USER_UPDATED");
    }

    @Bean
    Binding bindDeleted(Queue queue, TopicExchange ex) {
        return BindingBuilder.bind(queue).to(ex).with("USER_DELETED");
    }
}
