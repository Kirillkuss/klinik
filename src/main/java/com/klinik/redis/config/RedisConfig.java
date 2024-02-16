package com.klinik.redis.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@ComponentScan("com.klinik.redis")
@EnableRedisRepositories(basePackages = "com.klinik.redis.repository")
public class RedisConfig {
}
