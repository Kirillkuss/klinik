package com.klinik.testcontainer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.Random;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
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

    @Test
    @DisplayName( "Тестирование метода saveDoctor")
    public void testSaveDoctor() throws Exception{
        Random random = new Random();
        Doctor doctor = new Doctor();
               doctor.setIdDoctor( random.nextLong() );
               doctor.setFullName( "Docker");
               doctor.setName( "Post");
               doctor.setSurname( "Test");
        Allure.parameter( "doctor", doctor );
        Allure.addAttachment( REZULT, TYPE, doctorService.saveDoctor( doctor ).toString());
        assertNotNull( doctorService.saveDoctor( doctor ));
    }

    @Test
    @DisplayName( "Тестирование метода allDoctor")
    public void testAllDoctor(){
        int page = 1;
        int size = 10;
        Allure.parameter( "page", page );
        Allure.parameter( "size", size );
        Allure.addAttachment( REZULT, TYPE, doctorService.allDoctor( page, size).toString());
        assertNotNull( doctorService.allDoctor( page, size));
        assertEquals(doctorService.allDoctor( page, size), doctorService.allDoctor( page, size));
    }


}
