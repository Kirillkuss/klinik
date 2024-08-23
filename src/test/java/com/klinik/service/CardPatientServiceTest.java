package com.klinik.service;

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
import com.klinik.entity.CardPatient;
import com.klinik.entity.Document;
import com.klinik.entity.Patient;
import com.klinik.entity.TypeComplaint;
import com.klinik.repositories.CardPatientRepository;
import com.klinik.repositories.DocumentRepository;
import com.klinik.repositories.PatientRepository;
import com.klinik.repositories.TypeComplaintRepository;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

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
    private List<TypeComplaint> typeComplaints = new ArrayList<>();

    private final String TYPE   = "application/json";
    private final String RESULT = "Результат: ";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        patient = new Patient();
        TypeComplaint typeComplaint = new TypeComplaint( 1L , RESULT, null );
        cardPatient = new CardPatient(1L,
                                     "Рассеянный склероз",
                                      true,
                                       "Есть аллергия на цитрамон",
                                        "Болен",
                                        List.of( typeComplaint ),
                                        patient );
        cardPatientService = new CardPatientService( entityManager, typeComplaintRepository, cardPatientRepository, patientRepository );
    }

    @Test
    @DisplayName("Сохрание карты пациента")
    public void testSaveCardPatient() throws Exception{
        Long idPatient = 1L;
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
    @DisplayName("Поиска карты по ИД карты")
    public void testFindByIdCard( Long idCard ){
        Mockito.when( cardPatientRepository.findById( idCard )).thenReturn( Optional.of( cardPatient ));
        assertNotNull(cardPatientService.findByIdCard( idCard ));
        assertEquals( cardPatientService.findByIdCard( idCard ), cardPatientService.findByIdCard( idCard ));
        Allure.addAttachment( RESULT, TYPE,  cardPatientService.findByIdCard( idCard ).toString() );
        verify( cardPatientRepository, times(4 )).findById( idCard );
    }
    
}
