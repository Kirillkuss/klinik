package com.klinik.liquibase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.klinik.liquibase.entity.UserKlinik;

@Repository
public interface UserKlinikRepository extends JpaRepository<UserKlinik, Long>{
    
}
