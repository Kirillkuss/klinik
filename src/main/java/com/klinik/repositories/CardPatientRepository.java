package com.klinik.repositories;

import com.klinik.entity.CardPatient;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CardPatientRepository extends JpaRepository<CardPatient, Long> {
    @Query("SELECT u FROM CardPatient u where u.patient.idPatient = :id")
    Optional<CardPatient> findByPatientId( Long id );
    @Query( "SELECT cp FROM CardPatient cp where cp.patient.document.numar = :parametr or cp.patient.document.snils = :parametr or cp.patient.document.polis = :parametr")
    Optional<CardPatient> findByNPS( String parametr);
}
