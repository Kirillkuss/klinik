package com.klinik.repositories;

import com.klinik.entity.DrugTreatment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DrugTreatmentRepository extends JpaRepository<DrugTreatment, Long> {

    @Query( "SELECT u FROM DrugTreatment u WHERE u.name = :name")
    Optional<DrugTreatment> findByName( String name );
}
