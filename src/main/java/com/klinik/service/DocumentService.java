package com.klinik.service;

import com.klinik.entity.Document;
import com.klinik.repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository repository;

    public List<Document> getAllDocuments() throws Exception{
        return repository.findAll();
    }

    @Transactional
    public Document addDocument( Document document ) throws Exception{
        return repository.save( document );
    }
}
