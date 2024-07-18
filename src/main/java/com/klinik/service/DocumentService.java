package com.klinik.service;

import com.klinik.entity.Document;
import com.klinik.excep.MyException;
import com.klinik.repositories.DocumentRepository;

import javax.persistence.EntityManager;
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
        if ( documentRepository.findById( document.getIdDocument()).isPresent() ) throw new MyException( 409, "Документ с таким ИД документа уже существует, используйте другой ИД");
        if ( documentRepository.findByNumar( document.getNumar()).isPresent() ) throw new MyException( 409, "Документ с таким номером документа уже существует");
        if ( documentRepository.findByPolis( document.getPolis()).isPresent() ) throw new MyException( 409, "Документ с таким полисом уже существует");
        if ( documentRepository.findBySnils( document.getSnils()).isPresent() ) throw new MyException( 409, "Документ с таким СНИЛСом уже существует");
        log.info( "addDocument" );
        return documentRepository.save( document );
    }

    public List<Document> findByWord( String word ){
        log.info( "findByWord" );
        List<Document> list = documentRepository.findByWord( word );
        if( list.isEmpty() ) throw new NoSuchElementException("По данному запросму ничего не найдено");
        return list;
    }

    public List<Document> getLazyDocuments(int page, int size){
        log.info( "getLazyDocuments  - page >> " + page + " size >> " + size );
        return entityManager.createNativeQuery( "select * from Document", Document.class)
                            .setFirstResult((page - 1) * size)
                            .setMaxResults(size)
                            .getResultList();
    }


}
