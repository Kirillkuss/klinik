package com.klinik.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.klinik.entity.Complaint;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    @Query( "SELECT u from Complaint u WHERE u.functionalImpairment = :name")
    Optional<Complaint> findByName( String name );
    
}
