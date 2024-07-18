package com.softka.massive_trasport.massive_transport_api;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${sacavix.queue.name}")
    private String message;

    @Bean
    public Queue transactionQueue() {
        // return new Queue("transactionQueue", false);
        return new Queue(message, true);
    }

}
