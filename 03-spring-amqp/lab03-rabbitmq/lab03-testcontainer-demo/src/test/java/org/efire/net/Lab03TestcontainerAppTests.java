package org.efire.net;

import lombok.val;
import org.efire.net.producer.DemoMessageSender;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.OutputCaptureRule;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Lab03TestcontainerApp.class)
@ContextConfiguration(initializers = Lab03TestcontainerAppTests.Initializer.class)
@DirtiesContext
public class Lab03TestcontainerAppTests {

    @ClassRule
    public static GenericContainer rabbit = new GenericContainer("rabbitmq:3-management-alpine")
            .withExposedPorts(5672, 15672);

    @Rule
    public OutputCaptureRule outputCapture = new OutputCaptureRule();

    @Autowired
    private DemoMessageSender messageSender;

    @Test
    public void testBroadcast() {
        String msg = "Broadcast Test";
        messageSender.broadcast(msg);
        await().atMost(5, TimeUnit.SECONDS).until(isMessageConsumed(msg), Predicate.isEqual(true));
    }

    @Test
    public void testErrorMessage() {
        String errMsg = "Houston we have a problem!";
        messageSender.sendError(errMsg);
        await().atMost(5, TimeUnit.SECONDS).until(isMessageConsumed(errMsg), Predicate.isEqual(true));
    }

    private Callable<Boolean> isMessageConsumed(String message) {
        return () -> outputCapture.toString().contains(message);
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext cxt) {
            val values = TestPropertyValues.of(
                    "spring.rabbitmq.host=" + rabbit.getHost(),
                    "spring.rabbitmq.port=" + rabbit.getMappedPort(5672)
            );
            values.applyTo(cxt);
        }
    }
}
