package org.efire.net.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String DEMO_DIRECT_EXCHANGE_NAME = "DEMO_DIRECT_EXCHANGE";
    public static final String DEMO_DIRECT_QUEUE_NAME = "demo.direct.queue";
    public static final String DEMO_ROUTING_KEY = "demo.routing.key";

    @Bean
    public Declarables directBindings() {
        var directExchange = new DirectExchange(DEMO_DIRECT_EXCHANGE_NAME);
        var demoDirectQueue = QueueBuilder.durable(DEMO_DIRECT_QUEUE_NAME).build();

        return new Declarables(demoDirectQueue, directExchange,
                BindingBuilder
                        .bind(demoDirectQueue)
                        .to(directExchange)
                        .with(DEMO_ROUTING_KEY));

    }
}
