package org.efire.net.producer;

import org.efire.net.common.FanoutExchangeProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("fanout")
public class FanoutProducer implements CommandLineRunner {

    private RabbitTemplate rabbitTemplate;
    private FanoutExchangeProperties props;

    public FanoutProducer(RabbitTemplate rabbitTemplate, FanoutExchangeProperties props) {
        this.rabbitTemplate = rabbitTemplate;
        this.props = props;
    }

    @Override
    public void run(String... args) throws Exception {
        rabbitTemplate.convertAndSend(props.getExchangeName(), "", "HELLO SPORTS FANS!!!");
    }
}
