package com.klinik.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.klinik.entity.Document;
import com.klinik.repositories.DocumentRepository;

@Disabled
@DisplayName( "Класс предназначен для тестирования сервиса DocumentService")
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class DocumentServiceTest {

    @Autowired
    private DocumentRepository repository;

    @Autowired
    private EntityManager em;

    private DocumentService service;

    private Optional<Document> document = null;
    private List<Document> list = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        service = mock( DocumentService.class );
        //service.documentRepository = repository;
    }

    @AfterEach
    public void tearDown() {
    }

    @DisplayName("Получение списка документов")
    @Test
    public void testGetAllDocuments() throws Exception{
        Mockito.when( service.getAllDocuments()).thenCallRealMethod();
        Mockito.when( service.getAllDocuments()).thenReturn( list );
        Mockito.when( service.getAllDocuments()).then(( InvocationOnMock inv ) ->{
            return ( List<Document> ) inv.callRealMethod();
        });
        assertNotNull( service.getAllDocuments());
        Mockito.verify( service, times(1)).getAllDocuments();
    }

    
}
