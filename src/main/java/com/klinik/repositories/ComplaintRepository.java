package com.klinik.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.klinik.entity.Сomplaint;

@Repository
public interface ComplaintRepository extends JpaRepository<Сomplaint, Long> {
    @Query( "SELECT u from Сomplaint u WHERE u.functional_impairment = :name")
    Optional<Сomplaint> findByName( String name );
    
}
