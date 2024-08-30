package com.klinik.repositories;

import com.klinik.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Modifying
    @Query( "SELECT u FROM Patient u WHERE u.surname = :word or u.name = :word or u.fullName = :word or u.phone =:word")
    List<Patient> findPatientByWord( String word );

    @Query( "SELECT u FROM Patient u WHERE u.document.idDocument = :id")
    Optional<Patient> findPatientByIdDocument( Long id );

    @Query ( "SELECT u FROM Patient u WHERE u.phone = :phoneNumber")
    Optional<Patient> findByPhone( String phoneNumber );

    @Query ("SELECT u FROM Patient u where u.idPatient = :id")
    Optional<Patient> findByIdPatinet( Long id );

}   
