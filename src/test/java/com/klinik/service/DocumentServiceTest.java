package com.klinik.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.klinik.entity.Document;
import com.klinik.repositories.DocumentRepository;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import java.util.Optional;
import java.util.NoSuchElementException;

@Owner(value = "Barysevich K. A.")
@Epic(value = "Тестирование сервиса DocumentService")
@DisplayName( value = "Тестирование сервиса DocumentService")
public class DocumentServiceTest {

    @Mock        private Query              query;
    @Mock        private DocumentRepository documentRepository;
    @Mock        private EntityManager      entityManager;
                 private DocumentService    documentService;

    private final String TYPE   = "application/json";
    private final String RESULT = "Результат: ";

    private static Document document;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        document = new Document(1L, "passport", "DM", "123456789", "821-033-843-46", "8850 9911 3793 7050");
        documentService = new DocumentService( documentRepository, entityManager );
    }
    
    @Test
    @DisplayName("Получение списка документов")
    public void testGetAllDocuments() throws Exception{
        Mockito.when( documentRepository.findAll() ).thenReturn( List.of( document ));
        assertNotNull( documentService.getAllDocuments() );
        assertEquals( documentService.getAllDocuments(), documentService.getAllDocuments() );
        Allure.addAttachment( RESULT, TYPE, documentService.getAllDocuments().toString() );
        verify( documentRepository, times(4 )).findAll();
    }

    @Test
    @DisplayName( "Сохранить документ" )
    public void testAddDocument() throws Exception{
        Allure.parameter( "document", document );
        Mockito.when( documentRepository.findById( document.getIdDocument() )).thenReturn( Optional.empty() );
        Mockito.when( documentRepository.findByNumar( document.getNumar() )).thenReturn( Optional.empty() );
        Mockito.when( documentRepository.findByPolis( document.getPolis() )).thenReturn( Optional.empty() );
        Mockito.when( documentRepository.findBySnils( document.getSnils() )).thenReturn( Optional.empty() );
        Mockito.when( documentService.addDocument( document )).thenReturn( document );
        assertNotNull( documentService.addDocument( document ));
        assertEquals( documentService.addDocument( document ), documentService.addDocument( document ));
        Allure.addAttachment( RESULT, TYPE, documentService.addDocument( document ).toString() );
        verify( documentRepository, times(4 )).save( document );
    }

    @Test
    @DisplayName( "Сохранить документ - Error " )
    public void testAddDocumentError() throws Exception{
        String ERROR = "Ожидаемая ошибка :";
        Allure.parameter( "document", document );
        /**Mockito.when( documentRepository.findById( document.getIdDocument() )).thenReturn( Optional.empty() );
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> documentService.addDocument( document ));
        assertEquals("Документ с таким ИД документа уже существует, используйте другой ИД", exception.getMessage());
        Allure.addAttachment( ERROR, TYPE,exception.getMessage() );*/
        
        Mockito.when( documentRepository.findById( document.getIdDocument() )).thenReturn( Optional.empty() );
        Mockito.when( documentRepository.findByNumar( document.getNumar() )).thenReturn( Optional.of( document ) );
        IllegalArgumentException exceptionTwo = assertThrows(IllegalArgumentException.class, () -> documentService.addDocument( document ));
        assertEquals("Документ с таким номером документа уже существует", exceptionTwo.getMessage());
        Allure.addAttachment( ERROR, TYPE,exceptionTwo.getMessage() );

        Mockito.when( documentRepository.findById( document.getIdDocument() )).thenReturn( Optional.empty() );
        Mockito.when( documentRepository.findByNumar( document.getNumar() )).thenReturn( Optional.empty() );
        Mockito.when( documentRepository.findByPolis( document.getPolis() )).thenReturn( Optional.of( document ));
        IllegalArgumentException exceptionThree = assertThrows(IllegalArgumentException.class, () -> documentService.addDocument( document ));
        assertEquals("Документ с таким полисом уже существует", exceptionThree.getMessage());
        Allure.addAttachment( ERROR, TYPE,exceptionThree.getMessage() );

        Mockito.when( documentRepository.findById( document.getIdDocument() )).thenReturn( Optional.empty() );
        Mockito.when( documentRepository.findByNumar( document.getNumar() )).thenReturn( Optional.empty() );
        Mockito.when( documentRepository.findByPolis( document.getPolis() )).thenReturn(Optional.empty());
        Mockito.when( documentRepository.findBySnils( document.getSnils() )).thenReturn( Optional.of( document ));
        IllegalArgumentException exceptionFour = assertThrows(IllegalArgumentException.class, () -> documentService.addDocument( document ));
        assertEquals("Документ с таким СНИЛСом уже существует", exceptionFour.getMessage());
        Allure.addAttachment( ERROR, TYPE,exceptionFour.getMessage() );
    }

    @ParameterizedTest
    @CsvSource({"248469703"})
    @DisplayName("Поиск по слову")
    public void testGetFindByWord( String WORD ){
        Mockito.when( documentRepository.findByWord( WORD )).thenReturn( List.of( document ));
        Allure.addAttachment( RESULT, TYPE, documentRepository.findByWord( WORD ).toString() );
       // assertNotNull( documentService.findByWord( WORD ));
        /**assertEquals( documentService.findByWord( WORD ), documentService.findByWord( WORD ));
        Allure.addAttachment( RESULT, TYPE, documentService.findByWord( WORD ).toString() );
        verify( documentRepository, times(4 )).findByWord( WORD );*/
    }

    @ParameterizedTest
    @CsvSource({"12lcmc"})
    @DisplayName("Поиск по слову - Error")
    public void testGetFindByWordError( String WORD ){
        String ERROR = "Ожидаемая ошибка :";
        Mockito.when( documentRepository.findByWord( WORD )).thenReturn( List.of() );
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> documentService.findByWord( WORD ));
        assertEquals("По данному запросму ничего не найдено", exception.getMessage());
        Allure.addAttachment( ERROR, TYPE,exception.getMessage() );
    }


    @ParameterizedTest
    @CsvSource({"1,3", "1,10"})
    @DisplayName("Получение списка документов (LAZY)")
    public void testGetLazyDocuments( int PAGE, int SIZE ) throws Exception{
        Mockito.when( entityManager.createNativeQuery("select * from Document", Document.class)).thenReturn(query);
        Mockito.when( query.setFirstResult((PAGE - 1) * SIZE)).thenReturn(query);
        Mockito.when( query.setMaxResults(SIZE)).thenReturn(query);
        Mockito.when( query.getResultList()).thenReturn( List.of( document ));
        Mockito.when( documentService.getLazyDocuments( PAGE, SIZE )).thenReturn( List.of( document ));
        assertNotNull( documentService.getLazyDocuments( PAGE, SIZE ));
        assertEquals( documentService.getLazyDocuments( PAGE, SIZE ), documentService.getLazyDocuments( PAGE, SIZE ));
        Allure.addAttachment( RESULT, TYPE, documentService.getLazyDocuments( PAGE, SIZE ).toString() );
        verify( query, times(4 )).getResultList( ) ;
    }
}
