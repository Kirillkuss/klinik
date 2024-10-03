package com.klinik.liquibase.controller;

import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.klinik.liquibase.entity.DepartmentLiquibase;
import com.klinik.liquibase.entity.DoctorLiquibase;
import com.klinik.liquibase.entity.UserKlinik;
import com.klinik.liquibase.repository.DepartmentLiquibaseRepository;
import com.klinik.liquibase.repository.DoctorLiquibaseRepository;
import com.klinik.liquibase.repository.UserKlinikRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "liquibase ", description = "liquibase")
@RestController
@RequestMapping( value = "liquibase")
@RequiredArgsConstructor
public class LiquibaseController {
    
    private final DepartmentLiquibaseRepository departmentLiquibaseRepository;
    private final DoctorLiquibaseRepository doctorLiquibaseRepository;
    private final UserKlinikRepository userKlinikRepository;
    @GetMapping("/departments")
    public List<DepartmentLiquibase> getDepartmentLiquibases(){
        return departmentLiquibaseRepository.findAll();
    }
    @GetMapping("/doctors")
    public List<DoctorLiquibase> gDoctorLiquibases(){
        return doctorLiquibaseRepository.findAll();
    }
    @GetMapping("/users")
    public List<UserKlinik> getUserKliniks(){
        return userKlinikRepository.findAll();
    }
}
