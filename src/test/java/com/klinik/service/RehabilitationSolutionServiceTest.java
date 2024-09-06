package com.klinik.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.klinik.entity.RehabilitationSolution;
import com.klinik.repositories.RehabilitationSolutionRepository;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Owner(value = "Barysevich K. A.")
@Epic(value = "Тестирование сервиса PatientService")
@DisplayName( value = "Тестирование сервиса PatientService")
public class RehabilitationSolutionServiceTest {

    @Mock private RehabilitationSolutionRepository rehabilitationSolutionRepository;

    private RehabilitationSolutionService rehabilitationSolutionService;
    
    private final String TYPE   = "application/json";
    private final String RESULT = "Результат: ";
    private final String ERROR  = "Ожидаемая ошибка :";

    private RehabilitationSolution rehabilitationSolution;

    @BeforeEach
    public void setUp() {
        rehabilitationSolution = new RehabilitationSolution();
        rehabilitationSolution.setIdRehabilitationSolution( -1L );
        rehabilitationSolution.setName( "name");
        rehabilitationSolution.setSurveyPlan("surveyPlan");
        MockitoAnnotations.openMocks(this);
        rehabilitationSolutionService = new RehabilitationSolutionService( rehabilitationSolutionRepository );
    }

    @Test
    @DisplayName("Получение списка реб лечений")
     public void testGetAll(){
        Mockito.when( rehabilitationSolutionRepository.findAll() ).thenReturn( List.of( rehabilitationSolution ));
        Mockito.when( rehabilitationSolutionService.getAll()).thenReturn( List.of( rehabilitationSolution ));
        assertEquals( rehabilitationSolutionService.getAll(), List.of( rehabilitationSolution ));
        Allure.addAttachment( RESULT, TYPE, rehabilitationSolutionService.getAll().toString());
        Mockito.verify( rehabilitationSolutionRepository, times( 2 )).findAll();
    }

    @ParameterizedTest
    @CsvSource({"name"})
    @DisplayName("Поиск по наименованию")
    public void testFindByName( String name ){
        Mockito.when( rehabilitationSolutionRepository.findByName( name )).thenReturn(Optional.of( rehabilitationSolution ));
        assertEquals( rehabilitationSolutionService.findByName( name ), rehabilitationSolution);
        Allure.addAttachment( RESULT, TYPE, rehabilitationSolutionService.findByName( name ).toString());
        Mockito.verify( rehabilitationSolutionRepository, times( 2 )).findByName( name );
    }

    @ParameterizedTest
    @CsvSource({"name"})
    @DisplayName("Поиск по наименованию - проверка на ошибку")
    public void testFindByNameError( String name ){
        Mockito.when( rehabilitationSolutionRepository.findByName( name )).thenReturn(Optional.empty());
        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
        () ->  rehabilitationSolutionService.findByName( name ));
        assertEquals("Ребилитационное лечение c таким наименованием не существует", exception.getMessage());
        Allure.addAttachment( ERROR, TYPE, exception.getMessage() );
    }

    @Test
    @DisplayName("Сохранение реб лечения")
    public void testSaveRehabilitationSolution() throws Exception{
        Allure.parameter( "rehabilitationSolution", rehabilitationSolution );
        Mockito.when( rehabilitationSolutionRepository.findByName( rehabilitationSolution.getName() )).thenReturn( Optional.empty());
        Mockito.when( rehabilitationSolutionRepository.findById( rehabilitationSolution.getIdRehabilitationSolution() )).thenReturn( Optional.empty());
        Mockito.when( rehabilitationSolutionRepository.save( rehabilitationSolution )).thenReturn( rehabilitationSolution );
        Mockito.when( rehabilitationSolutionService.saveRehabilitationSolution( rehabilitationSolution )).thenReturn( rehabilitationSolution );
        assertNotNull( rehabilitationSolutionService.saveRehabilitationSolution( rehabilitationSolution ));
        Allure.addAttachment( RESULT, TYPE, rehabilitationSolutionService.saveRehabilitationSolution( rehabilitationSolution ).toString());
        Mockito.verify( rehabilitationSolutionRepository, times(2 )).save( rehabilitationSolution );
    }

    @Test
    @DisplayName("Сохранение реб лечения - ошибки")
    public void testSaveRehabilitationSolutionError() throws Exception{
        Allure.parameter( "rehabilitationSolution", rehabilitationSolution );
        Mockito.when( rehabilitationSolutionRepository.findByName( rehabilitationSolution.getName() )).thenReturn( Optional.of( rehabilitationSolution));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () ->  rehabilitationSolutionService.saveRehabilitationSolution( rehabilitationSolution ));
        assertEquals("Ребилитационное лечение с таким наименованием уже существует", exception.getMessage());
        Allure.addAttachment( ERROR, TYPE, exception.getMessage() );

        Mockito.when( rehabilitationSolutionRepository.findByName( rehabilitationSolution.getName() )).thenReturn( Optional.empty());
        Mockito.when( rehabilitationSolutionRepository.findById( rehabilitationSolution.getIdRehabilitationSolution() )).thenReturn( Optional.of( rehabilitationSolution ));
        IllegalArgumentException exceptionSecond = assertThrows(IllegalArgumentException.class,
        () ->  rehabilitationSolutionService.saveRehabilitationSolution( rehabilitationSolution ));
        assertEquals("Ребилитационное лечение с таким ИД уже существует", exceptionSecond.getMessage());
        Allure.addAttachment( ERROR, TYPE, exceptionSecond.getMessage() );
    }
}
