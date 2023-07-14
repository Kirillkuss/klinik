package com.klinik.service;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import com.klinik.entity.RecordPatient;
import com.klinik.repositories.CardPatientRepository;
import com.klinik.repositories.DoctorRerository;
import com.klinik.repositories.RecordPatientRepository;
import static org.mockito.Mockito.mock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Этот класс предназнаен для тестирования сервиса RecordPatientService")
public class RecordPatientServiceTest {

    @Autowired
    private RecordPatientService recordPatientService;

    @MockBean
    private static RecordPatientRepository recordPatientRepository;
    @MockBean
    private static DoctorRerository        doctorRerository;
    @MockBean
    private static CardPatientRepository   cardPatientRepository;

    private final List<RecordPatient> listRecordPatients = new ArrayList<>();

    @TestConfiguration
    static class RecordPatientServiceTestConfiguration {
 
        @Bean
        public RecordPatientService recordPatientService() {
            return new RecordPatientService(recordPatientRepository, doctorRerository, cardPatientRepository);
        }
    }

    @BeforeEach
    public void setUp() {
        recordPatientService = mock ( RecordPatientService.class  );
    }

    @Test
    @DisplayName( "Получение всех запицесей к врачу ")
    public void testFindAll() throws Exception {
        Mockito.when( recordPatientService.findAll() ).thenReturn( listRecordPatients );
        Mockito.when( recordPatientService.findAll() ).then(( InvocationOnMock inv ) ->{
            return ( List<RecordPatient> ) inv.callRealMethod();
        });
    }

    @Test
    @DisplayName( "Поиск записей пациента по параметрам")
    public void testFindByParam() throws Exception{
        Long id = 1L;
        LocalDateTime date = LocalDateTime.now();
        Mockito.when( recordPatientService.findByParam( id, date, date )).thenReturn( listRecordPatients );
        Mockito.when( recordPatientService.findByParam( id, date, date )).then(( InvocationOnMock inv ) ->{
            return ( List<RecordPatient> ) inv.callRealMethod();
        });
    }

    @Test
    @DisplayName("Добавить запись")
    public void testSaveRecordPatient() throws Exception{
        RecordPatient recordPatient = new RecordPatient();
        Long idDoctor = 1L;
        Long idCardPatient = 1L;
        Mockito.when( recordPatientService.saveRecordPatient( recordPatient, idDoctor, idCardPatient )).thenReturn( recordPatient );
        Mockito.when( recordPatientService.saveRecordPatient( recordPatient, idDoctor, idCardPatient )).then(( InvocationOnMock inv ) ->{
            return ( RecordPatient ) inv.callRealMethod();
        }); 

    }


}

