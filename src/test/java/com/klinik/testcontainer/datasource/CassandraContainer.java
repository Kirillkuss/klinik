package com.klinik.testcontainer.datasource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class CassandraContainer {

    @SuppressWarnings("resource")
    @Container
    private static GenericContainer<?> cassandraContainer = new GenericContainer<>("cassandra:latest")
                                                                    .withExposedPorts(9042)
                                                                    .withCreateContainerCmdModifier(cmd -> cmd.withName("cassandra_test"));                                                                                                                       
    @BeforeAll
    public static void setUpClass() throws Exception{
        cassandraContainer.start();
        Thread.sleep( 6000 );
    }

    @AfterAll
    public static void tearDownClass() throws InterruptedException {
        Thread.sleep(5000 ); 
        cassandraContainer.stop();
    }

    @Test
    public void testFirst(){
        System.out.println( "FIRST");
    }
    
}
