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
public class HeaderMessageSender implements CommandLineRunner {

    private RabbitTemplate rabbitTemplate;
    private HeaderExchangeProperties props;

    public HeaderMessageSender(RabbitTemplate rabbitTemplate, HeaderExchangeProperties props) {
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
        /**
         *  Message is published to exchange 'agreeement' and is delivered to
         *  pdf_report_queue because all key/value pairs match( format = pdf and type = report, x-match = all)
         *  and
         *  pdf_log_queue since "format = pdf" is a match (because binding rule was set to "x-match = any")
         */
        rabbitTemplate.convertAndSend(props.getExchangeName(), "", messageToPdfAndLog);

        // lambda implementation sending message
        /**
         *  Message is publish to exchange 'agreements' and is delivered to
         *  zip_report_queue because its key/value header pairs only to this binding rule
         *  (format = zip, type = report, x-match = all)
         */
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
