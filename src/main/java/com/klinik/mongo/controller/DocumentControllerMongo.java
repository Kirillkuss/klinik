package com.klinik.mongo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.klinik.mongo.model.Document;
import com.klinik.mongo.rest.RestDocumentMongo;
import com.klinik.mongo.service.DocumentServiceMongo;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DocumentControllerMongo implements RestDocumentMongo {

    private final DocumentServiceMongo documentServiceMongo;

    @Override
    public ResponseEntity<List<Document>> getDocuments(){
        return new ResponseEntity<List<Document>>( documentServiceMongo.findAll(), HttpStatus.OK );
    }

    @Override
    public ResponseEntity<Document> getDocument( String id ){
        return new ResponseEntity<Document>( documentServiceMongo.findById( id ), HttpStatus.OK );    
    }

    @Override
    public ResponseEntity<Document> saveDocument(Document document) {
        return new ResponseEntity<Document>( documentServiceMongo.addDocument( document ), HttpStatus.OK );
    }
    
}
