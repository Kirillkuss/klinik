package com.klinik.rest;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.transaction.annotation.Transactional;

import com.klinik.entity.Doctor;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.TmsLink;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.qameta.allure.Feature;

@Epic(value = "Тест АПИ для сущности Doctor")
@DisplayName("Тест АПИ для сущности Doctor")
@Owner(value = "Barysevich K. A.")
public class RestDoctorTest {

    @Feature("Получение списка врачей")
    @Description("Получение списка врачей")
    @DisplayName("Вызов метода GET: http://localhost:8082/web/doctors?page=page&size=size")
    @Link(name = "swagger", url = "http://localhost:8082/web/swagger-ui/index.html#/1.%20Doctors/getAllDoc")
    @ParameterizedTest
    @CsvSource({"1, 10", "500, 30", "1000, 4"})
    public void testGetAllDocuments( int page, int size ){
        try{
            RestAssured.baseURI = "http://localhost:8082";
            Response response = given().when().get("/web/doctors?page=" + page + "&size=" + size );
            response.then().statusCode(200);
            Allure.addAttachment("Результат:", "application/json", response.andReturn().asString() );
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", "application/json", ex.getMessage() );
        }
    }

    @Feature("Получение количества врачей")
    @Description("Получение количества врачей")
    @DisplayName("Вызов метода GET: http://localhost:8082/web/doctors/counts")
    @Link(name = "swagger", url = "http://localhost:8082/web/swagger-ui/index.html#/1.%20Doctors/getCountDoctors")
    @RepeatedTest( 2 )
    @TmsLink("TEST-3545")
    public void testGetDoctorCounts() {
        try{
            RestAssured.baseURI = "http://localhost:8082";
            Response response = given().when().get("/web/doctors/counts");
            response.then().statusCode(200);
            Allure.addAttachment("Результат:", "application/json", response.andReturn().asString() );
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", "application/json", ex.getMessage() );
        }
    }

    @Feature("Получение списка врачей (LAZY)")
    @Description("Получение списка врачей (LAZY)")
    @DisplayName("Вызов метода POST: http://localhost:8082/web/doctors/lazy?page=1&size=12")
    @Link(name = "swagger", url = "http://localhost:8082/web/swagger-ui/index.html#/1.%20Doctors/getLazyDoctors")
    @ParameterizedTest
    @CsvSource({"1, 14", "486, 50", "851, 12"})
    public void testGetDocumentsLazy( int page, int size ){
        try{
            RestAssured.baseURI = "http://localhost:8082";
            Response response = given().when().post("/web/doctors/lazy?page=" + page + "&size=" + size );
            response.then().statusCode(200);
            Allure.addAttachment("Результат:", "application/json", response.andReturn().asString() );
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", "application/json", ex.getMessage() );
        }
    }

    @DisplayName("Параметры для тестирования")
    public static Stream<Arguments> getParams() throws Exception{
        return Stream.of( Arguments.of( new Doctor( -1L, "GERP", "DERT", "ERYT") ) );
    }

    @Feature("Добавить врача")
    @Description("Добавить врача")
    @DisplayName("Вызов метода POST: http://localhost:8082/web/doctors/add")
    @Link(name = "swagger", url = "http://localhost:8082/web/swagger-ui/index.html#/1.%20Doctors/addDoctor")
    @ParameterizedTest
    @MethodSource("getParams")
    public void testAddDoctor( Doctor doctor ){
        try{
            RestAssured.baseURI = "http://localhost:8082";
            Response response = given().when().contentType(ContentType.JSON).body( doctor ).post("/web/doctors/add");
            response.then().statusCode(200);
            Allure.addAttachment("Результат:", "application/json", response.andReturn().asString() );
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", "application/json", ex.getMessage() );
        }
    }

    @Feature("Получение врачей по ФИО")
    @Description("Получение врачей по ФИО")
    @DisplayName("Вызов метода GET: http://localhost:8082/web/doctors/fio/{word}{page}{size}?word=Test&page=1&size=10")
    @Link(name = "swagger", url = "http://localhost:8082/web/swagger-ui/index.html#/1.%20Doctors/findByFIO")
    @TmsLink("TEST-3545")
    @ParameterizedTest
    @CsvSource({"1, 14, Test", "2, 5, Mouse", "8, 10, Elk"})
    public void testGetByFIO(int page, int size, String fio) {
        try{
            RestAssured.baseURI = "http://localhost:8082";
            Response response = given().queryParam("word", fio).queryParam("page", page).queryParam("size", size).when().get("/web/doctors/fio" );
            response.then().statusCode(200);
            Allure.addAttachment("Результат:", "application/json", response.andReturn().asString() );
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", "application/json", ex.getMessage() );
        }
    }
}