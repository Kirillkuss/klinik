package com.klinik.kafka.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.klinik.kafka.message.Animal;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
    
}
