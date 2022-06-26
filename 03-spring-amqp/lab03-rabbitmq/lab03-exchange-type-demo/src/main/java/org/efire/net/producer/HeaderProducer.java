package org.efire.net.producer;

import org.efire.net.common.HeaderExchangeProperties;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("header")
public class HeaderProducer implements CommandLineRunner {

    private RabbitTemplate rabbitTemplate;
    private HeaderExchangeProperties props;

    public HeaderProducer(RabbitTemplate rabbitTemplate, HeaderExchangeProperties props) {
        this.rabbitTemplate = rabbitTemplate;
        this.props = props;
    }

    @Override
    public void run(String... args) throws Exception {
        // using a MessageBuilder to create a message and pass as an argument
        var messageToPdfAndLog = MessageBuilder.withBody(
                "HI PDF REPORT with HEADER".getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                .setHeader("format", "pdf")
                .setHeader("type", "report")
                .build();
        // publish message to pdf_report_queue and pdf_log_queue
        rabbitTemplate.convertAndSend(props.getExchangeName(), "", messageToPdfAndLog);

        // lambda implementation sending message
        // publish message to zip_report_queue and pdf_log_queue
        rabbitTemplate.convertAndSend(props.getExchangeName(), "",
                "HI ZIP REPORT via HEADER",
                m -> {
                    var headers = m.getMessageProperties().getHeaders();
                    headers.put("format", "zip");
                    headers.put("type", "report");
                    return m;
                });
    }
}
