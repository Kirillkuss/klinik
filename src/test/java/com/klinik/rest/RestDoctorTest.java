package com.klinik.rest;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

@Feature("Тест АПИ для сущности Doctor")
@DisplayName("Тест АПИ для сущности Doctor")
@Owner(value = "Barysevich K. A.")
public class RestDoctorTest {

    @Description("Получение списка врачей")
    @DisplayName("Вызов метода GET: http://localhost:8082/web/doctors?page=page&size=size")
    @ParameterizedTest
    @Link(name = "swagger", url = "http://localhost:8082/web/swagger-ui/index.html#/1.%20Doctors/getAllDoc")
    @CsvSource({"1, 10", "500, 30", "1000, 4"})
    public void testGetAllDocuments( int page, int size ){
        RestAssured.baseURI = "http://localhost:8082";
        Response response = given().when().get("/web/doctors?page=" + page + "&size=" + size );
        response.then().statusCode(200);
        Allure.addAttachment("Результат:", "application/json", response.andReturn().asString() );
    }

    @Test
    @Description("Получение количества врачей")
    @DisplayName("Вызов метода GET: http://localhost:8082/web/doctors/counts")
    @Link(name = "swagger", url = "http://localhost:8082/web/swagger-ui/index.html#/1.%20Doctors/getCountDoctors")
    @TmsLink("TEST-3545")
    public void testGetDoctorCounts() {
        RestAssured.baseURI = "http://localhost:8082";
        Response response = given().when().get("/web/doctors/counts");
        response.then().statusCode(200);
        Allure.addAttachment("Результат:", "application/json", response.andReturn().asString() );
    }

    @Description("Получение списка врачей  (LAZY)")
    @DisplayName("Вызов метода POST: http://localhost:8082/web/doctors/lazy?page=1&size=12")
    @ParameterizedTest
    @Link(name = "swagger", url = "http://localhost:8082/web/swagger-ui/index.html#/1.%20Doctors/getLazyDoctors")
    @CsvSource({"1, 14", "486, 50", "851, 12"})
    public void testGetDocumentsLazy( int page, int size ){
        RestAssured.baseURI = "http://localhost:8082";
        Response response = given().when().post("/web/doctors/lazy?page=" + page + "&size=" + size );
        response.then().statusCode(200);
        Allure.addAttachment("Результат:", "application/json", response.andReturn().asString() );
    }

    @Description("Добавить врача")
    @DisplayName("Вызов метода POST: http://localhost:8082/web/doctors/add")
    @Test
    @Link(name = "swagger", url = "http://localhost:8082/web/swagger-ui/index.html#/1.%20Doctors/addDoctor")
    public void testAddDoctor(  ){
        String requestBody = "{ \"idDoctor\": \"-1\", \"surname\": \"Monstr\", \"name\": \"TERT\", \"fullName\": \"DERK\" }";
        RestAssured.baseURI = "http://localhost:8082";
        Response response = given().when().contentType(ContentType.JSON).body( requestBody ).post("/web/doctors/add");
        response.then().statusCode(200);
        Allure.addAttachment("Результат:", "application/json", response.andReturn().asString() );
    }


    @Description("Получение врачей по ФИО")
    @DisplayName("Вызов метода GET: http://localhost:8082/web/doctors/fio/{word}{page}{size}?word=Test&page=1&size=10")
    @Link(name = "swagger", url = "http://localhost:8082/web/swagger-ui/index.html#/1.%20Doctors/findByFIO")
    @TmsLink("TEST-3545")
    @ParameterizedTest
    @Epic(value = "Получение врачей по ФИО")
    @CsvSource({"1, 14, Test", "2, 5, Mouse", "8, 10, Elk"})
    public void testGetByFIO(int page, int size, String fio) {
        RestAssured.baseURI = "http://localhost:8082";
        Response response = given().queryParam("word", fio).queryParam("page", page).queryParam("size", size).when().get("/web/doctors/fio" );
        response.then().statusCode(200);
        Allure.addAttachment("Результат:", "application/json", response.andReturn().asString() );
    }
}