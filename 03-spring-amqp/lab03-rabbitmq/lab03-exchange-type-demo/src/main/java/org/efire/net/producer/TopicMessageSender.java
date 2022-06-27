package org.efire.net.producer;

import org.efire.net.common.TopicExchangeProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("topic")
public class TopicMessageSender implements CommandLineRunner {

    private RabbitTemplate rabbitTemplate;
    private TopicExchangeProperties props;

    public TopicMessageSender(RabbitTemplate rabbitTemplate, TopicExchangeProperties props) {
        this.rabbitTemplate = rabbitTemplate;
        this.props = props;
    }

    @Override
    public void run(String... args) throws Exception {

        //publish Berlin message
        rabbitTemplate.convertAndSend(props.getExchangeName(), props.berlin().getRoutingKey(),"DEAR BERLIN");

        //publish HeadStore message
        rabbitTemplate.convertAndSend(props.getExchangeName(), props.headstore().getRoutingKey(),"DEAR HEADSTORE");

        //publish ALL message
        rabbitTemplate.convertAndSend(props.getExchangeName(), props.all().getRoutingKey(),"DEAR ALL");
    }
}
