package com.klinik.redis.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.klinik.redis.model.Session;

@Repository
public interface SessionRepository extends CrudRepository<Session, String> {
    
}
