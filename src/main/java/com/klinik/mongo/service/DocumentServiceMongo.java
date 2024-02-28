package com.klinik.mongo.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.klinik.aspect.GlobalOperation;
import com.klinik.mongo.model.Document;
import com.klinik.mongo.repository.DocumentRepositiryMongo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentServiceMongo {
    
    private final DocumentRepositiryMongo documentRepositiryMongo;

    @GlobalOperation(operation = "Document all mongo")
    public List<Document> findAll(){
        return documentRepositiryMongo.findAll();
    }

    @GlobalOperation(operation = "Document id mongo")
    public Document findById( String id ){
        return documentRepositiryMongo.findById( id )
                                      .orElseThrow( () -> new NoSuchElementException("Hasn't document with id = " + id ));
    }

    @GlobalOperation(operation = "Document add mongo")
    public Document addDocument( Document document ){
        document.setId( UUID.randomUUID().toString() );
        return documentRepositiryMongo.save( document );
    }
    
}
