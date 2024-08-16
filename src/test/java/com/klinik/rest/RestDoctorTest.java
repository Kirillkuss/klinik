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

    private static final String PATH = "http://localhost:8082";
    private static final String TYPE = "application/json";
    private final String authorization = "Authorization";
    private static String token;
    private static String bearer = "Bearer " + token;

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
                                       .post("/auth/login");                         
            response.then().statusCode(200);
            ObjectMapper objectMapper = new ObjectMapper();
            AuthResponse authResponse = objectMapper.readValue(response.asString(), AuthResponse.class);
            token = authResponse.getToken();
            Allure.addAttachment("token:", TYPE, token );
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", TYPE, ex.getMessage() );
        }
    }
    
    @Description("Получение количества врачей (GET)")
    @DisplayName("Получение количества врачей (GET)")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/1.%20Doctors/getCountDoctors")
    @RepeatedTest( 2 )
    @TmsLink("TEST-3545")
    public void testGetDoctorCounts() throws Exception {
        try{
            RestAssured.baseURI = PATH;
            Response response = given().header( authorization, bearer ).when().get("/doctors/counts");
            response.then().statusCode(200);
            Allure.addAttachment("Результат:", TYPE, response.andReturn().asString() );
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", TYPE, ex.getMessage() );
        }
    }

    @Description("Получение списка врачей (POST)")
    @DisplayName("Получение списка врачей (POST)")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/1.%20Doctors/getLazyDoctors")
    @ParameterizedTest
    @CsvSource({"1, 14", "486, 50", "851, 12"})
    public void testGetDocumentsLazy( int page, int size ){
        try{
            RestAssured.baseURI = PATH;
            Response response = given().header( authorization, bearer )
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
 
    @Description("Добавить врача")
    @DisplayName("Добавить врача (POST)")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/1.%20Doctors/addDoctor")
    @ParameterizedTest
    @MethodSource("getParams")
    public void testAddDoctor( Doctor doctor ){
        try{
            RestAssured.baseURI = PATH;
            Response response = given().header( authorization, bearer ).when()
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


    @Description("Получение врачей по ФИО")
    @DisplayName("Получение врачей по ФИО (GET)")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/1.%20Doctors/findByFIO")
    @TmsLink("TEST-3545")
    @ParameterizedTest
    @CsvSource({"Test, 1, 14", "Mouse, 2, 5", "Elk, 8, 10"})
    public void testGetByFIO(String word, int page, int size ) {
        try{
            RestAssured.baseURI = PATH;
            Response response = given().header( authorization, bearer )
                                       .queryParam("word", word)
                                       .queryParam("page", page)
                                       .queryParam("size", size)
                                       .when()
                                       .get("/doctors/fio" );
            response.then().statusCode(200);
            Allure.addAttachment("Результат:", TYPE, response.andReturn().asString() );
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", TYPE, ex.getMessage() );
        }
    } 
} 
