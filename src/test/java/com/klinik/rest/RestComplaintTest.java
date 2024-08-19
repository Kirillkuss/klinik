package com.klinik.rest;

import static io.restassured.RestAssured.given;

import java.util.stream.Stream;

import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.klinik.entity.CardPatient;
import com.klinik.entity.Complaint;
import com.klinik.entity.Document;
import com.klinik.entity.TypeComplaint;
import com.klinik.request.AuthRequest;
import com.klinik.request.RequestTypeComplaint;
import com.klinik.response.AuthResponse;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@Owner(value = "Barysevich K. A.")
@Epic(value = "Тестирование АПИ - ComplaintController")
@DisplayName("Тестирование АПИ - ComplaintController")
public class RestComplaintTest {
    
    private static final String PATH = "http://localhost:8082";
    private static final String TYPE = "application/json";
    private static final String authorization = "Authorization";
    private static String token;
    private static String bearer;

    @BeforeAll
    @DisplayName("Получение токена") 
    public static void setUpClass() {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setLogin( "admin");
        authRequest.setPassword("admin");
        try{
            RestAssured.baseURI = PATH;
            Response response = given().contentType( TYPE )
                                       .body( authRequest )
                                       .when()
                                       .contentType( ContentType.JSON )
                                       .post("/auth/login");                         
            response.then().statusCode(200);
            ObjectMapper objectMapper = new ObjectMapper();
            AuthResponse authResponse = objectMapper.readValue(response.asString(), AuthResponse.class);
            token = authResponse.getToken();
            bearer = "Bearer " + token;
            Allure.addAttachment("token:", TYPE, token );
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", TYPE, ex.getMessage() );
        }
    }

    @Description("Получение справочника жалобы (GET)")
    @DisplayName("Получение справочника жалобы (GET)")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/6.%20%D0%A1omplaint/findAll")
    @Test
    public void testListComplaints(){
        try{
            RestAssured.baseURI = PATH;
            Response response = given().header( authorization, bearer )
                                       .when()
                                       .contentType( ContentType.JSON )
                                       .get("/complaints/list");
            response.then()
                    .statusCode( 200 );
            Allure.addAttachment("Результат:", TYPE, response.andReturn().asString() );
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", TYPE, ex.getMessage() );
        }
    }

    
    @Description("Получение жалобы с под жалобами (GET)")
    @DisplayName("Получение жалобы с под жалобами (GET)")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/6.%20%D0%A1omplaint/listComplaintWithTypeComplaints")
    @ParameterizedTest
    @CsvSource({"1", "2" })
    public void testComplaintsTypeId( Long id ){
        try{
            RestAssured.baseURI = PATH;
            Response response = given().header( authorization, bearer )
                                       .queryParam( "id", id )
                                       .when()
                                       .contentType( ContentType.JSON )
                                       .get("/complaints/type/{id}", id );
            response.then()
                    .statusCode( 200 );
            Allure.addAttachment("Результат:", TYPE, response.andReturn().asString() );
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", TYPE, ex.getMessage() );
        }
    }

    @DisplayName("Параметры для тестирования")
    public static Stream<Arguments> getComplaint() throws Exception{
        Complaint complaint = Instancio.of(Complaint.class).ignore(Select.field( Complaint::getIdComplaint )).create();
        complaint.setIdComplaint( -1L );
        return Stream.of( Arguments.of( complaint));
    }

    @Description("Добавление справочника жалобы (POST)")
    @DisplayName("Добавление справочника жалобы (POST)")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/6.%20%D0%A1omplaint/listComplaintWithTypeComplaints")
    @ParameterizedTest
    @MethodSource("getComplaint")
    public void testAddComplaintsAndTypeComplaint( Complaint complaint ){
        try{
            RestAssured.baseURI = PATH;
            Response response = given().header( authorization, bearer )
                                       .when()
                                       .contentType( ContentType.JSON )
                                       .body(complaint)
                                       .post("/complaints/complaint");
            response.then().statusCode( 201 );
            Allure.addAttachment("Результат:", TYPE, response.andReturn().asString() );
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", TYPE, ex.getMessage() );
        }
    }

    @DisplayName("Параметры для тестирования")
    public static Stream<Arguments> getTypeComplaint() throws Exception{
        RequestTypeComplaint requestTypeComplaint = Instancio.of(RequestTypeComplaint.class).ignore(Select.field( RequestTypeComplaint::getIdComplaint )).create();
        requestTypeComplaint.setIdComplaint( 1L );
        return Stream.of( Arguments.of( requestTypeComplaint));
    }

    @Description("Добавление под жалобы(POST)")
    @DisplayName("Добавление под жалобы(POST)")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/6.%20%D0%A1omplaint/saveTypeComplaint")
    @ParameterizedTest
    @MethodSource("getTypeComplaint")
    public void testFirst(RequestTypeComplaint requestTypeComplaint){
        try{
            RestAssured.baseURI = PATH;
            Response response = given().header( authorization, bearer )
                                       .when()
                                       .contentType( ContentType.JSON )
                                       .body( requestTypeComplaint )
                                       .post("complaints/typecomplaint");
            response.then()
                    .statusCode( 201 );
            Allure.addAttachment("Результат:", TYPE, response.andReturn().asString() );
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", TYPE, ex.getMessage() );
        }
    }


}
