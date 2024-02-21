package com.klinik.redis.repository;

import org.springframework.data.repository.CrudRepository;
import com.klinik.redis.model.Cache;

public interface CacheRepository extends CrudRepository<Cache, String> {
    
    
}
