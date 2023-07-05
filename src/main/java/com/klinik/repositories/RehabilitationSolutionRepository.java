package com.klinik.repositories;

import com.klinik.entity.RehabilitationSolution;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RehabilitationSolutionRepository extends JpaRepository<RehabilitationSolution, Long> {

    @Query( "SELECT u FROM RehabilitationSolution u WHERE u.name = :name")
    Optional<RehabilitationSolution> findByName( String name );
}
