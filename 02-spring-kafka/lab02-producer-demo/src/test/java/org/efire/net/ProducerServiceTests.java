package org.efire.net;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.SettableListenableFuture;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProducerServiceTests {
    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;
    @InjectMocks
    private Lab02ProducerService lab02ProducerService;

    @Test
    void givenMessageExpectSendDefaultTopicSuccess() {

        var lab02Message = new Lab02Message();
        lab02Message.setMessage("Hello ProducerServiceTests");
        ProducerRecord<String, Object> record = new ProducerRecord<>(Lab02Message.LAB02_TOPIC, 1, null, lab02Message);
        RecordMetadata recordMetadata = new RecordMetadata(new TopicPartition(Lab02Message.LAB02_TOPIC, 1),0, 1, System.currentTimeMillis(), 0,0);
        var lab02SendResult = new SendResult<String, Object>(record, recordMetadata);
        var lf = new SettableListenableFuture();
        lf.set(lab02SendResult);

        when(kafkaTemplate.sendDefault(null, lab02Message)).thenReturn(lf);
        lab02ProducerService.publishEvent(lab02Message);
    }
}
