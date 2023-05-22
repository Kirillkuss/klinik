package com.klinik.repositories;

import com.klinik.entity.Record_patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordPatientRepository extends JpaRepository<Record_patient,Long> {
}
