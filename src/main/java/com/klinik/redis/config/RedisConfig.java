package com.klinik.redis.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@ComponentScan("com.klinik.redis")
@PropertySource(value = { "classpath:redis.properties" })
@EnableRedisRepositories( basePackages = "com.klinik.redis")
public class RedisConfig {
    
    /**@Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new JedisConnectionFactory();
    }*/
    /**
     * RedisTemplate для entity Person
     * @param connectionFactory
     * @return RedisTemplate<String, Person> 
     */
    /**@Bean
    public RedisTemplate<String, Person> redisTemplate() {
        RedisTemplate<String, Person> template = new RedisTemplate<>();
        template.setConnectionFactory( redisConnectionFactory() );
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Person.class));
        return template;
    }*/
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

