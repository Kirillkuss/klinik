package com.klinik.repositories;

import com.klinik.entity.Document;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document,Long> {
    @Query( "SELECT u FROM Document u WHERE u.numar = :numar")
    Optional<Document> findByNumar( String numar);
    @Query( "SELECT u FROM Document u WHERE u.snils = :snils")
    Optional<Document> findBySnils( String snils );
    @Query( "SELECT u FROM Document u WHERE u.polis = :polis")
    Optional<Document> findByPolis( String polis );    
}
