package org.efire.net;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static org.efire.net.AppConstants.LAB02_GROUP_ID;
import static org.efire.net.AppConstants.LAB02_TOPIC;

@Component
@Slf4j
public class Lab02ConsumerService {

    @KafkaListener(id = "lab02-id", topics = LAB02_TOPIC, groupId = LAB02_GROUP_ID, containerFactory = "kafkaListenerContainerFactory")
    public void onMessage(Person person) {
        log.info("=== Received message ===\n {}", person);
    }
}
