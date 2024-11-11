package com.klinik.aspect.cache.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Slf4j
@EnableCaching
@Configuration
public class CaffeineConfiguration{
    
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
                             caffeineCacheManager.registerCustomCache("cachePatient", cachePatientCaffeine());
                             caffeineCacheManager.registerCustomCache("cacheDoctor", cacheDoctorCaffeine());
                             caffeineCacheManager.setCacheNames(Collections.emptyList());
        return caffeineCacheManager;
    }

    private static Cache<Object, Object> cachePatientCaffeine() {
        return Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(5, TimeUnit.SECONDS)
                .removalListener((key, value, cause) -> {
                    log.info( "CachePatient removed >>> key=" + key  + ", cause=" + cause); 
                })
                .build();
    }

    private static Cache<Object, Object> cacheDoctorCaffeine() {
        return Caffeine.newBuilder()
                .maximumSize(2000)
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .removalListener((key, value, cause) -> { 
                    log.info( "CacheDoctor removed >>> key=" + key  + ", cause=" + cause); 
                })
                .build();
    }

    /**@Bean
    public Caffeine caffeineConfig() {
        return Caffeine.newBuilder()
                       .expireAfterWrite(10, TimeUnit.SECONDS) // Время жизни элемента после записи
                       //.expireAfterAccess(15, TimeUnit.SECONDS)// Время жизни элемента после последнего доступа (LRU)
                       .maximumSize(1000)// Максимальное кол-во элементов в кэше
                       //.maximumWeight(10000) // Максимальный вес элементов в кэше 
                        .removalListener((key, value, cause) -> { // удаления элементов 
                            log.info( "Cache removed: key=" + key  + ", cause=" + cause); 
                        })
                        //Политика замены элементов (если кэш заполнен)
                        //.weigher((key, value) -> 1) //Пример весовой функции. 1 - каждый элемент весит 1
                        //.weakKeys() // если не нужно блокировать GC удаление ключей
                        //.weakValues() //если не нужно блокировать GC удаление значений
                        //.executor(Executors.newFixedThreadPool(4)) //Настройка потоков для обновления кэша
                        .recordStats();//Включение сбора статистики (для мониторинга)
    }

    @Bean
    public CacheManager cacheManager(Caffeine caffeine) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(caffeine);
        caffeineCacheManager.registerCustomCache("test", caffeineConfig());
        return caffeineCacheManager;
    }*/


}