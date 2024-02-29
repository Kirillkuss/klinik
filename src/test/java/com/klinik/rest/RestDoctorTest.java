package com.klinik.rest;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Features;
import io.qameta.allure.Step;
import io.qameta.allure.Stories;
import io.qameta.allure.TmsLink;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.qameta.allure.Feature;

@Feature("Тест АПИ для сущности Doctor")
@DisplayName("Тест АПИ для сущности Doctor")
public class RestDoctorTest {

   /**  @Test
    public void testGetDoctorCounts() {
        given().when().get("http://localhost:8082/web/doctors/counts").then().statusCode(200);
    }*/

    @Test
   // @Feature("Тест АПИ для сущности Doctor")
    @Description("Получение количества врачей 2")
    @DisplayName("Получение количества врачей 2")
    @TmsLink("TEST-1234")
    public void testGetDoctorCountsTWo() {
        when().request("GET", "http://localhost:8082/web/doctors/counts").then().statusCode(200);
       // Allure.addAttachment("Результат:", "text/plain", response.andReturn().asString() );
    } 

    @Test
   // @Feature("Тест АПИ для сущности Doctor 1")
    @Description("Получение количества врачей 1")
    @DisplayName("Получение количества врачей 1")
    @TmsLink("TEST-3545")
    public void testGetDoctorCounts() {
        RestAssured.baseURI = "http://localhost:8082";
        Response response = given().when().get("/web/doctors/counts");
        response.then().statusCode(200);
        //System.out.println( "RESPONSE >>>" + response.andReturn().asString() );
        Allure.addAttachment("Результат:", "text/plain", response.andReturn().asString() );
    }

   // @Feature("Тест АПИ для сущности Doctor ")
    @Description("Получение списка врачей")
    @DisplayName("Получение списка врачей")
    @ParameterizedTest
    @CsvSource({"1, 10", "5, 30", "40, 4"})
    @Step("\"Получение списка врачей страница -> {page} и размер -> {size}")
    public void testGetDocAll( int page, int size ){
        RestAssured.baseURI = "http://localhost:8082";
        Response response = given().when().get("/web/doctors?page=" + page + "&size=" + size );
        response.then().statusCode(200);
        //Allure.addAttachment("Результат:", "text/plain", response.andReturn().asString() );
    }

}
