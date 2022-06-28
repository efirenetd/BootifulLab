package org.efire.net.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageProducerReturnsCallback implements RabbitTemplate.ReturnsCallback {
    @Override
    public void returnedMessage(ReturnedMessage rm) {
        log.info("== Returns Callback == \n " +
                "Message: {} \n " +
                "ReplyCode: {} \n " +
                "ReplyText: {} \n " +
                "Exchange: {} \n " +
                "RoutingKey: {} ",
                rm.getMessage(),
                rm.getReplyCode(),
                rm.getReplyText(),
                rm.getExchange(),
                rm.getRoutingKey());
    }
}
