package com.klinik.testcontainer.docker;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Disabled
@Testcontainers
public class PostgreSQLContainerKlinikaTest {

    @SuppressWarnings({ "rawtypes", "resource" })
    @Container
    private PostgreSQLContainer postgresqlContainer = new PostgreSQLContainer("postgres:latest")
        .withDatabaseName("Klinika")
        .withUsername("postgres")
        .withPassword("admin");

   @Test
    void testRunningContainer() {
        postgresqlContainer.isRunning();
    }
    
}
