package com.klinik.rest;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.Arguments;
import com.klinik.entity.Doctor;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.TmsLink;

@Epic(value = "Тестирование АПИ - DoctorController")
@DisplayName("Тестирование АПИ - DoctorController")
@Owner(value = "Barysevich K. A.")
public interface AllureDoctorTest {
    
    @DisplayName("Параметры для тестирования")
    public static Stream<Arguments> getParams() throws Exception{
        return Stream.of( Arguments.of( new Doctor( -1L, "GERP", "DERT", "ERYT") ) );
    }
    
    @Feature("Получение списка врачей")
    @Description("Получение списка врачей")
    @DisplayName("Вызов метода GET: http://localhost:8082/web/doctors?page=page&size=size")
    @Link(name = "swagger", url = "http://localhost:8082/web/swagger-ui/index.html#/1.%20Doctors/getAllDoc")
    public void testGetAllDocuments( int page, int size );

    @Feature("Получение количества врачей")
    @Description("Получение количества врачей")
    @DisplayName("Вызов метода GET: http://localhost:8082/web/doctors/counts")
    @Link(name = "swagger", url = "http://localhost:8082/web/swagger-ui/index.html#/1.%20Doctors/getCountDoctors")
    @TmsLink("TEST-3545")
    public void testGetDoctorCounts();

    @Feature("Получение списка врачей (LAZY)")
    @Description("Получение списка врачей (LAZY)")
    @DisplayName("Вызов метода POST: http://localhost:8082/web/doctors/lazy?page=1&size=12")
    @Link(name = "swagger", url = "http://localhost:8082/web/swagger-ui/index.html#/1.%20Doctors/getLazyDoctors")
    public void testGetDocumentsLazy( int page, int size );

    @Feature("Добавить врача")
    @Description("Добавить врача")
    @DisplayName("Вызов метода POST: http://localhost:8082/web/doctors/add")
    @Link(name = "swagger", url = "http://localhost:8082/web/swagger-ui/index.html#/1.%20Doctors/addDoctor")
    public void testAddDoctor( Doctor doctor );

    @Feature("Получение врачей по ФИО")
    @Description("Получение врачей по ФИО")
    @DisplayName("Вызов метода GET: http://localhost:8082/web/doctors/fio/{word}{page}{size}?word=Test&page=1&size=10")
    @Link(name = "swagger", url = "http://localhost:8082/web/swagger-ui/index.html#/1.%20Doctors/findByFIO")
    @TmsLink("TEST-3545")
    public void testGetByFIO(int page, int size, String fio);
}
