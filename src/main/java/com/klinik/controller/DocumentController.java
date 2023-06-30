package com.klinik.controller;

import com.klinik.entity.Document;
import com.klinik.excep.MyException;
import com.klinik.rest.IDocument;
import com.klinik.service.DocumentService;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DocumentController implements IDocument{

    private final DocumentService documentService;
    public ResponseEntity<List<Document>> getAllDocuments() throws Exception, MyException{
        return new ResponseEntity<>( documentService.getAllDocuments(), HttpStatus.OK );
    }

    public ResponseEntity<Document> addDocument( Document document ) throws Exception, MyException{
        if ( documentService.findById( document.getId_document()) != null ) throw new MyException( 409, "Документ с таким ИД документа уже существует, используйте другой ИД");
        if ( documentService.findByNumar( document.getNumar()) != null )    throw new MyException( 409, "Документ с таким номером документа уже существует");
        if ( documentService.findByPolis( document.getPolis()) != null )    throw new MyException( 409, "Документ с таким полисом уже существует");
        if ( documentService.findBySnils( document.getSnils()) != null )    throw new MyException( 409, "Документ с таким СНИЛСом уже существует");
        return new ResponseEntity<>( documentService.addDocument( document ), HttpStatus.CREATED );
    }

}
