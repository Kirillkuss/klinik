package com.klinik.redis.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.klinik.redis.model.Doctor;

@Repository
public interface DoctorRepositoryRedis extends CrudRepository<Doctor, String> {
    
}
