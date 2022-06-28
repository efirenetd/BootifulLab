package org.efire.net.producer;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static org.efire.net.config.RabbitMQConfig.*;

@Component
public class MessageProducer {

    private RabbitTemplate rabbitTemplate;

    public MessageProducer(RabbitTemplate rabbitTemplate,
                           MessageProducerCallback callback,
                           MessageProducerReturnsCallback returnsCallback) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback(callback);
        rabbitTemplate.setReturnsCallback(returnsCallback);
    }

    public void sendMessage() {
        CorrelationData cd1 = new CorrelationData("Correlation for message 1");
        this.rabbitTemplate.convertAndSend(DEMO_DIRECT_EXCHANGE_NAME,
                DEMO_DIRECT_QUEUE_NAME,
                DEMO_ROUTING_KEY,
                cd1);

        //Send a message to a non existing queue
        CorrelationData cd2 = new CorrelationData("Correlation for message 2");
        this.rabbitTemplate.convertAndSend(DEMO_DIRECT_EXCHANGE_NAME,
                "NON_EXISTING_QUEUE",
                "boo",
                cd2);
    }
}
