package org.efire.net.config;

import org.efire.net.common.DirectExchangeProperties;
import org.efire.net.consumer.Lab03Consumer;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value = "direct")
public class DirectExchangeConfig {

    @Autowired
    private DirectExchangeProperties props;

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(getPdfCreate().getQueueName(), getPdfLog().getQueueName());
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Lab03Consumer consumer) {
        return new MessageListenerAdapter(consumer, "receiveMessage");
    }


    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(props.getExchangeName(), true, false);
    }

    @Bean
    public Queue createPdfQueue() {
        return new Queue(getPdfCreate().getQueueName(),
                true,
                false,
                false);
    }

    private DirectExchangeProperties.QueueRoutingKey getPdfCreate() {
        return props.getBindings().get("pdfCreate");
    }

    @Bean
    public Queue pdfLogQueue() {
        return new Queue(getPdfLog().getQueueName(),
                true,
                false,
                false);
    }

    private DirectExchangeProperties.QueueRoutingKey getPdfLog() {
        return props.getBindings().get("pdfLog");
    }

    @Bean
    public Binding bindingPdfCreate(DirectExchange exchange) {
        return BindingBuilder
                .bind(createPdfQueue())
                .to(exchange)
                .with(getPdfCreate().getRoutingKey());
    }

    @Bean
    public Binding bindingPdfLog(DirectExchange exchange) {
        return BindingBuilder
                .bind(pdfLogQueue())
                .to(exchange)
                .with(getPdfLog().getRoutingKey());
    }
}
