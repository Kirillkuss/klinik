package com.klinik.neo4j.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
import com.klinik.neo4j.model.PersonNeo4j;

@Repository
public interface PersonNeo4jRepository extends Neo4jRepository<PersonNeo4j, UUID> {
    
}
