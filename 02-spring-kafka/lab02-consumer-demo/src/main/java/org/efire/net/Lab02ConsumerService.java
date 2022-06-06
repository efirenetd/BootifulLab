package org.efire.net;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static org.efire.net.Lab02Message.LAB02_TOPIC;

@Component
@Slf4j
public class Lab02ConsumerService {

    @KafkaListener(id = "lab02-id", topics = LAB02_TOPIC, groupId = "lab02", containerFactory = "kafkaListenerContainerFactory")
    public void onMessage(String message) {
        log.info("== Received message payload ===\n {}", message);
    }
}
