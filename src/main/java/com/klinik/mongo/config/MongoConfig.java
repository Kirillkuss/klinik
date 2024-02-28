package com.klinik.mongo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@PropertySource(value = { "classpath:mongo.properties" })
@EnableMongoRepositories( basePackages = "com.klinik.mongo.repository")
public class MongoConfig {
}
