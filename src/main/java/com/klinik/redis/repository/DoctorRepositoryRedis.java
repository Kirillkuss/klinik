package com.klinik.redis.repository;

import org.springframework.data.repository.CrudRepository;
import com.klinik.redis.model.Doctor;


public interface DoctorRepositoryRedis extends CrudRepository<Doctor, String> {
    
}
