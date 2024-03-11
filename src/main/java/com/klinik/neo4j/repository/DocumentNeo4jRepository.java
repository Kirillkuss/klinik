package com.klinik.neo4j.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import com.klinik.neo4j.model.DocumentNeo4j;

@Repository
public interface DocumentNeo4jRepository extends Neo4jRepository<DocumentNeo4j, String> {
    
}
