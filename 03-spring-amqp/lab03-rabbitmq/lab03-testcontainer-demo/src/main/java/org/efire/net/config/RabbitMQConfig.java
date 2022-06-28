package org.efire.net.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String DEMO_TOPIC_EXCHANGE_NAME = "DEMO_TOPIC_EXCHANGE";
    public static final String DEMO_FANOUT_EXCHANGE_NAME = "DEMO_FANOUT_EXCHANGE";
    public static final String DEMO_TOPIC_QUEUE_NAME = "demo.topic.queue";
    public static final String DEMO_FANOUT_A_QUEUE_NAME = "demo.fanout.a.queue";
    public static final String DEMO_FANOUT_B_QUEUE_NAME = "demo.fanout.b.queue";
    public static final String DEMO_FANOUT_C_QUEUE_NAME = "demo.fanout.c.queue";
    public static final String BINDING_PATTERN_ERROR = "#.error";

    @Bean
    public Declarables topicBindings() {
        var topicExchange = new TopicExchange(DEMO_TOPIC_EXCHANGE_NAME);
        var demoTopicQueue = QueueBuilder.durable(DEMO_TOPIC_QUEUE_NAME).build();
        return new Declarables(demoTopicQueue, topicExchange,
                BindingBuilder
                        .bind(demoTopicQueue)
                        .to(topicExchange)
                        .with(BINDING_PATTERN_ERROR));
    }

    @Bean
    public Declarables fanoutBindings() {
        var fanoutExchange = new FanoutExchange(DEMO_FANOUT_EXCHANGE_NAME);
        var fanoutAQueue = QueueBuilder.durable(DEMO_FANOUT_A_QUEUE_NAME).build();
        var fanoutBQueue = QueueBuilder.durable(DEMO_FANOUT_B_QUEUE_NAME).build();
        var fanoutCQueue = QueueBuilder.durable(DEMO_FANOUT_C_QUEUE_NAME).build();
        return new Declarables(fanoutAQueue, fanoutBQueue, fanoutCQueue, fanoutExchange,
                BindingBuilder
                        .bind(fanoutAQueue).to(fanoutExchange),
                BindingBuilder
                        .bind(fanoutBQueue).to(fanoutExchange),
                BindingBuilder
                        .bind(fanoutCQueue).to(fanoutExchange));
    }
}
