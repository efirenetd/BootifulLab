package org.efire.net.config;

import org.efire.net.common.FanoutExchangeProperties;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.stream.Collectors;

@Configuration
@Profile("fanout")
public class FanoutExchangeConfig {

    private FanoutExchangeProperties props;

    public FanoutExchangeConfig(FanoutExchangeProperties props) {
        this.props = props;
    }

    @Bean
    public Declarables topicBindings() {
        FanoutExchange fanoutExchange = new FanoutExchange(props.getExchangeName());
        Declarables declarables = new Declarables(fanoutExchange);
        declarables.getDeclarables().addAll(
                props.sportsChannelQueueA().stream()
                        .map(queueName -> QueueBuilder.durable(queueName).build())
                        .collect(Collectors.toList()));
        return declarables;
    }
}
