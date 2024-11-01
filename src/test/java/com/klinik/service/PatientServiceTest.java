package com.klinik.service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.klinik.entity.Document;
import com.klinik.entity.Gender;
import com.klinik.entity.Patient;
import com.klinik.repositories.DocumentRepository;
import com.klinik.repositories.PatientRepository;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import java.util.List;
import java.util.Optional;
import java.util.NoSuchElementException;

@Disabled
@Owner(value = "Barysevich K. A.")
@Epic(value = "Тестирование сервиса PatientService")
@DisplayName( value = "Тестирование сервиса PatientService")
public class PatientServiceTest {

    @Mock private PatientRepository  patientRepository;
    @Mock private DocumentRepository documentRepository;
    @Mock private EntityManager      entityManager;
    @Mock private Query             query;

    private PatientService patientService;
    
    private final String TYPE   = "application/json";
    private final String RESULT = "Результат: ";
    private final String ERROR  = "Ожидаемая ошибка :";

    private Document document;
    private Patient patient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        document = new Document( 1L, "typeDocument", "seria", "numar", "snils", "polis");
        patient = new Patient( 1L, "surname", "name", "fullname", Gender.MAN, "phone", "address", document );
        patientService = new PatientService( patientRepository, documentRepository, entityManager );

    }

    @Test
    @DisplayName("Получение списка пациантов")
    public void testGetAllPatients(){
        Mockito.when( patientRepository.findAll() ).thenReturn( List.of( patient ));
        Mockito.when( patientService.getAllPatients() ).thenReturn(List.of( patient ));
        assertEquals( patientService.getAllPatients(), patientService.getAllPatients());
        Allure.addAttachment( RESULT, TYPE,  patientService.getAllPatients().toString());
        Mockito.verify( patientRepository, times( 3 )).findAll();
    }

    @Test
    @DisplayName("Добавление пациента")
    public void testAddPatient() throws Exception{
        Long idDocument = 1L;
        Allure.parameter( "idDocument", idDocument );
        Allure.parameter( "patient", patient );
        Mockito.when( patientRepository.findByPhone( patient.getPhone() )).thenReturn( Optional.empty());
        Mockito.when( patientRepository.findPatientByIdDocument( idDocument )).thenReturn(  Optional.empty());
        Mockito.when( patientRepository.findById( patient.getIdPatient() )).thenReturn(  Optional.empty());
        Mockito.when( documentRepository.findById( idDocument )).thenReturn( Optional.of( document ));
        Mockito.when( patientRepository.save( patient )).thenReturn( patient );
        Mockito.when( patientService.addPatient( patient, idDocument )).thenReturn( patient );
        assertNotNull( patientService.addPatient( patient, idDocument ));
        Allure.addAttachment( RESULT, TYPE, patientService.addPatient( patient, idDocument ).toString());
        Mockito.verify( patientRepository, times( 2 )).save( patient );
    }

    @Test
    @DisplayName("Добавление пациента - проверка на ошибки ")
    public void testAddPatientError() throws Exception{
        Long idDocument = 1L;
        Allure.parameter( "idDocument", idDocument );
        Allure.parameter( "patient", patient );
        Mockito.when( patientRepository.findByPhone( patient.getPhone() )).thenReturn( Optional.of( patient ));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () ->  patientService.addPatient( patient, idDocument ));
        assertEquals("Пользователь с таким номером телефона уже существует, укажите другой", exception.getMessage());
        Allure.addAttachment( ERROR, TYPE, exception.getMessage() );

        Mockito.when( patientRepository.findByPhone( patient.getPhone() )).thenReturn( Optional.empty());
        Mockito.when( patientRepository.findPatientByIdDocument( idDocument )).thenReturn(  Optional.of( patient ));
        IllegalArgumentException exceptionTwo = assertThrows(IllegalArgumentException.class,
        () ->  patientService.addPatient( patient, idDocument ));
        assertEquals("Неверное значение ИД документа, попробуйте другой", exceptionTwo.getMessage());
        Allure.addAttachment( ERROR, TYPE, exceptionTwo.getMessage() );

       /**  Mockito.when( patientRepository.findByPhone( patient.getPhone() )).thenReturn( Optional.empty());
        Mockito.when( patientRepository.findPatientByIdDocument( idDocument )).thenReturn(  Optional.empty());
        Mockito.when( patientRepository.findById( patient.getIdPatient() )).thenReturn(  Optional.of( patient ));
        IllegalArgumentException exceptionThree = assertThrows(IllegalArgumentException.class,
        () ->  patientService.addPatient( patient, idDocument ));
        assertEquals("Пользователь с таким ИД уже существует", exceptionThree.getMessage());
        Allure.addAttachment( ERROR, TYPE, exceptionThree.getMessage() );*/

        
        Mockito.when( patientRepository.findByPhone( patient.getPhone() )).thenReturn( Optional.empty());
        Mockito.when( patientRepository.findPatientByIdDocument( idDocument )).thenReturn(  Optional.empty());
        Mockito.when( patientRepository.findById( patient.getIdPatient() )).thenReturn(  Optional.empty());
        Mockito.when( documentRepository.findById( idDocument )).thenReturn( Optional.empty());
        IllegalArgumentException exceptionFour = assertThrows(IllegalArgumentException.class,
        () ->  patientService.addPatient( patient, idDocument ));
        assertEquals("Документ с таким ИД не существует", exceptionFour.getMessage());
        Allure.addAttachment( ERROR, TYPE, exceptionFour.getMessage() );
    }

   // @ParameterizedTest
    @CsvSource({"TEST"})
    @DisplayName("Поиск пациента по фио и номеру тел ")
    public void testFindByWord( String word ) throws Exception{
        String pattern = "%" + word + "%";
        Mockito.when( patientRepository.findPatientByWord( pattern )).thenReturn( List.of( patient ));
        Mockito.when( patientService.findByWord( pattern )).thenReturn( List.of( patient ));
        //assertEquals( patientService.findByWord( word ), patientService.findByWord( word ));
       // Allure.addAttachment( RESULT, TYPE, patientService.findByWord( word ).toString());
        //Mockito.verify( patientRepository, times( 3)).findPatientByWord( word );
    }

    @ParameterizedTest
    @CsvSource({"numar"})
    @DisplayName("Поиск пациента по фио и номеру тел - проверка на ошибку")
    public void testFindByWordError( String word ) throws Exception{
        Mockito.when( patientRepository.findPatientByWord( word )).thenReturn( List.of());
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
        () ->  patientService.findByWord( word ));
        assertEquals("По данному запросу ничего не найдено", exception.getMessage());
        Allure.addAttachment( ERROR, TYPE, exception.getMessage() );
    }

    @ParameterizedTest
    @CsvSource({"1, 4"})
    @DisplayName("Получение списка польхователей")
    public void testGetLazyLoad( int page, int size ){
        Mockito.when( entityManager.createNativeQuery("select * from Patient", Patient.class)).thenReturn(query);
        Mockito.when( query.setFirstResult((page - 1) * size)).thenReturn(query);
        Mockito.when( query.setMaxResults(size)).thenReturn(query);
        Mockito.when( query.getResultList()).thenReturn( List.of( patient ));
        Mockito.when( patientService.getLazyLoad( page, size )).thenReturn( List.of( patient ));
        assertNotNull( patientService.getLazyLoad( page, size ));
        assertEquals( patientService.getLazyLoad( page, size ), patientService.getLazyLoad( page, size ));
        Allure.addAttachment( RESULT, TYPE, patientService.getLazyLoad( page, size ).toString());
        Mockito.verify( query, times( 4 )).getResultList();

    }

}
