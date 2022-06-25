package org.efire.net.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Profile(value = "fanout")
@Component
@ConfigurationProperties(prefix = "lab03")
@Getter
@Setter
public class FanoutExchangeProperties {

    private String exchangeName;

    private Map<String, SportsQueueNames> bindings;

    @Getter
    @Setter
    public static class SportsQueueNames {
        List<String> sportsChannelQueueA;
    }

    public List<String> sportsChannelQueueA() {
        return this.bindings.get("sportsChannelQueueA").getSportsChannelQueueA();
    }

}
