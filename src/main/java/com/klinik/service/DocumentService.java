package com.klinik.service;

import com.klinik.entity.Document;
import com.klinik.excep.MyException;
import com.klinik.repositories.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {

    public final DocumentRepository documentRepository;

    public List<Document> getAllDocuments() throws Exception{
        return documentRepository.findAll();
    }

    @Transactional
    public Document addDocument( Document document ) throws Exception{
        if ( documentRepository.findByIdDocument( document.getIdDocument()).isEmpty() == false ) throw new MyException( 409, "Документ с таким ИД документа уже существует, используйте другой ИД");
        if ( documentRepository.findByNumar( document.getNumar()) != null ) throw new MyException( 409, "Документ с таким номером документа уже существует");
        if ( documentRepository.findByPolis( document.getPolis()) != null ) throw new MyException( 409, "Документ с таким полисом уже существует");
        if ( documentRepository.findBySnils( document.getSnils()) != null ) throw new MyException( 409, "Документ с таким СНИЛСом уже существует");
        return documentRepository.save( document );
    }

}
