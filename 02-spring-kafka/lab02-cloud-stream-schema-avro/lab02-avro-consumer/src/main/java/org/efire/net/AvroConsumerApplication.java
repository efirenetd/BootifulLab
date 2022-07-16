package org.efire.net;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

@SpringBootApplication
@Slf4j
public class AvroConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AvroConsumerApplication.class, args);
    }

    @Bean
    public Consumer<Sensor> process() {
        return input -> log.info("Input: {} ", input);
    }
}
