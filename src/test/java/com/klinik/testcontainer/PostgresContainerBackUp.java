package com.klinik.testcontainer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.klinik.cassandra.repository.DocumentRepositoryCassandra;
import com.klinik.mongo.repository.DocumentRepositiryMongo;
import com.klinik.redis.repository.SessionRepository;
import com.klinik.service.DocumentService;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.klinik.redis.model.Session;

@Disabled
@Testcontainers
@SpringBootTest
@DisplayName("Тестирование контейнера PostgreSQLContainer с загрузкой БД из backup")
public class PostgresContainerBackUp {

    @SuppressWarnings("resource")
    @Container
    private static PostgreSQLContainer<?> postgresSQLContainer = new PostgreSQLContainer<>("postgres:latest" )
                                                                        .withDatabaseName("Klinika" )
                                                                        .withUsername("postgres" )
                                                                        .withPassword("admin" )
                                                                        .withCreateContainerCmdModifier(cmd -> cmd.withName("postgres_test" ));

   
   @SuppressWarnings("resource")
   @Container
   private static GenericContainer<?> mongoContainer = new GenericContainer<>("mongo:latest")
                                                           .withExposedPorts(27017)
                                                           .withEnv("MONGO_INITDB_ROOT_USERNAME", "admin")
                                                           .withEnv("MONGO_INITDB_ROOT_PASSWORD", "password")
                                                           .withCreateContainerCmdModifier(cmd -> cmd.withName("mongo_test"));
    
    @SuppressWarnings("resource")
    @Container
    private static GenericContainer<?> redisContainer = new GenericContainer<>("redis:latest")
                                                            .withExposedPorts(6379)
                                                            .withCreateContainerCmdModifier(cmd -> cmd.withName("redis_test"));

    /**@SuppressWarnings("resource")
    @Container
    private static GenericContainer<?> cassandraContainer = new GenericContainer<>("cassandra:latest")
                                                                    .withExposedPorts(9042)
                                                                    .withCreateContainerCmdModifier(cmd -> cmd.withName("cassandra_test"));*/                                                          
    
    private static String URL;
    private static final String USER     = postgresSQLContainer.getUsername(); 
    private static final String PASSWORD = postgresSQLContainer.getPassword();
                                                                        
    @BeforeAll
    public static void setUpClass() throws Exception{
        URL = String.format("jdbc:postgresql://%s:%d/%s", postgresSQLContainer.getHost(),
                                                                 postgresSQLContainer.getMappedPort( PostgreSQLContainer.POSTGRESQL_PORT ),
                                                                 postgresSQLContainer.getDatabaseName());
        postgresSQLContainer.start();
        mongoContainer.start();
        redisContainer.start();
        //cassandraContainer.start();
        BackupPostgres.getRestoreDataBase( postgresSQLContainer.getMappedPort( PostgreSQLContainer.POSTGRESQL_PORT ));
        Thread.sleep( 6000 );
        RestoreMongodb.getRestoreDataBaseMongo( "mongo_test" );
        System.out.println( "port: " + mongoContainer.getFirstMappedPort());

    }

    @AfterAll
    public static void tearDownClass() throws InterruptedException {
        Thread.sleep(10000 ); 
        postgresSQLContainer.stop();
        mongoContainer.stop();
        redisContainer.stop();
        //cassandraContainer.stop();
    }

    @TestConfiguration
    @DisplayName("Конфигурация для подключения к созданным тест-контейнерам")
    static class TestConnectDataBase extends AbstractMongoClientConfiguration {

        @Override
        public MongoClient mongoClient() {
            return MongoClients.create("mongodb://admin:password@localhost:" +mongoContainer.getFirstMappedPort()+"/mongo?authSource=admin");
        }
    
        @Override
        protected String getDatabaseName() {
            return "mongo";
        }

        @Bean
        public DataSource dataSource() {
            return new DriverManagerDataSource( URL, USER, PASSWORD );
        }

        @Bean
        public RedisConnectionFactory redisConnectionFactory() {
            return new LettuceConnectionFactory( new RedisStandaloneConfiguration( redisContainer.getHost(), redisContainer.getMappedPort( 6379 )));
        }

        @Bean
        public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
            RedisTemplate<String, Object> template = new RedisTemplate<>();
            template.setConnectionFactory( redisConnectionFactory );
            return template;
        }

        /**@Bean
        public CqlSessionFactoryBean cassandraSession() {
            CqlSessionFactoryBean session = new CqlSessionFactoryBean();
                                  session.setContactPoints(cassandraContainer.getHost() + ":" + cassandraContainer.getMappedPort(9042));
                                  session.setLocalDatacenter("datacenter1"); 
            return session;
        }*/
    }

    @Autowired private DocumentRepositiryMongo documentRepositiryMongo;
    @Autowired private DocumentRepositoryCassandra documentRepositoryCassandra;
    @Autowired private DocumentService documentService;
    @Autowired private SessionRepository sessionRepository;

    @Test
    @Order(1)
    @DisplayName("Получение списка документов из бд Postgres")
    public void testGetLazyDocument(){
        System.out.println( "1. Postgres: \n " + documentService.getLazyDocuments(1, 10));
        assertEquals(documentService.getLazyDocuments(1, 10), documentService.getLazyDocuments(1, 10));
    }

    @Test
    @Order(2)
    @DisplayName("Получение списка документов из бд Cassandra")
    public void testGetCassandraDocuments(){
        System.out.println( "2. Cassandra: \n" + documentRepositoryCassandra.findAll() );
        assertEquals( documentRepositoryCassandra.findAll(), documentRepositoryCassandra.findAll());
    }

    @Test
    @Order(3)
    @DisplayName("Получение списка документов из бд Mongo")
    public void testGetMongoDocuments(){
        System.out.println( "3. Mongo: \n" + documentRepositiryMongo.findAll() );
        assertEquals( documentRepositiryMongo.findAll(), documentRepositiryMongo.findAll());
    }

    @Test
    @Order(4)
    @DisplayName("Добавление документа в бд Mongo")
    public void testAddMongoDocuments(){
        com.klinik.mongo.model.Document document =  new com.klinik.mongo.model.Document( "123465", "test", "test", "test", "test", "test" );
        com.klinik.mongo.model.Document response = documentRepositiryMongo.save(document);
        System.out.println( "4. Mongo save : \n" + response );
        assertNotNull( response );
    }


    
    @Test
    @Order(5)
    @DisplayName("Добавление сессии в бд Redis")
    public void testAddRedisEntity( ){
        Session session = sessionRepository.save( new Session( null, "AddRedisEntity", 5 ));
        System.out.println( "5. Create redis : \n" + session );
        assertNotNull( session );
    }


    
}
