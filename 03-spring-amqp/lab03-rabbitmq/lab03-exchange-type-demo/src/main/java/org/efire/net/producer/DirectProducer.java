package org.efire.net.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.efire.net.common.DirectExchangeProperties;
import org.efire.net.message.Lab03MessageA;
import org.efire.net.message.Lab03MessageB;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Profile("direct")
public class DirectProducer implements CommandLineRunner {

    private RabbitTemplate rabbitTemplate;
    private ObjectMapper objectMapper;
    private DirectExchangeProperties props;

    public DirectProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper, DirectExchangeProperties props) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
        this.props = props;
    }

    @Override
    public void run(String... args) throws Exception {
        var pdfCreateMessage = new Lab03MessageA(UUID.randomUUID().toString(), "aPDFFile.pdf", 10.50);
        var jsonStringA = objectMapper.writeValueAsString(pdfCreateMessage);
        rabbitTemplate.convertAndSend(props.getExchangeName(), props.pdfCreate().getRoutingKey(), jsonStringA);

        var pdfLogMessage = new Lab03MessageB("sender@gmail.com",
                "receiver@gmail.com",
                LocalDateTime.now().toString(),
                "Hello There!!!");

        var jsonStringB = objectMapper.writeValueAsString(pdfLogMessage);
        rabbitTemplate.convertAndSend(props.getExchangeName(), props.pdfLog().getRoutingKey(), jsonStringB);
    }
}
