package com.klinik.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.klinik.entity.Doctor;
import com.klinik.repositories.DoctorRerository;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import java.util.NoSuchElementException;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Owner(value = "Barysevich K. A.")
@DisplayName("Тестирование сервиса DoctorService from Mock")
@Epic(value = "Тестирование сервиса DoctorService from Mock")
public class DoctorServiceMock {

    private final String TYPE = "application/json";
    private final String RESULT = "Результат: ";

    @Autowired private DoctorRerository doctorRerository;
    @Autowired private EntityManager entityManager;
               private DoctorService doctorService;

    @BeforeEach
    public void tearDown() {
        doctorService  = mock( DoctorService.class );
        doctorService.doctorRerository = doctorRerository;
        doctorService.entityManager = entityManager;
    }
    
    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("Получение списка всех врачей (allDoctor)")
    public void findAllTest(){
        Mockito.when( doctorService.allDoctor(1, 15 )).thenCallRealMethod();
        Mockito.when( doctorService.allDoctor(1, 15 )).thenReturn( new ArrayList<>());
        Mockito.when( doctorService.allDoctor(1, 15 )).then(( InvocationOnMock inv ) ->{
           return ( List<Doctor> ) inv.callRealMethod(); 
        });
        Allure.addAttachment(RESULT, TYPE, doctorService.allDoctor(1, 15) .toString() );
        assertEquals(doctorService.allDoctor(1, 15 ) , doctorService.allDoctor(1, 15 ));
        Mockito.verify( doctorService, times(3 )).allDoctor(1, 15 );
    }

    @SuppressWarnings("unchecked")
    @ParameterizedTest
    @CsvSource({"1,3", "4,5", "2, 8"})
    @DisplayName("Получение списка всех врачей (getLazyDoctor)")
    public void testgetLazyDoctor(int page, int size ){
        Mockito.when( doctorService.getLazyDoctor( page, size )).thenCallRealMethod();
        Mockito.when( doctorService.getLazyDoctor( page, size )).thenReturn( new ArrayList<>());
        Mockito.when( doctorService.getLazyDoctor( page, size )).then(( InvocationOnMock inv ) ->{
           return ( List<Doctor> ) inv.callRealMethod(); 
        });
        Allure.addAttachment(RESULT, TYPE, doctorService.getLazyDoctor( page, size ) .toString());
        assertEquals(doctorService.getLazyDoctor( page, size ), doctorService.getLazyDoctor( page, size ));
        Mockito.verify( doctorService, times(3 )).getLazyDoctor( page, size );
    }

    @SuppressWarnings("unchecked")
    @ParameterizedTest
    @CsvSource({"Петров, 1, 2", "Сергей, 1,7", "Михайлович, 1, 6"})
    @DisplayName("Поиск доктора по фио")
    public void findByWordTest( String WORD, int PAGE, int SIZE ) throws Exception{
        Mockito.when( doctorService.findByFIO( WORD, PAGE, SIZE )).thenCallRealMethod();
        Mockito.when( doctorService.findByFIO( WORD, PAGE, SIZE )).thenReturn( new ArrayList<>());
        Mockito.when( doctorService.findByFIO( WORD, PAGE, SIZE )).then(( InvocationOnMock inv ) ->{
           return ( List<Doctor> ) inv.callRealMethod(); 
        });
        Allure.addAttachment(RESULT, TYPE, doctorService.findByFIO( WORD, PAGE, SIZE ).toString());
        assertEquals(doctorService.findByFIO( WORD, PAGE, SIZE ), doctorService.findByFIO( WORD, PAGE, SIZE ));
        Mockito.verify( doctorService, times(3 )).findByFIO( WORD, PAGE, SIZE );
    }

    @SuppressWarnings("unchecked")
    @ParameterizedTest
    @CsvSource({"KHSAUIPHFUISJNCZXJKCBHYS, 1, 2"})
    @DisplayName("Поиск доктора по фио - ошибка")
    public void findByWordTestError( String WORD, int PAGE, int SIZE ) throws Exception{
        Mockito.when( doctorService.findByFIO( WORD, PAGE, SIZE)).thenCallRealMethod();
        assertThrows( NoSuchElementException.class , () ->{
            doctorService.findByFIO( WORD, PAGE, SIZE );
        });
        Mockito.verify( doctorService, times(1 )).findByFIO( WORD, PAGE, SIZE );
    }

    @DisplayName("Параметры для тестирования")
    public static Stream<Arguments> getDoctors() throws Exception{
        return Stream.of( Arguments.of( new Doctor( -1L, "FIRST", "FIRST", "FIRST" )),
                          Arguments.of( new Doctor( -1L, "SECOND", "SECOND", "SECOND" )));
    }

    @ParameterizedTest
    @MethodSource("getDoctors")
    @DisplayName("Добавить доктора")
    public void saveDoctorTest( Doctor doctor ) throws Exception{
        Mockito.when( doctorService.saveDoctor( doctor )).thenCallRealMethod();
        Mockito.when( doctorService.saveDoctor( doctor )).thenReturn( new Doctor() );
        Mockito.when( doctorService.saveDoctor( doctor )).then(( InvocationOnMock inv ) ->{
            return ( Doctor ) inv.callRealMethod();
        });
        Doctor response = doctorService.saveDoctor( doctor );
        Allure.addAttachment(RESULT, TYPE, response.toString() );
        assertNotNull( response );
        Mockito.verify( doctorService, times(1 )).saveDoctor( doctor );
    }
    
    @ParameterizedTest
    @CsvSource({"KHSAUIPHFUISJNCZXJKCBHYS, 1, 2"})
    @DisplayName("Сохранить врача - ошибка")
    public void saveDoctorTestError(  ) throws Exception{
        Doctor requesError = new Doctor( 1L, "FIRST", "FIRST", "FIRST" );
        Mockito.when( doctorService.saveDoctor( requesError )).thenCallRealMethod();
        assertThrows( IllegalArgumentException.class , () ->{            doctorService.saveDoctor( requesError );
        });
        Mockito.verify( doctorService, times(1 )).saveDoctor( requesError );
    }

    @Test
    @DisplayName("Количество всех врачей")
    public void testGetCountDoctors( ) throws Exception{
        Mockito.when( doctorService.getCountDoctors()).thenCallRealMethod();
        Mockito.when( doctorService.getCountDoctors()).thenReturn( Long.getLong( "1" ) );
        Mockito.when( doctorService.getCountDoctors()).then(( InvocationOnMock inv ) ->{
            return ( Long ) inv.callRealMethod();
        });
        Allure.addAttachment(RESULT, TYPE,  doctorService.getCountDoctors().toString() );
        assertNotNull(  doctorService.getCountDoctors() );
        Mockito.verify( doctorService, times(2 )).getCountDoctors();
    }
    
}