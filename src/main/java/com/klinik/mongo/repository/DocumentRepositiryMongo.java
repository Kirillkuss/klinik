package com.klinik.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.klinik.mongo.model.Document;

@Repository
public interface DocumentRepositiryMongo extends MongoRepository<Document, String>{
    
}
