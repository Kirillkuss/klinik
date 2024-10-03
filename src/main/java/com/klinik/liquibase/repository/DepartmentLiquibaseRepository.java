package com.klinik.liquibase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.klinik.liquibase.entity.DepartmentLiquibase;

@Repository
public interface DepartmentLiquibaseRepository extends JpaRepository<DepartmentLiquibase, Long> {
    
}
