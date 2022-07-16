package org.efire.net;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Supplier;

@SpringBootApplication
@RestController
public class AvroProducer2Application {

    public static void main(String[] args) {
        SpringApplication.run(AvroProducer2Application.class, args);
    }

    BlockingQueue<Sensor> unbounded = new LinkedBlockingQueue<>();

    @PostMapping("/messages")
    public ResponseEntity<String> sendMessage() {
        unbounded.offer(createRandomSensor());
        return ResponseEntity.ok("Success - v2 \n");
    }

    private Sensor createRandomSensor() {
        var random = new Random();
        var sensor = new Sensor();
        sensor.setId(UUID.randomUUID().toString()+"-v2");
        sensor.setAcceleration(random.nextFloat() * 10);
        sensor.setVelocity(random.nextFloat() * 100);
        sensor.setInternalTemperature(random.nextFloat() * 50);
        sensor.setAccelerometer(null);
        sensor.setMagneticField(null);
        return sensor;
    }

    @Bean
    public Supplier<Sensor> supplier() {
        return () -> unbounded.poll();
    }
}
