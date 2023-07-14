package com.klinik.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.klinik.entity.Gender;
import com.klinik.entity.Patient;
import com.klinik.excep.MyException;
import com.klinik.repositories.DocumentRepository;
import com.klinik.repositories.PatientRepository;

@DisplayName( "Класс предназначен для тестирования сервиса PatientService")
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class PatientServiceTest {

    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @BeforeEach
    public void setUp() {
        patientService = new PatientService(patientRepository, documentRepository);
    }
    
    @Test
    @DisplayName("Получение списка всех пациентов")
    public void testGetAllPatients() throws Exception{
        assertNotNull( patientService.getAllPatients() );
        assertEquals( patientService.getAllPatients() , patientService.getAllPatients() );
    }

    @Test
    @DisplayName("Поиск по слову")
    public void testFindByWord() throws Exception{
        String REQUEST = "Петр";
        assertThrows( MyException.class, () -> {patientService.findByWord( "2323424dfsdfs");});
        assertNotNull( patientService.findByWord( REQUEST ));
        assertEquals( patientService.findByWord( REQUEST ), patientService.findByWord( REQUEST ));
    }

    @Test
    @DisplayName("Добавить пациента")
    public void testAddPatient() throws Exception{
        Patient patient = new Patient( 1L, "test", "test", "test", Gender.MAN, "94567", "rt", null );
        Long IdDocument = 1L;
        assertThrows( MyException.class, () ->{ patientService.addPatient( patient, IdDocument );});
    }
  
}
