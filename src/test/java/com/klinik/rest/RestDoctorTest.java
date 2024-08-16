package com.klinik.rest;

import static io.restassured.RestAssured.given;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klinik.entity.Doctor;
import com.klinik.request.AuthRequest;
import com.klinik.response.AuthResponse;
import groovy.util.logging.Slf4j;
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

@Slf4j
//@Disabled
@Owner(value = "Barysevich K. A.")
@Epic(value = "Тестирование АПИ - DoctorController")
@DisplayName("Тестирование АПИ - DoctorControllerr")
public class RestDoctorTest {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private static final String PATH = "http://localhost:8082";
    private static final String TYPE = "application/json";
    private static String token;

    @BeforeAll 
    public static void tearDown() {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setLogin( "admin");
        authRequest.setPassword("admin");
        try{
            RestAssured.baseURI = PATH;
            Response response = given().contentType( TYPE ).body( authRequest ).when().post("/auth/login");
            response.then()
                    .statusCode(200);
            AuthResponse authResponse = objectMapper.readValue(response.asString(), AuthResponse.class);
            token = authResponse.getToken();
            Allure.addAttachment("token:", TYPE, token );
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", TYPE, ex.getMessage() );
        }

    }
    
    @Feature("Получение количества врачей")
    @Description("Получение количества врачей")
    @DisplayName("Вызов метода GET: http://localhost:8082/doctors/counts")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/1.%20Doctors/getCountDoctors")
    @RepeatedTest( 1 )
    @TmsLink("TEST-3545")
    public void testGetDoctorCounts() throws Exception {
        try{
            RestAssured.baseURI = PATH;
            Response response = given().header("Authorization", "Bearer " + token).when().get("/doctors/counts");
            response.then().statusCode(200);
            Allure.addAttachment("Результат:", TYPE, response.andReturn().asString() );
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", TYPE, ex.getMessage() );
        }
    }

    @Feature("Получение списка врачей (LAZY)")
    @Description("Получение списка врачей (LAZY)")
    @DisplayName("Вызов метода POST: http://localhost:8082/doctors/lazy?page=1&size=12")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/1.%20Doctors/getLazyDoctors")
    @ParameterizedTest
    @CsvSource({"1, 14", "486, 50", "851, 12"})
    public void testGetDocumentsLazy( int page, int size ){
        try{
            RestAssured.baseURI = PATH;
            Response response = given().header("Authorization", "Bearer " + token)
                                       .queryParam("page", page)
                                       .queryParam("size", size)
                                       .when()
                                       .post("/doctors/lazy");
            response.then().statusCode(200);
            Allure.addAttachment("Результат:", TYPE, response.andReturn().asString() );
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", TYPE, ex.getMessage() );
        }
    }

    @DisplayName("Параметры для тестирования")
    public static Stream<Arguments> getParams() throws Exception{
        return Stream.of( Arguments.of( new Doctor( -1L, "GERP", "DERT", "ERYT") ) );
    }
 
    @Feature("Добавить врача")
    @Description("Добавить врача")
    @DisplayName("Вызов метода POST: http://localhost:8082/doctors/add")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/1.%20Doctors/addDoctor")
    @ParameterizedTest
    @MethodSource("getParams")
    public void testAddDoctor( Doctor doctor ){
        try{
            RestAssured.baseURI = PATH;
            Response response = given().header("Authorization", "Bearer " + token).when()
                                       .contentType(ContentType.JSON)
                                       .body( doctor )
                                       .post("/doctors/add");
            response.then()
                    .statusCode(200);
            Allure.addAttachment("Результат:", TYPE, response.andReturn().asString() );
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", TYPE, ex.getMessage() );
        }
    }

    @Feature("Получение врачей по ФИО")
    @Description("Получение врачей по ФИО")
    @DisplayName("Вызов метода GET: http://localhost:8082/doctors/fio/{word}{page}{size}?word=Test&page=1&size=10")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/1.%20Doctors/findByFIO")
    @TmsLink("TEST-3545")
    @ParameterizedTest
    @CsvSource({"Test, 1, 14", "Mouse, 2, 5", "Elk, 8, 10"})
    public void testGetByFIO(String word, int page, int size ) {
        try{
            RestAssured.baseURI = PATH;
            Response response = given().header("Authorization", "Bearer " + token)
                                       .queryParam("word", word)
                                       .queryParam("page", page)
                                       .queryParam("size", size)
                                       .when()
                                       .get("/doctors/fio" );
            response.then()
                    .statusCode(200);
            Allure.addAttachment("Результат:", TYPE, response.andReturn().asString() );
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", TYPE, ex.getMessage() );
        }
    } 
} 
