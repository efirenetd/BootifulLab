package org.efire.net.config;

import org.efire.net.handler.Lab03ErrorHandler;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                                    Lab03ErrorHandler errorHandler) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setErrorHandler(errorHandler);
        return container;
    }

}
