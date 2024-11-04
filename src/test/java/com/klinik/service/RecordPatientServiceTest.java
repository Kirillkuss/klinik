package com.klinik.service;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.klinik.entity.CardPatient;
import com.klinik.entity.Doctor;
import com.klinik.entity.RecordPatient;
import com.klinik.repositories.CardPatientRepository;
import com.klinik.repositories.DoctorRerository;
import com.klinik.repositories.RecordPatientRepository;
import com.klinik.request.RequestRecordPatient;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import java.util.List;
import java.util.Optional;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Owner(value = "Barysevich K. A.")
@Epic(value = "Тестирование сервиса RecordPatientService")
@DisplayName( value = "Тестирование сервиса RecordPatientService")
public class RecordPatientServiceTest {

    @Mock private RecordPatientRepository recordPatientRepository;
    @Mock private DoctorRerository doctorRepository;
    @Mock private CardPatientRepository cardPatientRepository;

    private RecordPatientService recordPatientService;

    private final String TYPE   = "application/json";
    private final String RESULT = "Результат: ";
    private final String ERROR  = "Ожидаемая ошибка :";

    private RequestRecordPatient requestRecordPatient;
    private RecordPatient recordPatient;
    private Doctor doctor;
    private CardPatient cardPatient;

    @BeforeEach
    public void setup() {
        LocalDateTime from = LocalDateTime.now().minusMonths(1);
        LocalDateTime to = LocalDateTime.now();
        MockitoAnnotations.openMocks( this );
        cardPatient          = new CardPatient( 1L, "diagnos", true, "note", "test", null, null);
        requestRecordPatient = new RequestRecordPatient( from, to, 234L, 1L, 1L);
        doctor               = new Doctor(1L, "surname", "name", "fullname" );
        recordPatient        = new RecordPatient();
        recordPatientService = new RecordPatientService( recordPatientRepository, doctorRepository, cardPatientRepository );
        recordPatient.setDateRecord( from );
        recordPatient.setDateAppointment( to );
        recordPatient.setDoctor( doctor );
        recordPatient.setCardPatientId(1L);
        recordPatient.setNumberRoom(234L);
        
    }
    
    @Test
    @DisplayName("Получение всех записей")
    public void testFindAll(){
        Mockito.when( recordPatientRepository.findAll() ).thenReturn( List.of( recordPatient ));
        Mockito.when( recordPatientService.findAll() ).thenReturn( List.of( recordPatient ));
        assertEquals( recordPatientService.findAll(), recordPatientService.findAll());
        Allure.addAttachment( RESULT, TYPE, recordPatientService.findAll().toString());
        Mockito.verify( recordPatientRepository, times( 3)).findAll();
    }

    @Test
    @DisplayName("Сохраненеи записи пациента к врачу")
    public void testSaveRecordPatientSuccess() throws Exception {
        Allure.parameter("requestRecordPatient", requestRecordPatient );
        Mockito.when( doctorRepository.findById( requestRecordPatient.getIdDoctor() )).thenReturn( Optional.of( doctor ));
        Mockito.when( cardPatientRepository.findById( requestRecordPatient.getIdCardPatient() )).thenReturn( Optional.of( cardPatient ));
        Mockito.when( recordPatientRepository.save( recordPatient )).thenReturn( recordPatient );
        Mockito.when( recordPatientService.saveRecordPatient( requestRecordPatient )).thenReturn( recordPatient );
        assertNotNull(recordPatientService.saveRecordPatient( requestRecordPatient ));
        assertEquals( recordPatientService.saveRecordPatient( requestRecordPatient ), recordPatientService.saveRecordPatient( requestRecordPatient ));
        Allure.addAttachment (RESULT, TYPE, recordPatientService.saveRecordPatient( requestRecordPatient ).toString());
        Mockito.verify( recordPatientRepository, times( 4)).save( recordPatient );
    }

    @Test
    @DisplayName("Сохраненеи записи пациента к врачу")
    public void testSaveRecordPatientError() throws Exception {
        Allure.parameter("requestRecordPatient", requestRecordPatient );
        Mockito.when( doctorRepository.findById( requestRecordPatient.getIdDoctor() )).thenReturn( Optional.empty());
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
        () ->  recordPatientService.saveRecordPatient( requestRecordPatient ));
        assertEquals("Указан неверный идентификатор доктора", exception.getMessage());
        Allure.addAttachment( ERROR, TYPE, exception.getMessage() );

        Mockito.when( doctorRepository.findById( requestRecordPatient.getIdDoctor() )).thenReturn( Optional.of( doctor ));
        Mockito.when( cardPatientRepository.findById( requestRecordPatient.getIdCardPatient() )).thenReturn( Optional.empty());
        NoSuchElementException exceptionSecond = assertThrows(NoSuchElementException.class,
        () ->  recordPatientService.saveRecordPatient( requestRecordPatient ));
        assertEquals("Указан неверный идентификатор карты пациента", exceptionSecond.getMessage());
        Allure.addAttachment( ERROR, TYPE, exceptionSecond.getMessage() );

        requestRecordPatient.setDateAppointment( LocalDateTime.now().minusMonths(2));
        Allure.parameter("requestRecordPatient for test 3", requestRecordPatient );
        IllegalArgumentException exceptionThree = assertThrows(IllegalArgumentException.class,
        () ->  recordPatientService.saveRecordPatient( requestRecordPatient ));
        assertEquals("Дата приема не может быть раньше даты записи", exceptionThree.getMessage());
        Allure.addAttachment( ERROR, TYPE, exceptionThree.getMessage() );



    }

    @Test
    @DisplayName("Полученеи списка записей по параметрам")
    public void testFindByParam() throws Exception{
        Long idCardPatient = 1L;
        LocalDateTime dateFrom = LocalDateTime.now().minusMonths(1);
        LocalDateTime dateTo = LocalDateTime.now();
        Allure.parameter( "idCardPatient", idCardPatient );
        Allure.parameter( "dateFrom", dateFrom );
        Allure.parameter( "dateTo", dateTo );
        Mockito.when( recordPatientRepository.findByParamTwo( idCardPatient, dateFrom, dateTo )).thenReturn( List.of( recordPatient ));
        Mockito.when( recordPatientService.findByParam( idCardPatient, dateFrom, dateTo )).thenReturn( List.of( recordPatient ));
        assertEquals( recordPatientService.findByParam( idCardPatient, dateFrom, dateTo ), recordPatientService.findByParam( idCardPatient, dateFrom, dateTo ));
        Allure.addAttachment( RESULT, TYPE, recordPatientService.findByParam( idCardPatient, dateFrom, dateTo ).toString());
        Mockito.verify( recordPatientRepository, times( 3)).findByParamTwo( idCardPatient, dateFrom, dateTo );
    }
}

