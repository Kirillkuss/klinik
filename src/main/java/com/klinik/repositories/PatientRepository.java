package com.klinik.repositories;

import com.klinik.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Modifying
    @Query( "SELECT u FROM Patient u WHERE u.suraname = :word or u.name = :word or u.full_name = :word or u.phone =:word")
    List<Patient> findPatientByWord( String word );
}
