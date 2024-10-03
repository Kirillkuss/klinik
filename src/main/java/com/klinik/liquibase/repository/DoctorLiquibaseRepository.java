package com.klinik.liquibase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.klinik.liquibase.entity.DoctorLiquibase;

@Repository
public interface DoctorLiquibaseRepository extends JpaRepository<DoctorLiquibase, Long>{
    
}
