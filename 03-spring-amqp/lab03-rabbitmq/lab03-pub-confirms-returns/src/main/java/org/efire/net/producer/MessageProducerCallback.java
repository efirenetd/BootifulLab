package org.efire.net.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageProducerCallback implements RabbitTemplate.ConfirmCallback {

    @Override
    public void confirm(CorrelationData cd, boolean ack, String cause) {
        if (ack) {
            log.info("=== confirm correlation data: {} === ", cd );
        } else {
            log.error("=== confirm correlation data: {}, cause: {} === ", cd, cause);
        }
    }
}
