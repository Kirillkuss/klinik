package com.klinik.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.klinik.entity.Doctor;
import com.klinik.excep.MyException;

@SpringBootTest
public class DoctorServiceTest {

    @Autowired DoctorService doctorService;

    @DisplayName( "Список всех врачей")
    @Test
	public void testGetAll() throws Exception {
        assertNotNull(doctorService.allDoctor());
        assertEquals( doctorService.allDoctor(), doctorService.allDoctor() );
	}

    @DisplayName( "Поиск доктора по слову")
    @Test
	public void testFindByWord() throws Exception {
        String request = "Чукча";
        assertNotNull(doctorService.findByFIO(request));
	}

    @DisplayName( "Ошибка - По данному запросу ничего не найдено")
    @Test
	public void testFindByWordError() throws Exception {
        String request = "xjhchjdsd";
        assertThrows( MyException.class, () -> {
            doctorService.findByFIO(request);
        });  
	}

    @DisplayName( "Ошибка - По данному запросу ничего не найдено")
    @Test
	public void testSaveDoctorError() throws Exception {
        Doctor doctor = new Doctor( 1L, "test", "test", "test" );
        assertThrows( MyException.class, () -> {
            doctorService.saveDoctor( doctor );
        });  
	}


}
