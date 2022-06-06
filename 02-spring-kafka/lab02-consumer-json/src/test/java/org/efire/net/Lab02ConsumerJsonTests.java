package org.efire.net;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.AcknowledgingConsumerAwareMessageListener;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Import(value = KafkaTestConfig.class)
@TestPropertySource(properties = { "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}" })
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext
@EmbeddedKafka(topics = AppConstants.LAB02_TOPIC)
public class Lab02ConsumerJsonTests {

    @Autowired
    private EmbeddedKafkaBroker broker;
    @Autowired
    private KafkaTemplate<String, Person> kafkaTemplate;
    @Autowired
    protected KafkaListenerEndpointRegistry registry;

    @BeforeEach
    void setUp() {
        broker.brokerProperty("controlled.shutdown.enable", true);

        for (MessageListenerContainer messageListenerContainer : registry
                .getListenerContainers()) {
            System.err.println(messageListenerContainer.getContainerProperties().toString());
            ContainerTestUtils.waitForAssignment(messageListenerContainer, broker.getPartitionsPerTopic());
        }
    }

    @AfterEach
    void tearDown() {
        for (MessageListenerContainer messageListenerContainer : registry
                .getListenerContainers()) {
            messageListenerContainer.stop();
        }

        broker.getKafkaServers().forEach(b -> b.shutdown());
        broker.getKafkaServers().forEach(b -> b.awaitShutdown());
    }

    @Test
    void sendMessagePayloadExpectConsumeInJSON() throws InterruptedException {
        ConcurrentMessageListenerContainer<?, ?> container = (ConcurrentMessageListenerContainer<?, ?>) registry
                .getListenerContainer("lab02-id");
        container.stop();
        @SuppressWarnings("unchecked")
        AcknowledgingConsumerAwareMessageListener<String, Person> messageListener = (AcknowledgingConsumerAwareMessageListener<String, Person>) container
                .getContainerProperties().getMessageListener();
        CountDownLatch latch = new CountDownLatch(1);
        container.getContainerProperties()
                .setMessageListener((AcknowledgingConsumerAwareMessageListener<String, Person>) (data, acknowledgment, consumer) -> {
                    messageListener.onMessage(data, acknowledgment, consumer);
                    latch.countDown();
                });
        container.start();

        var person = new Person();
        person.setName("Efirenetd");
        person.setAddress("Manila, Philippines");

        kafkaTemplate.send(AppConstants.LAB02_TOPIC, person);

        assertThat(latch.await(10, TimeUnit.SECONDS)).isTrue();

    }
}
