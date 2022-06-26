package org.efire.net.config;

import org.efire.net.common.HeaderExchangeProperties;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("header")
public class HeaderExchangeConfig {

    @Autowired
    private HeaderExchangeProperties props;

    @Bean
    public Declarables headerBindings() {
        var headerExchange = new HeadersExchange(props.getExchangeName());
        var pdfReportQueue = QueueBuilder.durable(props.getPdfReport().getQueueName()).build();
        var zipReportQueue = QueueBuilder.durable(props.getZipReport().getQueueName()).build();
        var pdfLogQueue = QueueBuilder.durable(props.getPdfLog().getQueueName()).build();

        return new Declarables(pdfReportQueue, pdfLogQueue, zipReportQueue, headerExchange,
                BindingBuilder
                        .bind(pdfReportQueue)
                        .to(headerExchange)
                        .whereAll(props.getPdfReport().getHeaders()).match(),
                BindingBuilder
                        .bind(pdfLogQueue)
                        .to(headerExchange)
                        .whereAny(props.getPdfLog().getHeaders()).match(),
                BindingBuilder
                        .bind(zipReportQueue)
                        .to(headerExchange)
                        .whereAll(props.getZipReport().getHeaders()).match());
    }

}
