package com.klinik.redis.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@ComponentScan("com.klinik.redis")
@EnableRedisRepositories( basePackages = "com.klinik.redis.repository",
                          enableKeyspaceEvents = RedisKeyValueAdapter.EnableKeyspaceEvents.ON_STARTUP )
public class RedisConfig {
    /** 
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new JedisConnectionFactory();
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

