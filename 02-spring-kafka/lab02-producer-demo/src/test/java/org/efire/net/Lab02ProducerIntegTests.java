package org.efire.net;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.RequestEntity;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilderFactory;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"spring.kafka.consumer.bootstrap-servers=${spring.embedded.kafka.brokers}"})
@EmbeddedKafka(topics = {Lab02Message.LAB02_TOPIC}, partitions = 1)
@Log4j2
public class Lab02ProducerIntegTests {

    @Autowired
    private EmbeddedKafkaBroker broker;
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    private Consumer<String, Object> consumer;

    @Test
    void testResourcePostMessage() {

        Map<String, Object> configs = new HashMap<>(KafkaTestUtils.consumerProps("group1","true",broker));
       consumer = new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(), new JsonDeserializer<>())
                .createConsumer();
        broker.consumeFromAllEmbeddedTopics(consumer);

        var lab02Message = new Lab02Message();
        lab02Message.setEventId(null);
        lab02Message.setMessage("Test");

        UriBuilderFactory factory = new DefaultUriBuilderFactory();
        var builder = factory.builder();
        var uri = builder.scheme("http").host("localhost").port(port).path("lab02").build();
        var requestEntity = RequestEntity.post(uri).body(lab02Message);
        this.restTemplate.exchange(requestEntity, Lab02Message.class);
        ConsumerRecord<String, Object> singleRecord = KafkaTestUtils.getSingleRecord(consumer, Lab02Message.LAB02_TOPIC);

    }

    @AfterEach
    public void dummy() {
        consumer.close();
    }
}
