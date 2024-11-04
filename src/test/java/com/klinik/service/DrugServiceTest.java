package com.klinik.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.klinik.entity.Drug;
import com.klinik.entity.DrugTreatment;
import com.klinik.repositories.DrugRepository;
import com.klinik.repositories.DrugTreatmentRepository;
import com.klinik.request.DrugRequest;
import java.util.List;
import java.util.Optional;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import java.util.NoSuchElementException;

@Owner(value = "Barysevich K. A.")
@Epic(value = "Тестирование сервиса DrugService")
@DisplayName( value = "Тестирование сервиса DrugService")
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class DrugServiceTest {
    
    private final String TYPE   = "application/json";
    private final String RESULT = "Результат: ";
    private final String ERROR  = "Ожидаемая ошибка :";

    @Mock private  DrugRepository          drugRepository;
    @Mock private  DrugTreatmentRepository drugTreatmentRepository;

    private DrugService drugService;
    private Drug drug;
    private DrugTreatment drugTreatment; 
    private DrugRequest drugRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        drugService   = new DrugService( drugRepository, drugTreatmentRepository );
        drugTreatment = new DrugTreatment( 1L, "drugTreatment");
        drug          = new Drug( -1L, "drug", drugTreatment );
        drugRequest   = new DrugRequest( "drug", 1L );
        
    }

    @Test
    @DisplayName("Получение спика всех препаратов мед. лечений")
    public void testFindAll(){
        Mockito.when( drugRepository.findAll()).thenReturn( List.of( drug ));
        Mockito.when( drugService.findAll() ).thenReturn( List.of( drug ));
        assertEquals( drugService.findAll(), drugService.findAll());
        Allure.addAttachment(RESULT, TYPE, drugService.findAll().toString());
        Mockito.verify( drugRepository, times( 3 )).findAll();
    }

    @Test
    @DisplayName("Сохранение препарат для  мед. лечения")
    public void testSaveDrug() throws Exception{
        Allure.parameter( "drugRequest", drugRequest );
        Allure.parameter( "drug", drug );
        Mockito.when( drugTreatmentRepository.findById( drugRequest.getIdDrugTreatment() )).thenReturn( Optional.of( drugTreatment ));
        Mockito.when( drugRepository.findByName( drugRequest.getName() )).thenReturn( Optional.empty());
        Mockito.when( drugRepository.save( drug )).thenReturn(  drug );
        Mockito.when( drugService.saveDrug( drugRequest )).thenReturn(  drug );
        assertNotNull( drugService.saveDrug( drugRequest ));
        Allure.addAttachment(RESULT, TYPE, drugService.saveDrug( drugRequest ).toString());
        Mockito.verify( drugRepository, times( 2 )).save( drug );
    }

    @Test
    @DisplayName("Сохранение препаата для мед. лечения - проверка на ошибки")
    public void testSaveDrugError() throws Exception{
        Allure.parameter( "drugRequest", drugRequest );
        Allure.parameter( "drug", drug );
        Mockito.when( drugTreatmentRepository.findById( drugRequest.getIdDrugTreatment() )).thenReturn( Optional.empty() );
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () ->  drugService.saveDrug( drugRequest ));
        assertEquals("Медикаментозное лечение с таким ИД не существует", exception.getMessage());
        Allure.addAttachment( ERROR, TYPE, exception.getMessage() );

        Mockito.when( drugTreatmentRepository.findById( drugRequest.getIdDrugTreatment() )).thenReturn( Optional.of( drugTreatment ));
        Mockito.when( drugRepository.findByName( drugRequest.getName() )).thenReturn( Optional.of( drug ));
        IllegalArgumentException exceptionTwo = assertThrows(IllegalArgumentException.class,
        () ->  drugService.saveDrug( drugRequest ));
        assertEquals("Препарат с такми наименованием уже существует", exceptionTwo.getMessage());
        Allure.addAttachment( ERROR, TYPE, exceptionTwo.getMessage() );
    }

    @ParameterizedTest
    @CsvSource({"-1"})
    @DisplayName("Получение списка препаратов по ид мед лечения")
    public void ratestFindByIdDrugTreatment( Long idDrugTretment ) throws Exception{
        Mockito.when( drugRepository.findByIdDrugTreatment( idDrugTretment )).thenReturn( List.of( drug ));
        Mockito.when(drugService.findByIdDrugTreatment( idDrugTretment )).thenReturn( List.of( drug ));
        assertEquals( drugService.findByIdDrugTreatment( idDrugTretment ), drugService.findByIdDrugTreatment( idDrugTretment ));
        Allure.addAttachment( RESULT, TYPE, drugService.findByIdDrugTreatment( idDrugTretment ).toString());
        Mockito.verify( drugRepository, times(3 )).findByIdDrugTreatment( idDrugTretment );
    }

    @ParameterizedTest
    @CsvSource({"-1"})
    @DisplayName("Получение списка препаратов по ид мед лечения - проверка на ошибку")
    public void testFindByIdDrugTreatmentError( Long idDrugTretment ) throws Exception{
        Mockito.when( drugRepository.findByIdDrugTreatment( idDrugTretment )).thenReturn( List.of());
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
        () ->  drugService.findByIdDrugTreatment( idDrugTretment ));
        assertEquals("По данному запросу ничего не найдено", exception.getMessage());
        Allure.addAttachment( ERROR, TYPE, exception.getMessage() );
    }

}
