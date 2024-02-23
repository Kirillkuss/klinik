package com.klinik.cassandra.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import com.klinik.cassandra.entity.Document;

@Repository
public interface DocumentRepositoryCassandra extends CassandraRepository<Document, UUID> {
    
}
