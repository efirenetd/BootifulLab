package org.efire.net;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Lab03ExchangeTypeApp {

    public static void main(String[] args) {
        SpringApplication.run(Lab03ExchangeTypeApp.class, args);
    }
}
