package org.efire.net;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class Lab02App {

    public static void main(String[] args) {
        SpringApplication.run(Lab02App.class, args);
    }

    @RestController
    @RequestMapping("/lab02")
    @Log4j2
    static class Resource  {

        Lab02ProducerService service;

        public Resource(Lab02ProducerService service) {
            this.service = service;
        }

        @PostMapping
        public void send(@RequestBody Lab02Message message) {
            log.info("Sending message to topic");
            service.publishEvent(message);
        }
    }
}
