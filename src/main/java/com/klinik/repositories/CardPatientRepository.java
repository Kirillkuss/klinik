package com.klinik.repositories;

import com.klinik.entity.Card_patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardPatientRepository extends JpaRepository<Card_patient, Long> {
}
