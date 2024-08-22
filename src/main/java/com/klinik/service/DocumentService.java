package com.klinik.service;

import com.klinik.entity.Document;
import com.klinik.repositories.DocumentRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentService {

    public final DocumentRepository documentRepository;
    private final EntityManager entityManager;
    
    public List<Document> getAllDocuments(){
        log.info( "getAllDocuments" );
        return documentRepository.findAll();
    }
    @Transactional
    public Document addDocument( Document document ) throws Exception{
        checkDocument( document );
        log.info( "addDocument" );
        return documentRepository.save( document );
    }

    private void checkDocument( Document document ){
        if ( documentRepository.findById( document.getIdDocument()).isPresent() ) throw new IllegalArgumentException(  "Документ с таким ИД документа уже существует, используйте другой ИД");
        if ( documentRepository.findByNumar( document.getNumar()).isPresent() ) throw new IllegalArgumentException(  "Документ с таким номером документа уже существует");
        if ( documentRepository.findByPolis( document.getPolis()).isPresent() ) throw new IllegalArgumentException(  "Документ с таким полисом уже существует");
        if ( documentRepository.findBySnils( document.getSnils()).isPresent() ) throw new IllegalArgumentException(  "Документ с таким СНИЛСом уже существует");
    }

    public List<Document> findByWord( String word ){
        log.info( "findByWord" );
        List<Document> list = documentRepository.findByWord( word );
        if( list.isEmpty() ) throw new NoSuchElementException("По данному запросму ничего не найдено");
        return list;
    }

    @SuppressWarnings("unchecked")
    public List<Document> getLazyDocuments(int page, int size){
        log.info( "getLazyDocuments  - page >> " + page + " size >> " + size );
        return entityManager.createNativeQuery( "select * from Document", Document.class)
                            .setFirstResult((page - 1) * size)
                            .setMaxResults(size)
                            .getResultList();
    }


}
