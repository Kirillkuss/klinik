package com.klinik.config;

import java.time.Duration;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.core.RedisKeyExpiredEvent;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableRedisRepositories(enableKeyspaceEvents = RedisKeyValueAdapter.EnableKeyspaceEvents.ON_STARTUP)
@EnableCaching
public class CachingConfig {
    
    public RedisCacheConfiguration getTimeOut( Integer time ){
        return RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds( time ));
    }

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return (builder) -> builder.withCacheConfiguration( "cacheDocumentOne", getTimeOut( 8 ) )
                                   .withCacheConfiguration( "cacheDocumentTwo",getTimeOut( 8 ))
                                   .withCacheConfiguration( "doctors", getTimeOut( 5 ));
    } 

    @Bean("customKeyGenerator")
    public KeyGenerator keyGenerator() {
        return new CustomKeyGenerator();
    }

    /**
     * Истечение срока действия ключа
     */
    @Component
    public static class SessionExpiredEventListener {
        
        @EventListener
        public void handleRedisKeyExpiredEvent(RedisKeyExpiredEvent<Object> event) {
            assert event != null;
            log.info("Cache with value = {} ",   event.getValue() );
            log.info("Cache with key = {} has expired", new String(  event.getId()));
        }
    }
    
    
} 
