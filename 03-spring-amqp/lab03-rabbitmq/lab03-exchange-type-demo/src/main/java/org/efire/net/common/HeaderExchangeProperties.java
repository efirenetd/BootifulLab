package org.efire.net.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Profile("header")
@ConfigurationProperties(prefix = "lab03")
@Getter @Setter
public class HeaderExchangeProperties {

    private String exchangeName;
    private Map<String, HeaderBindingTypes> bindings;

    @Getter @Setter
    public static class HeaderBindingTypes {

        String queueName;
        Map<String, Object> headers;
    }

    public HeaderBindingTypes getPdfReport() {
        return getBindings().get("pdfReport");
    }

    public HeaderBindingTypes getZipReport() {
        return getBindings().get("zipReport");
    }

    public HeaderBindingTypes getPdfLog() {
        return getBindings().get("pdfLog");
    }

}
