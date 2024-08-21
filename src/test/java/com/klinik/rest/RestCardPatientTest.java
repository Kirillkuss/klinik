package com.klinik.rest;

import static io.restassured.RestAssured.given;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import com.klinik.entity.CardPatient;
import com.klinik.request.CoplaintRequest;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

//@Disabled
@Owner(value = "Barysevich K. A.")
@Epic(value = "Тестирование АПИ - CardPatientController")
@DisplayName("Тестирование АПИ - CardPatientController")
public class RestCardPatientTest {

    private static String PATH;
    private static String TYPE;
    private static String authorization;
    private static String rezult;
    private static String error;
    private static String bearer;
    public static  String leadTime;
    
    @BeforeAll
    @DisplayName("Получение входных параметров для выполения запросов") 
    public static void setUpClass() {
        bearer        = RestToken.getToken();
        PATH          = RestToken.PATH;
        TYPE          = RestToken.TYPE;
        authorization = RestToken.authorization;
        rezult        = RestToken.rezult;
        error         = RestToken.error;
        leadTime      = RestToken.leadTime;
    }

    @DisplayName("Параметры для тестирования")
    public static Stream<Arguments> getParamsCard() throws Exception{
        CardPatient cardPatient = new CardPatient();
        cardPatient.setIdCardPatient( -1L );
        cardPatient.setAllergy( true );
        cardPatient.setDiagnosis( "Рассеянный склероз");
        cardPatient.setNote("Есть аллергия на цитрамон");
        cardPatient.setСonclusion( "Болен");
        return Stream.of( Arguments.of( cardPatient, 3L ));
    }

    @Description("Добавить карту пациента (POST)")
    @DisplayName("Добавить пациента (POST)")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/4.%20Card%20Patient/saveCardPatient")
    //@ParameterizedTest
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
                     response.then().statusCode( 200 );
            Allure.addAttachment( rezult, TYPE, response.andReturn().asString() );
            Allure.addAttachment( leadTime,  TYPE, String.valueOf( response.time() + " ms."));
        }catch( Exception ex ){
            Allure.addAttachment( error, TYPE, ex.getMessage() );
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
            Allure.addAttachment( rezult, TYPE, response.andReturn().asString() );
            Allure.addAttachment( leadTime,  TYPE, String.valueOf( response.time() + " ms."));
        }catch( Exception ex ){
            Allure.addAttachment( error, TYPE, ex.getMessage() );
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
                                       .get("/card-patinets/patient/{id}", id);
                     response.then().statusCode(200);
            Allure.addAttachment( rezult, TYPE, response.andReturn().asString() );
            Allure.addAttachment( leadTime,  TYPE, String.valueOf( response.time() + " ms."));
        }catch( Exception ex ){
            Allure.addAttachment( error, TYPE, ex.getMessage() );
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
                                       .queryParam("id", id )
                                       .when()
                                       .contentType( ContentType.JSON )
                                       .get("/card-patinets/card/{id}", id );
                     response.then().statusCode(200);
            Allure.addAttachment( rezult, TYPE, response.andReturn().asString() );
            Allure.addAttachment( leadTime,  TYPE, String.valueOf( response.time() + " ms."));
        }catch( Exception ex ){
            Allure.addAttachment( error, TYPE, ex.getMessage() );
        }
    }

    @Description("Добавление жалобы пациенту (POST)")
    @DisplayName("Добавление жалобы пациенту (POST)")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/4.%20Card%20Patient/saveComplaintToCardPatient")
    //@ParameterizedTest
    @CsvSource({"1, 1", "1, 2"})
    public void testAddCoplaintToCard( Long idCard, Long idCoplaint  ) throws Exception {
        CoplaintRequest coplaintRequest = new CoplaintRequest(idCard, idCoplaint);
        try{
            RestAssured.baseURI = PATH;
            Response response = given().header( authorization, bearer )
                                       .when()
                                       .contentType( ContentType.JSON )
                                       .body( coplaintRequest )
                                       .post("/card-patinets/complaint");
                     response.then().statusCode(201);
            Allure.addAttachment( rezult, TYPE, response.andReturn().asString() );
            Allure.addAttachment( leadTime,  TYPE, String.valueOf( response.time() + " ms."));
        }catch( Exception ex ){
            Allure.addAttachment( error, TYPE, ex.getMessage() );
        }
    }
 
}
