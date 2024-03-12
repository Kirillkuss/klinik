package com.klinik.redis.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@ComponentScan("com.klinik.redis")
@PropertySource( value = { "classpath:redis.properties"})
@EnableRedisRepositories( basePackages = "com.klinik.redis.repository")
public class RedisConfig {
     
    /**
     * RedisTemplate для entity Session
     * @param connectionFactory
     * @return RedisTemplate<String, Person> 
     */
   /** @Bean
    public RedisTemplate<String, Session> redisTemplateSession(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Session> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Session.class));
        return template;
    } */

}

