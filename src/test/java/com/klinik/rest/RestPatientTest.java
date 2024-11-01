package com.klinik.rest;

import static io.restassured.RestAssured.given;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import com.klinik.entity.Patient;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.TmsLink;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@Owner(value = "Barysevich K. A.")
@Epic(value = "Тестирование АПИ - PatientController")
@DisplayName("Тестирование АПИ - PatientController")
public class RestPatientTest {

    private static String PATH;
    private static String TYPE;
    private static String rezult;
    private static String error;
    private static String JSESSIONID;
    public static  String leadTime;
    
    @BeforeAll
    @DisplayName("Получение входных параметров для выполения запросов") 
    public static void setUpClass() {
        JSESSIONID = RestSession.getSessionId();
        PATH       = RestSession.PATH;
        TYPE       = RestSession.TYPE;
        rezult     = RestSession.rezult;
        error      = RestSession.error;
        leadTime   = RestSession.leadTime;
    }

    private static String query = "SELECT d.id_document \n" + 
                                  "FROM Document d \n" + 
                                  "LEFT JOIN Patient p ON d.id_document = p.document_id \n" + 
                                  "WHERE p.document_id IS NULL;";

    @Description("Получение всех пациентов (GET)")
    @DisplayName("Получение всех пациентов  (GET)")
    @Link(name = "swagger", url = "http://localhost:8082/web/swagger-ui/index.html#/2.%20Patient/getAllPatients")
    @RepeatedTest( 1 )
    @TmsLink("TEST-3545")
    public void testGetAllPatients() throws Exception {
        try{
            RestAssured.baseURI = PATH;
            Response response = given().cookie("JSESSIONID", JSESSIONID )
                                       .when()
                                       .contentType( ContentType.JSON )
                                       .get("/patients/all");
                     response.then().statusCode(200);
            Allure.addAttachment( rezult, TYPE, response.andReturn().asString() );
            Allure.addAttachment( leadTime, TYPE, String.valueOf( response.time() + " ms."));
        }catch( Exception ex ){
            Allure.addAttachment( error, TYPE, ex.getMessage() );
        }
    }

    @Description("Поиск пациента по ФИО или номеру телефона(GET)")
    @DisplayName("Поиск пациента по ФИО или номеру телефона (GET)")
    @Link(name = "swagger", url = "http://localhost:8082/web/swagger-ui/index.html#/2.%20Patient/findByWord")
    @ParameterizedTest
    @CsvSource({"Анатольевич", "Алексей", "+375257200778"})
    public void testGetLazyDocuments( String word ) throws Exception {
        try{
            RestAssured.baseURI = PATH;
            Response response = given().cookie("JSESSIONID", JSESSIONID )
                                       .queryParam("word", word)
                                       .when()
                                       .contentType( ContentType.JSON )
                                       .get("/patients/find/{word}", word);
                     response.then().statusCode(200);
            Allure.addAttachment( rezult, TYPE, response.andReturn().asString() );
            Allure.addAttachment( leadTime, TYPE, String.valueOf( response.time() + " ms."));
        }catch( Exception ex ){
            Allure.addAttachment( error, TYPE, ex.getMessage() );
        }
    }

    @Description("Поиск пациента по ФИО или номеру телефона(GET)")
    @DisplayName("Поиск пациента по ФИО или номеру телефона (GET)")
    @Link(name = "swagger", url = "http://localhost:8082/web/swagger-ui/index.html#/2.%20Patient/findByWord")
    @ParameterizedTest
    @CsvSource({"1, 10", "4, 5", "9, 10"})
    public void testGetLazyDocuments( int page, int size ) throws Exception {
        try{
            RestAssured.baseURI = PATH;
            Response response = given().cookie("JSESSIONID", JSESSIONID )
                                       .queryParam("page", page)
                                       .queryParam("size", size)
                                       .when()
                                       .contentType( ContentType.JSON )
                                       .get("/patients/list/{page}",page);
                     response.then().statusCode(200);
            Allure.addAttachment( rezult, TYPE, response.andReturn().asString() );
            Allure.addAttachment( leadTime, TYPE, String.valueOf( response.time() + " ms."));
        }catch( Exception ex ){
            Allure.addAttachment( error, TYPE, ex.getMessage() );
        }
    }

    @DisplayName("Параметры для тестирования")
    public static Stream<Arguments> getParamsPatient() throws Exception{
        List<Long> listLong = RestSession.getStremValue( query, "id_document");
        if ( listLong.stream().count() < 2 ){
            addDocument();
        }
        return Stream.of( Arguments.of( RestSession.getPatient(), listLong.stream().findFirst().orElseThrow()));
    }

    @Description("Добавить пациента (POST)")
    @DisplayName("Добавить пациента (POST)")
    @Link(name = "swagger", url = "http://localhost:8082/web/swagger-ui/index.html#/2.%20Patient/addPatient")
    @ParameterizedTest
    @MethodSource("getParamsPatient")
    public void testAddPatient( Patient patient, Long idDocument ){
        try{
            RestAssured.baseURI = PATH;
            Response response = given().cookie("JSESSIONID", JSESSIONID )
                                       .queryParam("id", idDocument) 
                                       .when()
                                       .contentType( ContentType.JSON )
                                       .body( patient )
                                       .post("/patients/add");
                     response.then().statusCode( 201 );
            Allure.addAttachment( rezult, TYPE, response.andReturn().asString() );
            Allure.addAttachment( leadTime, TYPE, String.valueOf( response.time() + " ms."));
        }catch( Exception ex ){
            Allure.addAttachment( error, TYPE, ex.getMessage() );
        }

    }

    @DisplayName("Добавить документ")
    private static void addDocument(){
        try{
            RestAssured.baseURI = PATH;
            Response response = given().cookie("JSESSIONID", JSESSIONID )
                                       .when()
                                       .contentType( ContentType.JSON )
                                       .body( RestSession.getDocument() )
                                       .post("/documents/add");
                     response.then().statusCode( 201 );
            Allure.addAttachment( rezult, TYPE, response.andReturn().asString() );
            Allure.addAttachment( leadTime, TYPE, String.valueOf( response.time() + " ms."));
        }catch( Exception ex ){
            Allure.addAttachment( error, TYPE, ex.getMessage() );
        }
    }

}
