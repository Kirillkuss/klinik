package com.klinik.testcontainer.docker;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Random;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.klinik.entity.Document;
import com.klinik.repositories.DocumentRepository;
import com.klinik.rest.RestSession;
import com.klinik.service.DocumentService;
import com.klinik.ui.LoginSuccess;

@Disabled
@Testcontainers
@SpringBootTest
public class PostgreSQLContainerKlinikaTest {

    @SuppressWarnings("resource")
    @Container
    private static PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:latest")
                                                                    .withDatabaseName("Klinika")
                                                                    .withUsername("postgres")
                                                                    .withPassword("admin");

    private static String URL;
    private static final String USER     = postgresqlContainer.getUsername(); 
    private static final String PASSWORD = postgresqlContainer.getPassword();
    private Document document;

    @BeforeAll
    public static void setUpClass() {
        URL = String.format("jdbc:postgresql://%s:%d/%s", postgresqlContainer.getHost(),
                                                                 postgresqlContainer.getMappedPort( PostgreSQLContainer.POSTGRESQL_PORT ),
                                                                 postgresqlContainer.getDatabaseName());
        postgresqlContainer.start();
        try (Connection conn = DriverManager.getConnection( URL, USER, PASSWORD );
            Statement stmt = conn.createStatement()) {
            stmt.execute( new String(Files.readAllBytes(Paths.get("src/main/resources/db/init_db.sql"))));
            System.out.println("Add tables to DataBase Klinika  SUCCESS >>>>>>>>>>>>>>> ");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void setUp() {
        document = RestSession.getDocument();
    }

    @AfterAll
    public static void tearDownClass() {
        postgresqlContainer.stop();
    }

    @Autowired private DocumentRepository documentRepository;
    @Autowired private DocumentService documentService;
    /**
     * Подключение к созданной БД в контейнере
     */
    @TestConfiguration
    static class TestConnectDataBase {
        @Bean
        public DataSource dataSource() {
            return new DriverManagerDataSource( URL, USER, PASSWORD );
        }
    }
    /**
     * Сохранение дока
     * @throws Exception
     */
    @Test
    void testSaveDocument() throws Exception {
        assertNotNull( documentService.addDocument( document ));
    }

    /**
     * Поиск дока
     */
    @Test
    void testFindAll() {
        documentRepository.save( document );
        assertNotNull( documentService.getAllDocuments());
        assertNotNull( documentService.findByWord( document.getPolis()));
        assertNotNull( documentService.getLazyDocuments( 1, 12 ));
    }
    
}
