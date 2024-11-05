package com.klinik.testcontainer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.NoSuchElementException;
import java.util.Random;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import com.klinik.entity.Doctor;
import com.klinik.service.DoctorService;
import io.qameta.allure.Allure;
/**
 * Тестирование сервиса DoctorService через бд, которая формирована в Docker
 * через тестовый application-docker-postgres.properties
 */
@Disabled
@SpringBootTest
@TestPropertySource(locations = "/application-docker-postgres.properties")
@DisplayName( "Тестирование сервиса DoctorService")
public class DoctorServiceTest {

    @Autowired
    private DoctorService doctorService;

    private final String TYPE = "application/json";
    private final String REZULT = "результат: ";
    private final String ERROR  = "Ожидаемая ошибка :";

    @Test
    @Transactional
    @DisplayName( "Тестирование метода saveDoctor")
    public void testSaveDoctor() throws Exception{
        Doctor doctor = new Doctor( new Random().nextLong(), "Docker", "Post", "Test");
        Allure.parameter( "doctor", doctor );
        Doctor responce = doctorService.saveDoctor( doctor );
        Allure.addAttachment( REZULT, TYPE, responce.toString());
        assertNotNull( responce );
    }

   // @Test
    @DisplayName( "Тестирование метода saveDoctor - ERROR")
    public void testSaveDoctorError() throws Exception{
        Doctor doctor = new Doctor( 1L, "Docker", "Post", "Test");
        Allure.parameter( "doctor", doctor );
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> doctorService.saveDoctor( doctor ));
        assertEquals("Пользователь с таким ИД уще существует", exception.getMessage());
        Allure.addAttachment( ERROR, TYPE, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({"1, 10"})
    @DisplayName( "Тестирование метода allDoctor")
    public void testAllDoctor( int page, int size ){
        Allure.addAttachment( REZULT, TYPE, doctorService.allDoctor( page, size).toString());
        assertNotNull( doctorService.allDoctor( page, size));
        assertEquals(doctorService.allDoctor( page, size), doctorService.allDoctor( page, size));
    }

    @ParameterizedTest
    @CsvSource({"1, 5"})
    @DisplayName("Ленивая загрузка")
    public void testGetLazyDoctor( int page, int size ){
        Allure.addAttachment(REZULT, TYPE, doctorService.getLazyDoctor( page, size ).toString());
        assertNotNull( doctorService.getLazyDoctor( page, size ));
        assertEquals(doctorService.getLazyDoctor( page, size ), doctorService.getLazyDoctor( page, size ));
    } 

    @Test
    @DisplayName("Количество")
    public void testGetCount(){
        Allure.addAttachment(REZULT, TYPE, doctorService.getCountDoctors().toString());
        assertNotNull( doctorService.getCountDoctors());
        assertEquals(doctorService.getCountDoctors(), doctorService.getCountDoctors());
    }

   // @ParameterizedTest
    @CsvSource({"Test, 1, 5", "Post, 1, 2", "Docker, 2, 5"})
    @DisplayName("Поиск по фио")
    public void testFindByFIO( String word, int page, int size ) throws Exception{
        Allure.addAttachment(REZULT, TYPE, doctorService.findByFIO( word, page, size ).toString());
        assertNotNull( doctorService.findByFIO( word, page, size ));
        assertEquals(doctorService.findByFIO( word, page, size ), doctorService.findByFIO( word, page, size ));
    }

    @ParameterizedTest
    @CsvSource({"JLKHXCUBHSFJS, 1000, 1",})
    @DisplayName("Поиск по фио - Error")
    public void testFindByFIOError( String word, int page, int size ) throws Exception{
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> doctorService.findByFIO( word, page, size ));
        assertEquals("По данному запросу ничего не найдено", exception.getMessage());
        Allure.addAttachment( ERROR, TYPE, exception.getMessage());
    }

}
