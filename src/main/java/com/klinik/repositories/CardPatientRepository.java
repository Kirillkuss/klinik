package com.klinik.repositories;

import com.klinik.entity.Card_patient;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CardPatientRepository extends JpaRepository<Card_patient, Long> {

    @Query("SELECT u FROM Card_patient u where u.patient.id_patient = :id")
    Optional<Card_patient> findByPatientId( Long id );
}
