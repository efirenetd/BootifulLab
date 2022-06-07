package org.efire.net;

import org.efire.net.common.AppConstant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = { "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}" })
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext
@EmbeddedKafka(topics = AppConstant.LAB02_TOPIC)
public class Lab02SimpleErrorHandlerAppTests {

    @Autowired
    private EmbeddedKafkaBroker broker;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
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
    void consumeMessageGivenThrowAnExceptionExpectHandleError() throws InterruptedException {
        ConcurrentMessageListenerContainer<?, ?> container = (ConcurrentMessageListenerContainer<?, ?>) registry
                .getListenerContainer(AppConstant.LAB02_ID);
        container.stop();
        @SuppressWarnings("unchecked")
        AcknowledgingConsumerAwareMessageListener<String, String> messageListener = (AcknowledgingConsumerAwareMessageListener<String, String>) container
                .getContainerProperties().getMessageListener();
        CountDownLatch latch = new CountDownLatch(1);
        container.getContainerProperties()
                .setMessageListener((AcknowledgingConsumerAwareMessageListener<String, String>) (data, acknowledgment, consumer) -> {
                    messageListener.onMessage(data, acknowledgment, consumer);
                    latch.countDown();
                });
        container.start();

        kafkaTemplate.send(AppConstant.LAB02_TOPIC, "TEST ERROR HANDLER");

        assertThat(latch.await(10, TimeUnit.SECONDS)).isFalse();
    }
}
