package com.klinik.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;
import com.klinik.KlinikApplication;
import com.klinik.entity.Doctor;
import com.klinik.excep.MyException;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;

@Epic(value = "Тест предназначен для тестирования сервиса DoctorService")
@Owner(value = "Barysevich K. A.")
@DisplayName( "Тест предназначен для тестирования сервиса DoctorService")
@SpringBootTest( webEnvironment = WebEnvironment.RANDOM_PORT, classes = KlinikApplication.class )
public class DoctorServiceTest {

    @Autowired DoctorService doctorService;

    @Feature("Метод allDoctor - Получение списка врачей")
    @DisplayName( "Список всех врачей")
    @Test
	public void testGetAll() throws Exception {
        assertNotNull(doctorService.allDoctor(1,100));
        assertEquals( doctorService.allDoctor(1,100), doctorService.allDoctor(1,100) );
        Allure.addAttachment("Результат:", "text/plain", doctorService.allDoctor(3, 25) .toString() );
	}

    @Feature("Метод findByFIO - Поиск доктора по слову")
    @DisplayName( "Поиск доктора по слову")
    @Test
	public void testFindByWord() throws Exception {
        assertNotNull(doctorService.findByFIO(  "Тест", 1, 10 ));
        Allure.addAttachment("Результат:", "text/plain", doctorService.findByFIO(  "Тест", 1, 10 ).toString() );
	}

    @Feature("Метод findByFIO - По данному запросу ничего не найдено")
    @DisplayName( "Ошибка - По данному запросу ничего не найдено")
    @Test
	public void testFindByWordError() throws Exception {
        assertThrows( MyException.class, () -> {
            doctorService.findByFIO( "xjhchjdsd23545", 1, 100 );
        });  
	}

    @Feature("Метод saveDoctor - Ошибка - По данному запросу ничего не найдено")
    @DisplayName( "Ошибка - По данному запросу ничего не найдено")
    @Test
	public void testSaveDoctorError() throws Exception {
        assertThrows( MyException.class, () -> {
            doctorService.saveDoctor( new Doctor( 1L, "test", "test", "test" ) );
        });  
	}

    @Test
    @Transactional
    @Feature("Метод saveDoctor - Добавить доктора")
    @DisplayName("Добавить доктора")
	public void testSaveDoctor() throws Exception {
        Doctor doctor = doctorService.saveDoctor( new Doctor(15L,"Calam","Calam", "Calam" ));
        Allure.addAttachment("Результат:", "text/plain", doctor.toString() );
	}

    @Test
    @Transactional
    @Feature("Метод saveDoctor - Добавить доктора генерируя данные через Instancio")
    @DisplayName("Добавить доктора генерируя данные через Instancio")
    public void testSaveDocotr() throws Exception{
        Doctor doctor =  Instancio.of(Doctor.class).ignore(Select.field(Doctor::getIdDoctor)).create();
        doctor.setIdDoctor( -1L );
        Doctor response = doctorService.saveDoctor( doctor );
        assertNotNull( response );
        Allure.addAttachment("Результат:", "text/plain",  response.toString() );
    }

}
