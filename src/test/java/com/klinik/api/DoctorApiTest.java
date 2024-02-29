package com.klinik.api;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import com.klinik.entity.Doctor;
import com.klinik.rest.AllureDoctorTest;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class DoctorApiTest implements AllureDoctorTest{
    
    @ParameterizedTest
    @CsvSource({"1, 10", "500, 30", "1000, 4"})
    @Override
    public void testGetAllDocuments(int page, int size) {
        try{
            RestAssured.baseURI = "http://localhost:8082";
            Response response = given().when().get("/web/doctors?page=" + page + "&size=" + size );
            response.then().statusCode(200);
            Allure.addAttachment("Результат:", "application/json", response.andReturn().asString() );
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", "application/json", ex.getMessage() );
        }
    }

    @RepeatedTest( 2 )
    @Override
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

    @ParameterizedTest
    @CsvSource({"1, 14", "486, 50", "851, 12"})
    @Override
    public void testGetDocumentsLazy(int page, int size) {
        try{
            RestAssured.baseURI = "http://localhost:8082";
            Response response = given().when().post("/web/doctors/lazy?page=" + page + "&size=" + size );
            response.then().statusCode(200);
            Allure.addAttachment("Результат:", "application/json", response.andReturn().asString() );
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", "application/json", ex.getMessage() );
        }
    }

    @ParameterizedTest
    @MethodSource("getParams")
    @Override
    public void testAddDoctor(Doctor doctor) {
        try{
            RestAssured.baseURI = "http://localhost:8082";
            Response response = given().when().contentType(ContentType.JSON).body( doctor ).post("/web/doctors/add");
            response.then().statusCode(200);
            Allure.addAttachment("Результат:", "application/json", response.andReturn().asString() );
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", "application/json", ex.getMessage() );
        }
    }

    @ParameterizedTest
    @CsvSource({"1, 14, Test", "2, 5, Mouse", "8, 10, Elk"})
    @Override
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
