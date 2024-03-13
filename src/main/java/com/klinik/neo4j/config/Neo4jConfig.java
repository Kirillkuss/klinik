package com.klinik.neo4j.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.neo4j.cypherdsl.core.renderer.Dialect;

//@ComponentScan("com.klinik.neo4j")
@EnableNeo4jRepositories( basePackages = "com.klinik.neo4j.repository")
@PropertySource( value = { "classpath:neo4j.properties"})
@Configuration
public class Neo4jConfig {

    @Bean
    org.neo4j.cypherdsl.core.renderer.Configuration cypherDslConfiguration() {
        return org.neo4j.cypherdsl.core.renderer.Configuration.newConfig().withDialect(Dialect.NEO4J_5).build();
    }
    
}
