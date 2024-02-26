package com.klinik.cassandra.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@PropertySource(value = { "classpath:cassandra.properties" })
@ComponentScan("com.klinik.cassandra")
@EnableCassandraRepositories( basePackages = "com.klinik.cassandra.repository")
public class CassandraConfig {

}
