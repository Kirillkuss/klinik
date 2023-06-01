package com.klinik.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.klinik.entity.Drug;

@Repository
public interface DrugRepository extends JpaRepository< Drug, Long>{

    @Query( "SELECT d FROM Drug d WHERE d.name = :word")
    public Drug findByName( String word );
    
}
