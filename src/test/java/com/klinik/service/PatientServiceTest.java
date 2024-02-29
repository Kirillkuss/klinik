package com.klinik.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.klinik.excep.MyException;
import com.klinik.repositories.DocumentRepository;
import com.klinik.repositories.PatientRepository;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;

@Epic(value = "Класс предназначен для тестирования сервиса PatientService")
@Owner(value = "Barysevich K. A.")
@DisplayName( "Класс предназначен для тестирования сервиса PatientService")
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class PatientServiceTest {

    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    public void setUp() {
        patientService = new PatientService(patientRepository, documentRepository, entityManager);
    }
    
    @Test
    @Feature("Получение списка всех пациентов")
    @DisplayName("Получение списка всех пациентов")
    public void testGetAllPatients() throws Exception{
        assertNotNull( patientService.getAllPatients() );
        assertEquals( patientService.getAllPatients() , patientService.getAllPatients() );
        Allure.addAttachment("Результат:", "text/plain",  patientService.getAllPatients().toString() );
    }

    @Test
    @Feature("Поиск по слову")
    @DisplayName("Поиск по слову")
    public void testFindByWord() throws Exception{
        String REQUEST = "Петр";
        assertThrows( MyException.class, () -> {patientService.findByWord( "2323424dfsdfs");});
        assertNotNull( patientService.findByWord( REQUEST ));
        assertEquals( patientService.findByWord( REQUEST ), patientService.findByWord( REQUEST ));
        Allure.addAttachment("Результат:", "text/plain",  patientService.findByWord( REQUEST ).toString() );
    }
/** 
    @Test
    @DisplayName("Добавить пациента")
    public void testAddPatient() throws Exception{
        Patient patient = new Patient( 1L, "test", "test", "test", Gender.MAN, "94567", "rt", null );
        Long IdDocument = 1L;
        assertThrows( MyException.class, () ->{ patientService.addPatient( patient, IdDocument );});
    }
  */
}
