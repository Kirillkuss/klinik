package com.klinik.rest;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Step;
import io.qameta.allure.TmsLink;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.qameta.allure.Feature;

@Feature("Тест АПИ для сущности Doctor")
@DisplayName("Тест АПИ для сущности Doctor")
public class RestDoctorTest {


    @Test
    @Description("Получение количества врачей")
    @DisplayName("Вызов метода GET: http://localhost:8082/web/doctors?page=page&size=size")
    @Link(name = "swagger", url = "http://localhost:8082/web/swagger-ui/index.html#/1.%20Doctors/getCountDoctors")
    @TmsLink("TEST-3545")
    public void testGetDoctorCounts() {
        RestAssured.baseURI = "http://localhost:8082";
        Response response = given().when().get("/web/doctors/counts");
        response.then().statusCode(200);
        Allure.addAttachment("Результат:", "application/json", response.andReturn().asString() );
    }

    @Description("Получение списка врачей")
    @DisplayName("Вызов метода GET: http://localhost:8082/web/doctors/counts")
    @ParameterizedTest
    @Link(name = "swagger", url = "http://localhost:8082/web/swagger-ui/index.html#/1.%20Doctors/getAllDoc")
    @CsvSource({"1, 10", "500, 30", "1000, 4"})
    public void testGetDocAll( int page, int size ){
        RestAssured.baseURI = "http://localhost:8082";
        Response response = given().when().get("/web/doctors?page=" + page + "&size=" + size );
        response.then().statusCode(200);
        Allure.addAttachment("Результат:", "application/json", response.andReturn().asString() );
    }

}
