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
    public DocumentRepository documentRepository;

    public List<Document> getAllDocuments() throws Exception{
        return documentRepository.findAll();
    }

    @Transactional
    public Document addDocument( Document document ) throws Exception{
        return documentRepository.save( document );
    }

    public Document findById( Long id ) throws Exception{
         return documentRepository.findByIdDocument( id );
    }

    public Document findByNumar( String numar ) throws Exception{
        return documentRepository.findByNumar( numar );
    }

    public Document findBySnils( String snils ) throws Exception{
        return documentRepository.findBySnils( snils );
    }

    public Document findByPolis( String polis ) throws Exception{
        return documentRepository.findByPolis( polis );
    }
}
