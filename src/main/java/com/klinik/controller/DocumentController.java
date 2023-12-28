package com.klinik.controller;

import com.klinik.entity.Document;
import com.klinik.excep.MyException;
import com.klinik.rest.IDocument;
import com.klinik.service.DocumentService;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DocumentController implements IDocument{

    private final DocumentService documentService;

    /**
     * for soap 
     */
    @CrossOrigin
    public ResponseEntity<List<Document>> getAllDocuments() throws Exception, MyException{
        return new ResponseEntity<>( documentService.getAllDocuments(), HttpStatus.OK );
    }
    public ResponseEntity<Document> addDocument( Document document ) throws Exception, MyException{
        return new ResponseEntity<>( documentService.addDocument( document ), HttpStatus.CREATED );
    }

    @Override
    public ResponseEntity<List<Document>> findByWord(String word) {
        return new ResponseEntity<>( documentService.findByWord( word ), HttpStatus.OK ); 
    }

    @Override
    public ResponseEntity<List<Document>> getLazyDocument(int page, int size) {
        return new ResponseEntity<>( documentService.getLazyDocuments( page, size ), HttpStatus.OK ); 
    }

}
