package com.klinik.service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;

import com.klinik.entity.CardPatient;
import com.klinik.entity.Doctor;
import com.klinik.entity.Patient;
import com.klinik.entity.TypeComplaint;
import com.klinik.repositories.CardPatientRepository;
import com.klinik.repositories.PatientRepository;
import com.klinik.repositories.TypeComplaintRepository;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Owner(value = "Barysevich K. A.")
@Epic(value = "Тестирование сервиса CardPatientService")
@DisplayName( value = "Тестирование сервиса CardPatientService")
public class CardPatientServiceTest {

    @Mock private  TypeComplaintRepository typeComplaintRepository;
    @Mock private  CardPatientRepository   cardPatientRepository;
    @Mock private  PatientRepository       patientRepository;
    @Mock private  EntityManager           entityManager;
    @Mock private  Query                   query;
   
          private CardPatientService       cardPatientService;

    private CardPatient cardPatient;
    private Patient patient;
    private TypeComplaint typeComplaint;
    private List<CardPatient> lisrCardPatients = new ArrayList<>();

    private final String TYPE   = "application/json";
    private final String RESULT = "Результат: ";
    private final String ERROR  = "Ожидаемая ошибка :";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        patient = new Patient();
        typeComplaint = new TypeComplaint( 1L , RESULT, null );
        cardPatient = new CardPatient(1L,
                                     "Рассеянный склероз",
                                      true,
                                       "Есть аллергия на цитрамон",
                                        "Болен",
                                        List.of( typeComplaint ),
                                        patient );
        cardPatientService = new CardPatientService( entityManager, typeComplaintRepository, cardPatientRepository, patientRepository );
        Mockito.when(entityManager.createNativeQuery( any(String.class))).thenReturn(query);
    }

    @ParameterizedTest
    @CsvSource({"1"})
    @DisplayName("Сохрание карты пациента")
    public void testSaveCardPatient( Long idPatient ) throws Exception{
        Allure.parameter( "cardPatient", cardPatient);
        Mockito.when( cardPatientRepository.findByPatientId( idPatient )).thenReturn( Optional.empty());
        Mockito.when( cardPatientRepository.findById( cardPatient.getIdCardPatient() )).thenReturn( Optional.empty());
        Mockito.when( patientRepository.findById( idPatient )).thenReturn( Optional.of( patient ));
        Mockito.when( cardPatientService.saveCardPatient( cardPatient, idPatient )).thenReturn( cardPatient );
        assertNotNull( cardPatientService.saveCardPatient( cardPatient, idPatient ));
        assertEquals( cardPatientService.saveCardPatient( cardPatient, idPatient ), cardPatientService.saveCardPatient( cardPatient, idPatient ));
        Allure.addAttachment( RESULT, TYPE,  cardPatientService.saveCardPatient( cardPatient, idPatient ).toString() );
        verify( cardPatientRepository, times(4 )).save(cardPatient);
    }

    @ParameterizedTest
    @CsvSource({"1"})
    @DisplayName("Сохрание карты пациента")
    public void testtestSaveCardPatientError( Long idPatient){
        Allure.parameter( "cardPatient", cardPatient);
        Mockito.when( cardPatientRepository.findByPatientId( idPatient )).thenReturn( Optional.of( cardPatient ));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () ->  cardPatientService.saveCardPatient( cardPatient, idPatient ));
        assertEquals("Карта пациента с таким ИД пациента уже существует", exception.getMessage());
        Allure.addAttachment( ERROR, TYPE, exception.getMessage() );

       /** Mockito.when( cardPatientRepository.findByPatientId( idPatient )).thenReturn( Optional.empty());
        Mockito.when( cardPatientRepository.findById( cardPatient.getIdCardPatient() )).thenReturn( Optional.of( cardPatient ));
        IllegalArgumentException exceptionTwo = assertThrows(IllegalArgumentException.class,
        () ->  cardPatientService.saveCardPatient( cardPatient, idPatient ));
        assertEquals("Карта с таким ИД уже существует", exceptionTwo.getMessage());
        Allure.addAttachment( ERROR, TYPE, exceptionTwo.getMessage() ); */

        Mockito.when( cardPatientRepository.findByPatientId( idPatient )).thenReturn( Optional.empty());
        Mockito.when( cardPatientRepository.findById( cardPatient.getIdCardPatient() )).thenReturn( Optional.empty());
        Mockito.when( patientRepository.findById( idPatient )).thenReturn( Optional.empty());

        IllegalArgumentException exceptionThree = assertThrows(IllegalArgumentException.class,
        () ->  cardPatientService.saveCardPatient( cardPatient, idPatient ));
        assertEquals("Пациента с таким ИД не существует", exceptionThree.getMessage());
        Allure.addAttachment( ERROR, TYPE, exceptionThree.getMessage() );

    }

    @ParameterizedTest
    @CsvSource({"1"})
    @DisplayName("Поиск карты по ид пациента")
    public void testFindByPatientId( Long idPatient ){
        Mockito.when(cardPatientRepository.findByPatientId(idPatient)).thenReturn(Optional.of(cardPatient));
        assertNotNull( cardPatientService.findByPatientId(idPatient));
        assertEquals(cardPatientService.findByPatientId(idPatient), cardPatientService.findByPatientId(idPatient));
        Allure.addAttachment( RESULT, TYPE,  cardPatientService.findByPatientId(idPatient).toString() );
        verify( cardPatientRepository, times(4 )).findByPatientId( idPatient );
    }

    @ParameterizedTest
    @CsvSource({"1"})
    @DisplayName("Поиск карты по ид пациента - проверка на ошибку")
    public void testFindByPatientIdError( Long idPatient ){
        Allure.parameter( "idPatient", idPatient );
        Mockito.when(cardPatientRepository.findByPatientId(idPatient)).thenReturn( Optional.empty() );
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
        () ->  cardPatientService.findByPatientId(idPatient));
        assertEquals("Карты с таким ИД пациента не существует", exception.getMessage());
        Allure.addAttachment( ERROR, TYPE, exception.getMessage() );
    }

    @ParameterizedTest
    @CsvSource({"1"})
    @DisplayName("Поиска карты по ИД карты")
    public void testFindByIdCard( Long idCard ){
        Mockito.when( cardPatientRepository.findById( idCard )).thenReturn( Optional.of( cardPatient ));
        assertNotNull(cardPatientService.findByIdCard( idCard ));
        assertEquals( cardPatientService.findByIdCard( idCard ), cardPatientService.findByIdCard( idCard ));
        Allure.addAttachment( RESULT, TYPE,  cardPatientService.findByIdCard( idCard ).toString() );
        verify( cardPatientRepository, times(4 )).findById( idCard );
    }

    @ParameterizedTest
    @CsvSource({"1"})
    @DisplayName("Поиска карты по ИД карты - проверка на ошибку")
    public void testFindByIdCardError( Long idCard ){
        Allure.parameter( "idCard", idCard );
        Mockito.when( cardPatientRepository.findById( idCard )).thenReturn( Optional.empty() );
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () ->  cardPatientService.findByIdCard( idCard ));
        assertEquals("Карты с таким ИД карты не существует", exception.getMessage());
        Allure.addAttachment( ERROR, TYPE,exception.getMessage() );
    }

    @ParameterizedTest
    @CsvSource({"1, 1"})
    @DisplayName("Добавление в карту под жалобы")
    public void testAddCardPatientComplaint( Long idCard, Long idComplaint ) throws Exception{
        Mockito.when( cardPatientRepository.findById( idCard )).thenReturn( Optional.of( cardPatient ));
        Mockito.when( typeComplaintRepository.findById( idComplaint )).thenReturn( Optional.of( typeComplaint ));
        Mockito.when( cardPatientRepository.findByIdCardAndIdComplaint( idCard, idComplaint )).thenReturn( Optional.empty());
        Mockito.when( query.setParameter(1, idCard)).thenReturn(query);
        Mockito.when( query.setParameter(2, idComplaint)).thenReturn(query);
        cardPatientService.addCardPatientComplaint(idCard, idComplaint);
        verify(query).setParameter(1, idCard);
        verify(query).setParameter(2, idComplaint);
        verify(query).executeUpdate();
    }

    
    @ParameterizedTest
    @CsvSource({"1, 1"})
    @DisplayName("Добавление в карту под жалобы - проверка на ошибки")
    public void testAddCardPatientComplaintCheckaddCardPatientComplaint( Long idCard, Long idComplaint ) throws Exception{
        Mockito.when( cardPatientRepository.findById( idCard )).thenReturn( Optional.empty() );
        Mockito.when( cardPatientRepository.findByIdCardAndIdComplaint( idCard, idComplaint )).thenReturn( Optional.empty());
        IllegalArgumentException  exception = assertThrows(IllegalArgumentException .class,
        () ->  cardPatientService.addCardPatientComplaint(idCard, idComplaint));
        assertEquals("Карта с таким ИД не существует", exception.getMessage());
        Allure.addAttachment( ERROR, TYPE, exception.getMessage() );

        Mockito.when( cardPatientRepository.findById( idCard )).thenReturn( Optional.of( cardPatient ));
        Mockito.when( typeComplaintRepository.findById( idComplaint )).thenReturn( Optional.empty() );
        IllegalArgumentException  exceptionTwo = assertThrows(IllegalArgumentException .class,
        () ->  cardPatientService.addCardPatientComplaint(idCard, idComplaint));
        assertEquals("Под жалобы с таким ИД не существует", exceptionTwo.getMessage());
        Allure.addAttachment( ERROR, TYPE, exceptionTwo.getMessage() );

        Mockito.when( cardPatientRepository.findById( idCard )).thenReturn( Optional.of( cardPatient ));
        Mockito.when( typeComplaintRepository.findById( idComplaint )).thenReturn( Optional.of( typeComplaint ));
        Mockito.when( cardPatientRepository.findByIdCardAndIdComplaint( idCard, idComplaint )).thenReturn( Optional.of( cardPatient ));
        IllegalArgumentException  exceptionThree = assertThrows(IllegalArgumentException .class,
        () ->  cardPatientService.addCardPatientComplaint(idCard, idComplaint));
        assertEquals("Под жалоба с таким ИД уже добавлена в карту пацинета", exceptionThree.getMessage());
        Allure.addAttachment( ERROR, TYPE, exceptionThree.getMessage() );
    }

    //@ParameterizedTest
    @CsvSource({"741723664"})
    @DisplayName("Поиск карты пациента по документу пациента ( полис/снилс/номер )")
    public void testFindByNPS( String parametr ) throws Exception{
        Mockito.when( cardPatientRepository.findByNPS( parametr )).thenReturn( lisrCardPatients );
        assertNotNull( cardPatientService.findByNPS( parametr ));
        assertEquals( cardPatientService.findByNPS( parametr ), cardPatientService.findByNPS( parametr ));
        Allure.addAttachment( RESULT, TYPE,  cardPatientService.findByNPS( parametr ).toString() );
        verify( cardPatientRepository, times(4 )).findByNPS( parametr );
    }


    @ParameterizedTest
    @CsvSource({"123456789"})
    @DisplayName( "Поиск карты пациента по документу пациента ( полис/снилс/номер ) - проверка на ошибку" )
    public void testFindByNPSError( String parametr ) throws Exception{
        Mockito.when( cardPatientRepository.findByNPS( parametr )).thenReturn( List.of());
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
        () ->  cardPatientService.findByNPS( parametr ));
        Allure.addAttachment( ERROR, TYPE, exception.getMessage() );
    }

    
}
