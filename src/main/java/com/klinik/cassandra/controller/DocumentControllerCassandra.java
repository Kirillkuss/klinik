package com.klinik.cassandra.controller;

import org.springframework.web.bind.annotation.RestController;
import com.klinik.cassandra.entity.Document;
import com.klinik.cassandra.rest.RestControllerDocumentCassandra;
import com.klinik.cassandra.service.DocumentServiceCassandra;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class DocumentControllerCassandra implements RestControllerDocumentCassandra{

    @Autowired
    private DocumentServiceCassandra documentService;

    @Override
    public ResponseEntity<List<Document>> allDocument() {
        return new ResponseEntity<List<Document>>( documentService.getAll(), HttpStatus.OK );
    }

    @Override
    public ResponseEntity<Document> saveDocument(Document document) {
        return new ResponseEntity<Document>( documentService.addDocument( document ), HttpStatus.OK );
    }

    @Override
    public ResponseEntity<Document> findById( UUID id ) {
        return new ResponseEntity<Document>( documentService.findByIdDocument( id ), HttpStatus.OK );

    }

    @SuppressWarnings("rawtypes")
    @Override
    public ResponseEntity deleteDocument(UUID id) {
        documentService.deleteDocument( id );
        return new ResponseEntity( HttpStatus.NO_CONTENT);
    }
    
}
