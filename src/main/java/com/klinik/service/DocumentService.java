package com.klinik.service;

import com.klinik.aspect.GlobalOperation;
import com.klinik.entity.Document;
import com.klinik.excep.MyException;
import com.klinik.repositories.DocumentRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class DocumentService {

    public final DocumentRepository documentRepository;

    private final EntityManager entityManager;
    
    @GlobalOperation(operation = "getAllDocuments")
    public List<Document> getAllDocuments(){
        return documentRepository.findAll();
    }

    @Transactional
    @GlobalOperation(operation = "addDocument")
    public Document addDocument( Document document ) throws Exception{
        if ( documentRepository.findById( document.getIdDocument()).isPresent() ) throw new MyException( 409, "Документ с таким ИД документа уже существует, используйте другой ИД");
        if ( documentRepository.findByNumar( document.getNumar()).isPresent() ) throw new MyException( 409, "Документ с таким номером документа уже существует");
        if ( documentRepository.findByPolis( document.getPolis()).isPresent() ) throw new MyException( 409, "Документ с таким полисом уже существует");
        if ( documentRepository.findBySnils( document.getSnils()).isPresent() ) throw new MyException( 409, "Документ с таким СНИЛСом уже существует");
        return documentRepository.save( document );
    }

    @GlobalOperation(operation = "findByWordDocuments")
    public List<Document> findByWord( String word ){
        List<Document> list = documentRepository.findByWord( word );
        if( list.isEmpty() ) throw new NoSuchElementException("По данному запросму ничего не найдено");
        return list;
    }

    @GlobalOperation(operation = "getLazyDocuments")
    public List<Document> getLazyDocuments(int page, int size){
        return entityManager.createNativeQuery( "select * from Document", Document.class)
                            .setFirstResult((page - 1) * size)
                            .setMaxResults(size)
                            .getResultList();
    }


}
