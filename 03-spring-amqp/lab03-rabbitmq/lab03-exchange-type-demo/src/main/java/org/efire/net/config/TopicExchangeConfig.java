package org.efire.net.config;

import org.efire.net.common.TopicExchangeProperties;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

// References
/**
 *  - https://www.baeldung.com/rabbitmq-spring-amqp
 *  - https://jstobigdata.com/rabbitmq/topic-exchange-in-amqp-rabbitmq/
 *  - https://www.cloudamqp.com/blog/part4-rabbitmq-for-beginners-exchanges-routing-keys-bindings.html?utm_source=pocket_mylist
 **/
@Configuration
@Profile("topic")
public class TopicExchangeConfig {

    @Autowired
    private TopicExchangeProperties props;

    @Bean
    public Declarables topicBindings() {
        var topicExchange = new TopicExchange(props.getExchangeName(), true, false);
        var berlinQueue = QueueBuilder.durable(props.berlin().getQueueName()).build();
        var allQueue = QueueBuilder.durable(props.all().getQueueName()).build();
        var headStoreQueue = QueueBuilder.durable(props.headstore().getQueueName()).build();

        return new Declarables(berlinQueue, allQueue, headStoreQueue, topicExchange,
                BindingBuilder
                        .bind(berlinQueue)
                        .to(topicExchange)
                        .with(props.berlin().getRoutingKey()),
                BindingBuilder
                        .bind(allQueue)
                        .to(topicExchange)
                        .with(props.all().getRoutingKey()),
                BindingBuilder
                        .bind(headStoreQueue)
                        .to(topicExchange)
                        .with(props.headstore().getRoutingKey()));
    }
}
