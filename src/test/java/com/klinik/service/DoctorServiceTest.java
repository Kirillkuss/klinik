package com.klinik.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import com.klinik.entity.Doctor;
import com.klinik.excep.MyException;

@SpringBootTest
@Disabled
@DisplayName( "Тест предназначен для тестирования сервиса DoctorService")
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
        assertNotNull(doctorService.findByFIO(  "Тест" ));
	}

    @DisplayName( "Ошибка - По данному запросу ничего не найдено")
    @Test
	public void testFindByWordError() throws Exception {
        assertThrows( MyException.class, () -> {
            doctorService.findByFIO( "xjhchjdsd23545" );
        });  
	}

    @DisplayName( "Ошибка - По данному запросу ничего не найдено")
    @Test
	public void testSaveDoctorError() throws Exception {
        assertThrows( MyException.class, () -> {
            doctorService.saveDoctor( new Doctor( 1L, "test", "test", "test" ) );
        });  
	}

    @Transactional
    @DisplayName("Добавить доктора")
    @Test
	public void testSaveDoctor() throws Exception {
        doctorService.saveDoctor( new Doctor(15L,
                                              "Calam",
                                                 "Calam",
                                             "Calam" ));
	}

}
