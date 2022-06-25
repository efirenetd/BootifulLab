package org.efire.net.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Profile("topic")
public class TopicConsumer {

    @RabbitListener(queues = "berlin_agreements")
    public void consumeBerlin(String message) {
        log.info("Berlin message received >>>>> {} <<<<<", message);
    }

    @RabbitListener(queues = "headstore_agreements")
    public void consumeHeadStore(String message) {
        log.info("HeadStore message received >>>>> {} <<<<<", message);
    }

    @RabbitListener(queues = "all_agreements")
    public void consumeAll(String message) {
        log.info("All message received >>>>> {} <<<<<", message);
    }

}
