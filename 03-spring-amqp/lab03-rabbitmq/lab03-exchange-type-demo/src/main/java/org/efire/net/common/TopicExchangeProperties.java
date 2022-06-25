package org.efire.net.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Map;

@Profile(value = "topic")
@Component
@ConfigurationProperties(prefix = "lab03")
@Getter @Setter
public class TopicExchangeProperties {

    private String exchangeName;

    private Map<String, TopicExchangeProperties.QueueRoutingKey> bindings;

    @Getter
    @Setter
    public static class QueueRoutingKey {
        String queueName;
        String routingKey;
    }

    public QueueRoutingKey berlin() {
        return this.bindings.get("berlin");
    }

    public QueueRoutingKey all() {
        return this.bindings.get("all");
    }

    public QueueRoutingKey headstore() {
        return this.bindings.get("headstore");
    }

}
