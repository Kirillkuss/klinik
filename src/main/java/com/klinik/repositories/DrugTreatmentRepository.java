package com.klinik.repositories;

import com.klinik.entity.Drug_treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrugTreatmentRepository extends JpaRepository<Drug_treatment, Long> {
}
