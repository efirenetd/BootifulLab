package org.efire.net;

import lombok.extern.slf4j.Slf4j;
import org.efire.net.config.RabbitMQConfig;
import org.efire.net.producer.MessageProducer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class Lab03PubConfirmsReturnsApp implements CommandLineRunner {

    @Autowired
    private MessageProducer messageProducer;

    public static void main(String[] args) {
        SpringApplication.run(Lab03PubConfirmsReturnsApp.class, args);
    }

    @RabbitListener(queues = RabbitMQConfig.DEMO_DIRECT_QUEUE_NAME)
    public void onMessage(String message) {
        log.info("=== Received message: {} === ", message);
    }

    @Override
    public void run(String... args) throws Exception {
        messageProducer.sendMessage();
    }
}
