package org.efire.net.repository;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

public abstract class BasePostgresContainerTest {

    @Container
    protected static final PostgreSQLContainer postgres;

    static {
        postgres = new PostgreSQLContainer("postgres:13.1-alpine")
                .withDatabaseName("lab05db")
                .withUsername("postgres")
                .withPassword("password");
        postgres.withUrlParam("stringtype","unspecified");
        //Mapped port can only be obtained after container is started.
        postgres.start();
    }

    static class PropertiesInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues
                    .of("spring.datasource.driver-class-name=" + postgres.getDriverClassName(),
                            "spring.datasource.url=" + postgres.getJdbcUrl(),
                            "spring.datasource.username=" + postgres.getUsername(),
                            "spring.datasource.password=" + postgres.getPassword())
                    .applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
