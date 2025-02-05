package com.klinik.testcontainer.datasource;

import javax.sql.DataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.klinik.testcontainer.BackupPostgres;

@Testcontainers
public class PostgresContainer {


    @SuppressWarnings("resource")
    @Container
    private static PostgreSQLContainer<?> postgresSQLContainer = new PostgreSQLContainer<>("postgres:latest" )
                                                                        .withDatabaseName("Klinika" )
                                                                        .withUsername("postgres" )
                                                                        .withPassword("admin" )
                                                                        .withCreateContainerCmdModifier(cmd -> cmd.withName("postgres_test_2" ));

    private static String URL;
    private static final String USER     = postgresSQLContainer.getUsername(); 
    private static final String PASSWORD = postgresSQLContainer.getPassword();

    
    @BeforeAll
    public static void setUpClass() throws Exception{
        URL = String.format("jdbc:postgresql://%s:%d/%s", postgresSQLContainer.getHost(),
                                                                 postgresSQLContainer.getMappedPort( PostgreSQLContainer.POSTGRESQL_PORT ),
                                                                 postgresSQLContainer.getDatabaseName());
        postgresSQLContainer.start();
        BackupPostgres.getRestoreDataBase( postgresSQLContainer.getMappedPort( PostgreSQLContainer.POSTGRESQL_PORT ));
        Thread.sleep( 6000 );
    }

    @AfterAll
    public static void tearDownClass() throws InterruptedException {
        postgresSQLContainer.stop();
    }
    
    @TestConfiguration
    @DisplayName("Конфигурация для подключения к созданным тест-контейнерам")
    public static class TestConnectDataBase   {
        @Bean
        public DataSource dataSource() {
            return new DriverManagerDataSource( URL, USER, PASSWORD );
        }


    }


}
