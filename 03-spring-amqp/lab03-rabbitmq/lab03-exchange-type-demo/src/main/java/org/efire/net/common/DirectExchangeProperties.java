package org.efire.net.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Map;

@Profile(value = "direct")
@Component
@ConfigurationProperties(prefix = "lab03")
@Getter @Setter
public class DirectExchangeProperties {

    private String exchangeName;

    private Map<String, QueueRoutingKey> bindings;

    @Getter @Setter
    public static class QueueRoutingKey {
        String queueName;
        String routingKey;
    }

    public QueueRoutingKey pdfCreate() {
        return bindings.get("pdfCreate");
    }

    public QueueRoutingKey pdfLog() {
        return bindings.get("pdfLog");
    }
}
