package com.klinik.controller.model;

import com.klinik.entity.Document;
import com.klinik.excep.MyException;
import com.klinik.repositories.DocumentRepository;
import com.klinik.rest.model.IDocument;
import com.klinik.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DocumentController implements IDocument{

    private final DocumentService documentService;
    private final DocumentRepository documentRepository;
    private final KafkaTemplate<String,Document> kafkaTemplate;

    private void sendDocument( Document document ){
        kafkaTemplate.send("klinikFirst", document );
    }
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
        documentService.findByWord( word ).stream().forEach( document -> sendDocument(document));
        return new ResponseEntity<>( documentService.findByWord( word ), HttpStatus.OK ); 
    }

    @Override
    public ResponseEntity<List<Document>> getLazyDocument(int page, int size) {
        documentService.getLazyDocuments( page, size ).stream().forEach( document -> sendDocument(document));
        return new ResponseEntity<>( documentService.getLazyDocuments( page, size ), HttpStatus.OK ); 
    }
    @Override
    public ResponseEntity<Long> getCountDocument() {
        return new ResponseEntity<>( documentRepository.count(), HttpStatus.OK ); 
    }

}
