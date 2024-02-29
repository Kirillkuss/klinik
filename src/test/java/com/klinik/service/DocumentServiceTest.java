package com.klinik.service;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.EntityManager;
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
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;

@Owner(value = "Barysevich K. A.")
@Epic(value = "Тестирование класса DocumentService")
@DisplayName( "Класс предназначен для тестирования сервиса DocumentService")
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class DocumentServiceTest {

    @MockBean 
    private DocumentRepository documentRepository;

    @MockBean 
    private EntityManager entityManager;

    @InjectMocks
    private DocumentService service ;

    private List<Document> list = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        service = new DocumentService(documentRepository, entityManager); 
    }

    @Feature("Метод getAllDocuments - Получение списка документов")
    @DisplayName("Получение списка документов")
    @Test
    public void testGetAllDocuments() throws Exception{
        Mockito.when( service.getAllDocuments()).thenReturn( list );
        Mockito.when( service.getAllDocuments()).then(( InvocationOnMock inv ) ->{
            return ( List<Document> ) inv.callRealMethod();
        });
       }

    @Feature("Метод addDocument - Получение списка документов")
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
