package org.efire.net.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static org.efire.net.config.RabbitMQConfig.DEMO_FANOUT_EXCHANGE_NAME;
import static org.efire.net.config.RabbitMQConfig.DEMO_TOPIC_EXCHANGE_NAME;

@Component
public class DemoMessageSender {

    private RabbitTemplate rabbitTemplate;

    public DemoMessageSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void broadcast(String message) {
        this.rabbitTemplate.convertAndSend(DEMO_FANOUT_EXCHANGE_NAME, "", message);
    }

    public void sendError(String message) {
        this.rabbitTemplate.convertAndSend(DEMO_TOPIC_EXCHANGE_NAME, "this.is.an.error", message);
    }
}
