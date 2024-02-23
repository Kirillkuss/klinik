package com.klinik.cassandra.service;

import org.springframework.stereotype.Service;
import java.util.UUID;
import com.klinik.cassandra.entity.Document;
import com.klinik.cassandra.repository.DocumentRepositoryCassandra;
import java.util.List;
import lombok.RequiredArgsConstructor;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class DocumentServiceCassandra {

    private final DocumentRepositoryCassandra documentRepositoryCassandra;

    public List<Document> getAll(){
        return documentRepositoryCassandra.findAll();
    }

    public Document addDocument( Document document ){
        UUID id = UUID.randomUUID();
        if( documentRepositoryCassandra.findById( id ).isPresent() ) throw new NoSuchElementException( "Документ с таким ид уже существует" );
        document.setId( id );
        return documentRepositoryCassandra.save( document );
    }

    public Document findByIdDocument( UUID idDocument ){
        return documentRepositoryCassandra.findById( idDocument )
                                          .orElseThrow( () -> new NoSuchElementException( "Нет документа с таким ИД" ));
    }

    public void deleteDocument( UUID idDocument ){
        if( documentRepositoryCassandra.findById( idDocument ).isEmpty() ) throw new NoSuchElementException( "Нет документа с таким ИД" );
        documentRepositoryCassandra.deleteById( idDocument );
    }


    

}
