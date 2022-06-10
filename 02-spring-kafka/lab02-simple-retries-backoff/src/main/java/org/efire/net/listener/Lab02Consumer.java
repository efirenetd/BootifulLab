package org.efire.net.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static org.efire.net.common.AppConstant.*;

@Component
@Slf4j
public class Lab02Consumer {

    @KafkaListener(id = LAB02_ID, topics = LAB02_TOPIC, groupId = LAB02_GROUP_ID, containerFactory = "kafkaListenerContainerFactory")
    public void onMessage(ConsumerRecord<String, String> record) {
        log.info("==== Received Payload with Record value: "+record.value());

        //Simulate an error to trigger common error handler implementation
        throw new RuntimeException("Forced to throw an error to retry!!!");
    }
}
