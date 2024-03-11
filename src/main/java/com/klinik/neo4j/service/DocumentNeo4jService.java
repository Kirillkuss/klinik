package com.klinik.neo4j.service;

import java.util.*;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;
import com.klinik.neo4j.model.DocumentNeo4j;
import com.klinik.neo4j.repository.DocumentNeo4jRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentNeo4jService {
    
    private final DocumentNeo4jRepository documentNeo4jRepository;

    public DocumentNeo4j findById( String id ){
        return documentNeo4jRepository.findById( id )
                                      .orElseThrow( () -> new NoSuchElementException("Not found Document with id = " + id ));
    }

    public DocumentNeo4j addDocument( DocumentNeo4j document ){
        document.setId( UUID.randomUUID().toString() );
        return documentNeo4jRepository.save( document ) ;
    }

    public List<DocumentNeo4j> getAll(){
        //documentNeo4jRepository.deleteAll();
        return documentNeo4jRepository.findAll();
    }

}
