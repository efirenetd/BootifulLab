package org.efire.net.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("fanout")
@Slf4j
public class FanoutConsumer {

    @RabbitListener(queues = "espn_sports_news_queue")
    public void consumeEspnSports(String message) {
        log.info("ESPN NEWS message received >>>>> {} <<<<<", message);
    }

    @RabbitListener(queues = "yahoo_sports_news_queue")
    public void consumeYahooSports(String message) {
        log.info("YAHOO SPORTS message received >>>>> {} <<<<<", message);
    }

    @RabbitListener(queues = "theguardian_sports_news_queue")
    public void consumeTheGuardianSports(String message) {
        log.info("THE GUARDIAN message received >>>>> {} <<<<<", message);
    }
}
