package com.klinik.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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

@Disabled
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@DisplayName("Тестирование сервиса DoctorService from Mock")
public class DoctorServiceMock {

    private DoctorService doctorService;
    
    @Autowired
    private DoctorRerository doctorRerository;

    @BeforeEach
    public void tearDown() {
        doctorService  = mock( DoctorService.class );
        doctorService.doctorRerository = doctorRerository;
    }
    
    @AfterEach
    public void setUp() {
    }

    @Test
    @DisplayName("Получение списка всех врачей")
    public void findAllTest(){
        Mockito.when( doctorService.allDoctor() ).thenCallRealMethod();
        Mockito.when( doctorService.allDoctor() ).thenReturn( new ArrayList<>());
        Mockito.when( doctorService.allDoctor() ).then(( InvocationOnMock inv ) ->{
           return ( List<Doctor> ) inv.callRealMethod(); 
        });
        Allure.addAttachment("Результат:", "text/plain", doctorService.allDoctor().toString() );
        assertEquals(doctorService.allDoctor(), doctorService.allDoctor() );
        Mockito.verify( doctorService, times(3 )).allDoctor();
    }

    @ParameterizedTest
    @CsvSource({"Petrov", "Тест", "Один"})
    @DisplayName("Поиск доктора по фио")
    public void findByWordTest( String WORD ) throws Exception{
        Mockito.when( doctorService.findByFIO( WORD )).thenCallRealMethod();
        Mockito.when( doctorService.findByFIO( WORD ) ).thenReturn( new ArrayList<>());
        Mockito.when( doctorService.findByFIO( WORD ) ).then(( InvocationOnMock inv ) ->{
           return ( List<Doctor> ) inv.callRealMethod(); 
        });
        Allure.addAttachment("Результат:", "text/plain", doctorService.findByFIO( WORD ).toString() );
        assertEquals(doctorService.findByFIO( WORD ), doctorService.findByFIO( WORD ) );
        Mockito.verify( doctorService, times(3 )).findByFIO( WORD );
    }

    @DisplayName("Параметры для тестирования")
    public static Stream<Arguments> getDoctors() throws Exception{
        return Stream.of( Arguments.of( new Doctor( 15L, "FIRST", "FIRST", "FIRST" )),
                          Arguments.of( new Doctor( 16L, "SECOND", "SECOND", "SECOND" )));
    }

    @ParameterizedTest
    @MethodSource("getDoctors")
    @DisplayName("Добавить доктора")
    public void saveDoctorTest( Doctor doctor) throws Exception{
        Mockito.when( doctorService.saveDoctor( doctor )).thenCallRealMethod();
        Mockito.when( doctorService.saveDoctor( doctor )).thenReturn( new Doctor() );
        Mockito.when( doctorService.saveDoctor( doctor )).then(( InvocationOnMock inv ) ->{
            return ( Doctor ) inv.callRealMethod();
        });
        Allure.addAttachment("Результат:", "text/plain", doctorService.saveDoctor( doctor ).toString() );
        assertNotNull(doctorService.saveDoctor( doctor ));
        Mockito.verify( doctorService, times(2 )).saveDoctor( doctor );
    }
    
}
