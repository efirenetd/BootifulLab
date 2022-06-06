package org.efire.net;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.AcknowledgingConsumerAwareMessageListener;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Lab02ConsumerApp.class)
@Import(value = KafkaTestContainerConfig.class)
@TestPropertySource(properties = { "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}" })
@TestInstance(Lifecycle.PER_CLASS)
@DirtiesContext
@EmbeddedKafka(topics = Lab02Message.LAB02_TOPIC)
public class Lab02ConsumerEmbeddedKafkaTest {

/*    @Container
    public static KafkaContainer kafka = new KafkaContainer(
            DockerImageName.parse("confluentinc/cp-kafka:6.2.1"));*/

    @Autowired
    private EmbeddedKafkaBroker broker;
    @Value("${spring.embedded.kafka.brokers}")
    private String brokerAddress;


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
    void testLab02ConsumerExpectMessageReceived() throws InterruptedException {

        ConcurrentMessageListenerContainer<?, ?> container = (ConcurrentMessageListenerContainer<?, ?>) registry
                .getListenerContainer("lab02-id");
        container.stop();
        @SuppressWarnings("unchecked")
        AcknowledgingConsumerAwareMessageListener<String, String> messageListener = (AcknowledgingConsumerAwareMessageListener<String, String>) container
                .getContainerProperties().getMessageListener();
        CountDownLatch latch = new CountDownLatch(1);
        container.getContainerProperties()
                .setMessageListener(new AcknowledgingConsumerAwareMessageListener<String, String>() {
                    @Override
                    public void onMessage(ConsumerRecord<String, String> data, Acknowledgment acknowledgment,
                                          Consumer<?, ?> consumer) {
                        messageListener.onMessage(data, acknowledgment, consumer);
                        latch.countDown();
                    }

                });
        container.start();

/*        var lab02Message = new Lab02Message();
        lab02Message.setEventId(null);
        lab02Message.setMessage("Test Test Test Lab02");*/

        //ProducerRecord<String, Object> record = new ProducerRecord<>(Lab02Message.LAB02_TOPIC, 1, null, lab02Message);

        ProducerRecord<String, String> record = new ProducerRecord<>(Lab02Message.LAB02_TOPIC, 1,
                null, "TEST TEST TEST");
        kafkaTemplate.send(record);

        assertThat(latch.await(10, TimeUnit.SECONDS)).isTrue();

    }
}
