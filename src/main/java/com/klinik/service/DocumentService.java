package com.klinik.service;

import com.klinik.entity.Document;
import com.klinik.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

@Service
public class DocumentService {

    @PersistenceContext
    EntityManager em;

    @Autowired
    private DocumentRepository repository;

    public List<Document> getAllDocuments() throws Exception{
        return repository.findAll();
    }

    @Transactional
    public Document addDocument( Document document ) throws Exception{
        return repository.save( document );
    }

    public Document findById( Long id ) throws Exception{
         return ( Document ) em.createQuery("select e from Document e where e.id = ?1")
                              .setParameter(1 , id )
                              .getResultList()
                              .stream().findFirst().orElse( null );
    }
}
