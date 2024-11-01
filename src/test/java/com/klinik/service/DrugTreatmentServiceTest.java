package com.klinik.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.klinik.entity.DrugTreatment;
import com.klinik.repositories.DrugTreatmentRepository;
import java.util.List;
import java.util.Optional;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;

@Disabled
@Owner(value = "Barysevich K. A.")
@Epic(value = "Тестирование сервиса DrugTreatmentService")
@DisplayName( value = "Тестирование сервиса DrugTreatmentService")
public class DrugTreatmentServiceTest {

    @Mock private DrugTreatmentRepository drugTreatmentRepository;
          
          private DrugTreatmentService drugTreatmentService;
          private DrugTreatment drugTreatment;

          private final String TYPE   = "application/json";
          private final String RESULT = "Результат: ";
          private final String ERROR  = "Ожидаемая ошибка :";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        drugTreatment        = new DrugTreatment( 1L, "drugTreatment");
        drugTreatmentService = new DrugTreatmentService( drugTreatmentRepository );

    }

    @Test
    @DisplayName("Получение списка мед лечений")
    public void testGetAll(){
        Mockito.when( drugTreatmentRepository.findAll() ).thenReturn( List.of( drugTreatment ));
        Mockito.when( drugTreatmentService.getAll() ).thenReturn( List.of( drugTreatment ));
        assertEquals(drugTreatmentService.getAll(), drugTreatmentService.getAll());
        assertEquals( drugTreatmentRepository.findAll(), drugTreatmentService.getAll());
        Allure.addAttachment( RESULT, TYPE, drugTreatmentRepository.findAll().toString());
        Mockito.verify( drugTreatmentRepository, times(5)).findAll();
    }

    @Test
    @DisplayName("Добалвение мед лечения")
    public void testAddDrugTreatment() throws Exception{
        Allure.parameter( "drugTreatment", drugTreatment );
        Mockito.when( drugTreatmentRepository.findById( drugTreatment.getIdDrugTreatment())).thenReturn( Optional.empty() );
        Mockito.when( drugTreatmentRepository.findByName( drugTreatment.getName() )).thenReturn( Optional.empty() );
        Mockito.when( drugTreatmentRepository.save( drugTreatment )).thenReturn( drugTreatment );
        Mockito.when( drugTreatmentService.addDrugTreatment( drugTreatment )).thenReturn( drugTreatment );
        assertNotNull(drugTreatmentService.addDrugTreatment( drugTreatment ));
        Allure.addAttachment( RESULT, TYPE, drugTreatmentService.addDrugTreatment( drugTreatment ).toString());
        Mockito.verify( drugTreatmentRepository, times( 2 )).save( drugTreatment );
    }

    @Test
    @DisplayName("Добалвение мед лечения - проверка на ошибки")
    public void testAddDrugTreatmentError() throws Exception{
        Allure.parameter( "drugTreatment", drugTreatment );
        Mockito.when( drugTreatmentRepository.findById( drugTreatment.getIdDrugTreatment())).thenReturn( Optional.of( drugTreatment ));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () ->  drugTreatmentService.addDrugTreatment( drugTreatment ));
        assertEquals("Медикаментозное лечение с таким ИД уже существует", exception.getMessage());
        Allure.addAttachment( ERROR, TYPE, exception.getMessage() );

        Mockito.when( drugTreatmentRepository.findById( drugTreatment.getIdDrugTreatment())).thenReturn( Optional.empty() );
        Mockito.when( drugTreatmentRepository.findByName( drugTreatment.getName() )).thenReturn( Optional.of( drugTreatment ));
        IllegalArgumentException exceptionTwo = assertThrows(IllegalArgumentException.class,
        () ->  drugTreatmentService.addDrugTreatment( drugTreatment ));
        assertEquals("Медикаментозное лечение с таким наименование уже существует", exceptionTwo.getMessage());
        Allure.addAttachment( ERROR, TYPE, exceptionTwo.getMessage() );
    }
    
}
