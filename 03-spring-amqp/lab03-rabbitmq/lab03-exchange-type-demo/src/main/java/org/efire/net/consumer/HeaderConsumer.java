package org.efire.net.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("header")
@Slf4j
public class HeaderConsumer {
    @RabbitListener(queues = "pdf_report_queue")
    public void consumePdfReport(String message) {
        log.info("PDF REPORT message received >>>>> {} <<<<<", message);
    }

    @RabbitListener(queues = "pdf_log_queue")
    public void consumePdfLog(String message) {
        log.info("PDF LOG message received >>>>> {} <<<<<", message);
    }

    @RabbitListener(queues = "zip_report_queue")
    public void consumeZipReport(String message) {
        log.info("ZIP REPORT message received >>>>> {} <<<<<", message);
    }
}
