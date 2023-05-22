package com.klinik.repositories;

import com.klinik.entity.Rehabilitation_solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RehabilitationSolutionRepository extends JpaRepository<Rehabilitation_solution, Long> {

    @Query( "SELECT u FROM Rehabilitation_solution u WHERE u.name = :name")
    Rehabilitation_solution findByName( String name );
}
