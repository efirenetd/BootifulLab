package org.efire.net.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static org.efire.net.config.RabbitMQConfig.*;

@Component
@Slf4j
public class DemoMessageConsumer {

    @RabbitListener(queues = { DEMO_FANOUT_A_QUEUE_NAME })
    public void receivedMessageFromFanoutA(String message) {
        log.info("===> FANOUT A Message received: "+ message);
    }

    @RabbitListener(queues = { DEMO_FANOUT_B_QUEUE_NAME })
    public void receivedMessageFromFanoutB(String message) {
        log.info("===> FANOUT B Message received: "+ message);
    }

    @RabbitListener(queues = { DEMO_FANOUT_C_QUEUE_NAME })
    public void receivedMessageFromFanoutC(String message) {
        log.info("===> FANOUT C Message received: "+ message);
    }


    @RabbitListener(queues = { DEMO_TOPIC_QUEUE_NAME })
    public void receivedMessageFromTopic(String message) {
        log.info("===> TOPIC Message received: "+ message);
    }
}
