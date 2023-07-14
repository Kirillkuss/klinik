package com.klinik.service;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.klinik.entity.Document;
import com.klinik.repositories.DocumentRepository;

@DisplayName( "Класс предназначен для тестирования сервиса DocumentService")
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)

public class DocumentServiceTest {

    @MockBean 
    private DocumentRepository documentRepository;

    @InjectMocks
    private DocumentService service ;

    private List<Document> list = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        service = new DocumentService(documentRepository); 
    }

    @DisplayName("Получение списка документов")
    @Test
    public void testGetAllDocuments() throws Exception{
        Mockito.when( service.getAllDocuments()).thenReturn( list );
        Mockito.when( service.getAllDocuments()).then(( InvocationOnMock inv ) ->{
            return ( List<Document> ) inv.callRealMethod();
        });
       }

    @DisplayName("Получение списка документов")
    @Test
    public void testSave() throws Exception{
        Document document = new Document();
        Mockito.when( service.addDocument( document )).thenReturn( document );
        Mockito.when( service.addDocument( document )).then(( InvocationOnMock inv ) ->{
            return ( Document ) inv.callRealMethod();
        });
       }   

    
}
