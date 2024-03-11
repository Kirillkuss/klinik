package com.klinik.neo4j.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.klinik.neo4j.model.DocumentNeo4j;
import com.klinik.neo4j.model.PersonNeo4j;
import com.klinik.neo4j.repository.PersonNeo4jRepository;
import com.klinik.neo4j.rest.INeo4jDocument;
import com.klinik.neo4j.service.DocumentNeo4jService;
import java.util.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DocumentNeo4jController implements INeo4jDocument {

    private final DocumentNeo4jService documentNeo4jService;
    private final PersonNeo4jRepository personNeo4jRepository;

    @Override
    public ResponseEntity<List<DocumentNeo4j>> getAll() {
        return new ResponseEntity<List<DocumentNeo4j>>( documentNeo4jService.getAll(), HttpStatus.OK );
    }

    @Override
    public ResponseEntity<DocumentNeo4j> findById(String id) {
        return new ResponseEntity<DocumentNeo4j>( documentNeo4jService.findById( id ), HttpStatus.OK );
    }

    @Override
    public ResponseEntity<DocumentNeo4j> addDocument(DocumentNeo4j document) {
        return new ResponseEntity<DocumentNeo4j>( documentNeo4jService.addDocument( document ), HttpStatus.CREATED );
    }

    @Override
    public ResponseEntity<List<PersonNeo4j>> getAllPerson() {
        //personNeo4jRepository.deleteAll();
        return new ResponseEntity<List<PersonNeo4j>>( personNeo4jRepository.findAll(), HttpStatus.OK );
    }

    @Override
    public ResponseEntity<PersonNeo4j> addPerson(PersonNeo4j person) {
        person.setId( UUID.randomUUID());
        person.getDocumentNeo4j().setId( UUID.randomUUID().toString());
        return new ResponseEntity<PersonNeo4j>( personNeo4jRepository.save( person ), HttpStatus.CREATED );
    }
    

}
