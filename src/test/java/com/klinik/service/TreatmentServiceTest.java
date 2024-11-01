package com.klinik.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.klinik.entity.CardPatient;
import com.klinik.entity.Doctor;
import com.klinik.entity.Drug;
import com.klinik.entity.DrugTreatment;
import com.klinik.entity.Patient;
import com.klinik.entity.RehabilitationSolution;
import com.klinik.entity.Treatment;
import com.klinik.entity.TypeComplaint;
import com.klinik.repositories.CardPatientRepository;
import com.klinik.repositories.DoctorRerository;
import com.klinik.repositories.DrugRepository;
import com.klinik.repositories.RehabilitationSolutionRepository;
import com.klinik.repositories.TreatmentRepository;
import com.klinik.request.RequestTreatment;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;

@Disabled
@Owner( value = "Barysevich K. A." )
@Epic( value = "Тестирование сервиса TreatmentService" )
@DisplayName( value = "Тестирование сервиса TreatmentService" )
public class TreatmentServiceTest {

    @Mock     private TreatmentRepository              treatmentRepository;
    @Mock     private DrugRepository                   drugRepository;
    @Mock     private RehabilitationSolutionRepository rehabilitationSolutionRepository;
    @Mock     private CardPatientRepository            cardPatientRepository;
    @Mock     private DoctorRerository                 doctorRerository;

    private final String TYPE   = "application/json";
    private final String RESULT = "Результат: ";
    private final String ERROR  = "Ожидаемая ошибка :";

    private TreatmentService       treatmentService;
    private RehabilitationSolution rehabilitationSolution;
    private Doctor                 doctor;
    private Drug                   drug;
    private Treatment              treatment;
    private CardPatient            cardPatient;
    private RequestTreatment       requestTreatment;
    private LocalDateTime          from;    
    private LocalDateTime          to;    

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        from                   = LocalDateTime.now().minusDays( 10);
        to                     = LocalDateTime.now();
        doctor                 = new Doctor( 1L, "surname", "name", "fullname" );
        drug                   = new Drug( 1L, "name", new DrugTreatment());
        rehabilitationSolution = new RehabilitationSolution( 1L, "name", "surveyPlan");
        treatment              = new Treatment( -1L, from, to, drug, rehabilitationSolution, 1L, doctor);
        cardPatient            = new CardPatient( 1L, "test", true, "test", "test", List.of( new TypeComplaint()), new Patient());
        requestTreatment       = new RequestTreatment( from, to, 1L ,1L ,1L, 1L );
        treatmentService       = new TreatmentService( treatmentRepository, drugRepository, rehabilitationSolutionRepository, cardPatientRepository, doctorRerository );
    }
    
    @Test
    @DisplayName("Поиск всех лечений")
    public void testFindAll(){
        Mockito.when( treatmentRepository.findAll() ).thenReturn( List.of( treatment ));
        when( treatmentService.findAll() ).thenReturn(  List.of( treatment ));
        assertEquals( List.of( treatment), treatmentService.findAll() );
        Allure.addAttachment( RESULT, TYPE, treatmentService.findAll().toString() );
        verify( treatmentRepository, times( 2 )).findAll();
    }

    @Test
    @DisplayName("Сохранение лечения")
    public void testAddTreatmentSuccess() throws Exception{
        Allure.parameter( "requestTreatment", requestTreatment );
        Allure.parameter( "treatment", treatment );
        when( drugRepository.findById( requestTreatment.getIdDrug() )).thenReturn( Optional.of( drug ));
        when( doctorRerository.findById( requestTreatment.getIdDoctor() )).thenReturn( Optional.of( doctor ));
        when( rehabilitationSolutionRepository.findById( requestTreatment.getIdRehabilitationSolution() )).thenReturn( Optional.of(rehabilitationSolution ));
        when( cardPatientRepository.findById( requestTreatment.getIdCardPatient() )).thenReturn( Optional.of( cardPatient ));
        when( treatmentRepository.save( treatment )).thenReturn( treatment );
        when( treatmentService.addTreatment( requestTreatment )).thenReturn( treatment );
        assertNotNull( treatmentService.addTreatment( requestTreatment ));
        assertEquals( treatment, treatmentService.addTreatment( requestTreatment ));
        Allure.addAttachment( RESULT, TYPE, treatmentService.addTreatment( requestTreatment ).toString());
        verify( treatmentRepository, times( 3)).save( treatment );
    }

    @Test
    @DisplayName("Сохранение лечения - проверка на ошибки")
    public void testAddTreatmentError() throws Exception{
        Allure.parameter( "requestTreatment", requestTreatment );
        Allure.parameter( "treatment", treatment );
        when( drugRepository.findById( requestTreatment.getIdDrug() )).thenReturn( Optional.empty() );
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () ->  treatmentService.addTreatment( requestTreatment ));
        assertEquals("Указано неверное значение медикаментозного лечения, укажите другой", exception.getMessage());
        Allure.addAttachment( ERROR, TYPE, exception.getMessage() );

        when( drugRepository.findById( requestTreatment.getIdDrug() )).thenReturn( Optional.of( drug ));
        when( rehabilitationSolutionRepository.findById( requestTreatment.getIdRehabilitationSolution() )).thenReturn( Optional.of(rehabilitationSolution ));
        when( cardPatientRepository.findById( requestTreatment.getIdCardPatient() )).thenReturn( Optional.of( cardPatient ));
        when( doctorRerository.findById( requestTreatment.getIdDoctor() )).thenReturn( Optional.empty());
        IllegalArgumentException exceptionTwo = assertThrows(IllegalArgumentException.class,
        () ->  treatmentService.addTreatment( requestTreatment ));
        assertEquals("Указано неверное значение ид доктора, укажите другой", exceptionTwo.getMessage());
        Allure.addAttachment( ERROR, TYPE, exceptionTwo.getMessage() );

        when( drugRepository.findById( requestTreatment.getIdDrug() )).thenReturn( Optional.of( drug ));
        when( doctorRerository.findById( requestTreatment.getIdDoctor() )).thenReturn( Optional.of( doctor ));
        when( rehabilitationSolutionRepository.findById( requestTreatment.getIdRehabilitationSolution() )).thenReturn( Optional.empty() );
        IllegalArgumentException exceptionThree = assertThrows(IllegalArgumentException.class,
        () ->  treatmentService.addTreatment( requestTreatment ));
        assertEquals("Указано неверное значение реабилитационного лечения, укажите другой", exceptionThree.getMessage());
        Allure.addAttachment( ERROR, TYPE, exceptionThree.getMessage() );

        when( drugRepository.findById( requestTreatment.getIdDrug() )).thenReturn( Optional.of( drug ));
        when( doctorRerository.findById( requestTreatment.getIdDoctor() )).thenReturn( Optional.of( doctor ));
        when( rehabilitationSolutionRepository.findById( requestTreatment.getIdRehabilitationSolution() )).thenReturn( Optional.of(rehabilitationSolution ));
        when( cardPatientRepository.findById( requestTreatment.getIdCardPatient() )).thenReturn( Optional.empty() );
        IllegalArgumentException exceptionFour = assertThrows(IllegalArgumentException.class,
        () ->  treatmentService.addTreatment( requestTreatment ));
        assertEquals("Указано неверное значение карты пациента, укажите другой", exceptionFour.getMessage());
        Allure.addAttachment( ERROR, TYPE, exceptionFour.getMessage() );
    }

    @Test
    @DisplayName("Получение списка лечений пациентов по параметрам")
    public void testFindByParamIdCardAndDateStartSuccess() throws Exception{
        Long idCardPatient = 1L;
        Allure.parameter( "idCardPatient", idCardPatient );
        Allure.parameter( "from", from );
        Allure.parameter( "to", to );
        when( treatmentRepository.findByParamIdCardAndDateStart(idCardPatient, from, to )).thenReturn( List.of( treatment ));
        when( treatmentService.findByParamIdCardAndDateStart( idCardPatient, from, to )).thenReturn( List.of( treatment ));
        assertNotNull( treatmentService.findByParamIdCardAndDateStart( idCardPatient, from, to ));
        assertEquals(List.of( treatment ), treatmentService.findByParamIdCardAndDateStart( idCardPatient, from, to ));
        Allure.addAttachment( RESULT, TYPE, treatmentService.findByParamIdCardAndDateStart( idCardPatient, from, to ).toString());
        verify( treatmentRepository, times(3 )).findByParamIdCardAndDateStart(idCardPatient, from, to );
    }

    @Test
    @DisplayName("Получение списка лечений пациентов по параметрам - проверка на ошибку")
    public void testFindByParamIdCardAndDateStartError() throws Exception{
        Long idCardPatient = 1L;
        Allure.parameter( "idCardPatient", idCardPatient );
        Allure.parameter( "from", from );
        Allure.parameter( "to", to );
        when( treatmentRepository.findByParamIdCardAndDateStart(idCardPatient, from, to )).thenReturn( List.of());
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
        () ->  treatmentService.findByParamIdCardAndDateStart(idCardPatient, from, to ));
        assertEquals("По данному запросу ничего не найдено", exception.getMessage());
        Allure.addAttachment( ERROR, TYPE, exception.getMessage() );
    }

    @Test
    @DisplayName("Получение списка лечений пациентов по параметрам")
    public void testFindByParamIdCardAndIdRhSuccess() throws Exception{
        Long idCardPatient = 1L;
        Long idRh          = 1L;
        Allure.parameter( "idCardPatient", idCardPatient );
        Allure.parameter( "idRh", idRh );
        when( treatmentRepository.findByParamIdCardAndIdRh( idCardPatient, idRh )).thenReturn( List.of( treatment ));
        when( treatmentService.findByParamIdCardAndIdRh( idCardPatient, idRh )).thenReturn( List.of( treatment ));
        assertNotNull( treatmentService.findByParamIdCardAndIdRh( idCardPatient, idRh ));
        assertEquals( treatmentService.findByParamIdCardAndIdRh( idCardPatient, idRh ), List.of( treatment ));
        Allure.addAttachment( RESULT, TYPE, treatmentService.findByParamIdCardAndIdRh( idCardPatient, idRh ).toString());
        verify( treatmentRepository, times(3)).findByParamIdCardAndIdRh( idCardPatient, idRh );
    }

    @Test
    @DisplayName("Получение списка лечений пациентов по параметрам - проверка на ошибки")
    public void testFindByParamIdCardAndIdRhError() throws Exception{
        Long idCardPatient = 1L;
        Long idRh          = 1L;
        Allure.parameter( "idCardPatient", idCardPatient );
        Allure.parameter( "idRh", idRh );
        when( treatmentRepository.findByParamIdCardAndIdRh( idCardPatient, idRh )).thenReturn( List.of());
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
        () ->  treatmentService.findByParamIdCardAndIdRh( idCardPatient, idRh ));
        assertEquals("По данному запросу ничего не найдено", exception.getMessage());
        Allure.addAttachment( ERROR, TYPE, exception.getMessage() );
    }
    
}
