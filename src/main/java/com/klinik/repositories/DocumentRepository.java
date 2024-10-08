package com.klinik.repositories;

import com.klinik.entity.Document;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document,Long> {
    @Query( "SELECT u FROM Document u WHERE u.numar = :numar")
    Optional<Document> findByNumar( String numar);
    @Query( "SELECT u FROM Document u WHERE u.snils = :snils")
    Optional<Document> findBySnils( String snils );
    @Query( "SELECT u FROM Document u WHERE u.polis = :polis")
    Optional<Document> findByPolis( String polis );
    @Query("SELECT u FROM Document u WHERE u.numar LIKE :word or u.snils LIKE :word or u.polis LIKE :word")
    List<Document> findByWord( @Param("word") String word );    
}
