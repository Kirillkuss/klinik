package com.klinik.rest;

import static io.restassured.RestAssured.given;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import com.klinik.entity.Doctor;
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

@Slf4j
@Owner(value = "Barysevich K. A.")
@Epic(value = "Тестирование АПИ - DoctorController")
@DisplayName("Тестирование АПИ - DoctorControllerr")
public class RestDoctorTest {

    private static String PATH;
    private static String TYPE;
    private static String rezult;
    private static String error;
    private static String JSESSIONID;
    public static  String leadTime;
    
    @BeforeAll
    @DisplayName("Получение входных параметров для выполения запросов") 
    public static void setUpClass() {
        JSESSIONID    = RestToken.getSessionId();
        PATH          = RestToken.PATH;
        TYPE          = RestToken.TYPE;
        rezult        = RestToken.rezult;
        error         = RestToken.error;
        leadTime      = RestToken.leadTime;
    }
    
    @Description("Получение количества врачей (GET)")
    @DisplayName("Получение количества врачей (GET)")
    @Link(name = "swagger", url = "http://localhost:8082/web/swagger-ui/index.html#/1.%20Doctors/getCountDoctors")
    @RepeatedTest( 2 )
    @TmsLink("TEST-3545")
    public void testGetDoctorCounts() throws Exception {
        try{
            RestAssured.baseURI = PATH;
            Response response = given().cookie("JSESSIONID", JSESSIONID )
                                       .when()
                                       .get("/web/doctors/counts" );
            response.then()
                    .statusCode(200);
            Allure.addAttachment( rezult, TYPE, response.andReturn().asString() );
            Allure.addAttachment( leadTime, TYPE, String.valueOf( response.time() + " ms."));
        }catch( Exception ex ){
            Allure.addAttachment( error, TYPE, ex.getMessage() );
        }
    }

    @Description("Получение списка врачей (POST)")
    @DisplayName("Получение списка врачей (POST)")
    @Link(name = "swagger", url = "http://localhost:8082/web/swagger-ui/index.html#/1.%20Doctors/getLazyDoctors")
    @ParameterizedTest
    @CsvSource({"1, 14", "486, 50", "851, 12"})
    public void testGetDocumentsLazy( int page, int size ){
        try{
            RestAssured.baseURI = PATH;
            Response response = given().cookie("JSESSIONID", JSESSIONID )
                                       .queryParam("page", page)
                                       .queryParam("size", size)
                                       .when()
                                       .post("/web/doctors/lazy");
                     response.then().statusCode(200);
            Allure.addAttachment( rezult, TYPE, response.andReturn().asString() );
            Allure.addAttachment( leadTime, TYPE, String.valueOf( response.time() + " ms."));
        }catch( Exception ex ){
            Allure.addAttachment( error, TYPE, ex.getMessage() );
        }
    }

    @DisplayName("Параметры для тестирования")
    public static Stream<Arguments> getParams() throws Exception{                 
        return Stream.of( Arguments.of( RestToken.getDoctor() ));
    }
 
    @Description("Добавить врача")
    @DisplayName("Добавить врача (POST)")
    @Link(name = "swagger", url = "http://localhost:8082/web/swagger-ui/index.html#/1.%20Doctors/addDoctor")
    @ParameterizedTest
    @MethodSource("getParams")
    public void testAddDoctor( Doctor doctor ){
        try{
            RestAssured.baseURI = PATH;
            Response response = given().cookie("JSESSIONID", JSESSIONID )
                                       .contentType( ContentType.JSON )
                                       .body( doctor )
                                       .post("/web/doctors/add");
                     response.then().statusCode(200);
            Allure.addAttachment( rezult, TYPE, response.andReturn().asString() );
            Allure.addAttachment( leadTime, TYPE, String.valueOf( response.time() + " ms."));
        }catch( Exception ex ){
            Allure.addAttachment( error, TYPE, ex.getMessage() );
        }
    }

    @Description("Получение врачей по ФИО")
    @DisplayName("Получение врачей по ФИО (GET)")
    @Link(name = "swagger", url = "http://localhost:8082/web/swagger-ui/index.html#/1.%20Doctors/findByFIO")
    @TmsLink("TEST-3545")
    @ParameterizedTest
    @CsvSource({"SECOND, 1, 14", "Mouse, 1, 5", "TEST, 1, 10"})
    public void testGetByFIO(String word, int page, int size ) {
        try{
            RestAssured.baseURI = PATH;
            Response response = given().cookie("JSESSIONID", JSESSIONID )
                                       .queryParam("word", word)
                                       .queryParam("page", page)
                                       .queryParam("size", size)
                                       .when()
                                       .get("/web/doctors/fio/{word}", word);
                     response.then().statusCode(200);           
            Allure.addAttachment( rezult, TYPE, response.andReturn().asString() );
            Allure.addAttachment( leadTime, TYPE, String.valueOf( response.time() + " ms."));
        }catch( Exception ex ){
            Allure.addAttachment( error, TYPE, ex.getMessage() );
        }
    } 
} 
