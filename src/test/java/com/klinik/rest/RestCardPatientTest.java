package com.klinik.rest;

import static io.restassured.RestAssured.given;

import java.util.List;
import java.util.stream.Stream;

import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.klinik.entity.CardPatient;
import com.klinik.entity.Patient;
import com.klinik.request.AuthRequest;
import com.klinik.response.AuthResponse;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.TmsLink;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

//@Disabled
@Owner(value = "Barysevich K. A.")
@Epic(value = "Тестирование АПИ - CardPatientController")
@DisplayName("Тестирование АПИ - CardPatientController")
public class RestCardPatientTest {

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

    @DisplayName("Параметры для тестирования")
    public static Stream<Arguments> getParamsCard() throws Exception{
        CardPatient cardPatient = new CardPatient();
        cardPatient.setIdCardPatient( -1L );
        cardPatient.setAllergy( true );
        cardPatient.setDiagnosis( "Рассеянный склероз");
        cardPatient.setNote("Есть аллергия на цитрамон");
        cardPatient.setСonclusion( "Болен");
        return Stream.of( Arguments.of( cardPatient, 29L ));
    }

    @Description("Добавить карту пациента (POST)")
    @DisplayName("Добавить пациента (POST)")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/4.%20Card%20Patient/saveCardPatient")
   // @ParameterizedTest
    @MethodSource("getParamsCard")
    public void testAddCardPatient( CardPatient cardPatient, Long idPatient ){
        try{
            RestAssured.baseURI = PATH;
            Response response = given().header( authorization, bearer )
                                       .queryParam("idPatient", idPatient ) 
                                       .when()
                                       .contentType( ContentType.JSON )
                                       .body( cardPatient )
                                       .post("/card-patinets/add");
            response.then()
                    .statusCode( 200 );
            Allure.addAttachment("Результат:", TYPE, response.andReturn().asString() );
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", TYPE, ex.getMessage() );
        }

    }


    
    @Description("Поиск карты пациента по ид пациента (GET)")
    @DisplayName("Поиск карты пациента по ид пациента (GET)")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/2.%20Patient/getAllPatients")
    @ParameterizedTest
    @CsvSource({"10", "8"})
    public void testGetCardPatientId( Long id ) throws Exception {
        try{
            RestAssured.baseURI = PATH;
            Response response = given().header( authorization, bearer )
                                       .queryParam("id", id)
                                       .when()
                                       .contentType( ContentType.JSON )
                                       .get("/card-patinets/patient/{id}");
            response.then().statusCode(200);
            Allure.addAttachment("Результат:", TYPE, response.andReturn().asString() );
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", TYPE, ex.getMessage() );
        }
    }

    @Description("Поиск карты пациента по документу пациента (GET)")
    @DisplayName("Поиск карты пациента по документу пациента  (GET)")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/2.%20Patient/getAllPatients")
    @ParameterizedTest
    @CsvSource({"BMJZI", "JGGKJZHRN"})
    public void testGetCardPatientByDocument( String word ) throws Exception {
        try{
            RestAssured.baseURI = PATH;
            Response response = given().header( authorization, bearer )
                                       .queryParam("word", word)
                                       .when()
                                       .contentType( ContentType.JSON )
                                       .get("/card-patinets/document");
            response.then().statusCode(200);
            Allure.addAttachment("Результат:", TYPE, response.andReturn().asString() );
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", TYPE, ex.getMessage() );
        }
    }

    @Description("Поиск карты пациента по ид карты (GET)")
    @DisplayName("Поиск карты пациента по ид карты (GET)")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/2.%20Patient/getAllPatients")
    @ParameterizedTest
    @CsvSource({"1", "3"})
    public void testGetCardId( Long id ) throws Exception {
        try{
            RestAssured.baseURI = PATH;
            Response response = given().header( authorization, bearer )
                                       .queryParam("id", id)
                                       .when()
                                       .contentType( ContentType.JSON )
                                       .get("/card-patinets/сard/{id}");
            response.then().statusCode(200);
            Allure.addAttachment("Результат:", TYPE, response.andReturn().asString() );
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", TYPE, ex.getMessage() );
        }
    }





    
}
