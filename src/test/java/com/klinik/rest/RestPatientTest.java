package com.klinik.rest;

import static io.restassured.RestAssured.given;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import com.klinik.entity.Document;
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
    @Description("Получение всех пациентов (GET)")
    @DisplayName("Получение всех пациентов  (GET)")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/2.%20Patient/getAllPatients")
    @RepeatedTest( 1 )
    @TmsLink("TEST-3545")
    public void testGetAllPatients() throws Exception {
        try{
            RestAssured.baseURI = PATH;
            Response response = given().header( authorization, bearer )
                                       .when()
                                       .contentType( ContentType.JSON )
                                       .get("/patients/all");
                     response.then().statusCode(200);
            Allure.addAttachment( rezult, TYPE, response.andReturn().asString() );
            Allure.addAttachment( leadTime,  TYPE, String.valueOf( response.time() + " ms."));
        }catch( Exception ex ){
            Allure.addAttachment( error, TYPE, ex.getMessage() );
        }
    }

    @Description("Поиск пациента по ФИО или номеру телефона(GET)")
    @DisplayName("Поиск пациента по ФИО или номеру телефона (GET)")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/2.%20Patient/findByWord")
    @ParameterizedTest
    @CsvSource({"ZCRAOTDK", "TVMW", "OMSBVELYJ"})
    public void testGetLazyDocuments( String word ) throws Exception {
        try{
            RestAssured.baseURI = PATH;
            Response response = given().header( authorization, bearer )
                                       .queryParam("word", word)
                                       .when()
                                       .contentType( ContentType.JSON )
                                       .get("/patients/find");
                     response.then().statusCode(200);
            Allure.addAttachment( rezult, TYPE, response.andReturn().asString() );
            Allure.addAttachment( leadTime,  TYPE, String.valueOf( response.time() + " ms."));
        }catch( Exception ex ){
            Allure.addAttachment( error, TYPE, ex.getMessage() );
        }
    }

    @Description("Поиск пациента по ФИО или номеру телефона(GET)")
    @DisplayName("Поиск пациента по ФИО или номеру телефона (GET)")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/2.%20Patient/findByWord")
    @ParameterizedTest
    @CsvSource({"1, 10", "4, 5", "9, 10"})
    public void testGetLazyDocuments( int page, int size ) throws Exception {
        try{
            RestAssured.baseURI = PATH;
            Response response = given().header( authorization, bearer )
                                       .queryParam("page", page)
                                       .queryParam("size", size)
                                       .when()
                                       .contentType( ContentType.JSON )
                                       .get("/patients/list");
                     response.then().statusCode(200);
            Allure.addAttachment( rezult, TYPE, response.andReturn().asString() );
            Allure.addAttachment( leadTime,  TYPE, String.valueOf( response.time() + " ms."));
        }catch( Exception ex ){
            Allure.addAttachment( error, TYPE, ex.getMessage() );
        }
    }

    @DisplayName("Параметры для тестирования")
    public static Stream<Arguments> getParamsPatient() throws Exception{
        List<Long> listLong = getIdDocuments();
        if ( listLong.stream().count() < 2 ){
            addDocument();
        }
        Patient patient = Instancio.of(Patient.class).ignore(Select.field( Patient::getIdPatient )).create();
        patient.setIdPatient( -1L );
        return Stream.of( Arguments.of( patient, listLong.stream().findFirst().orElseThrow()));
    }

    @Description("Добавить пациента (POST)")
    @DisplayName("Добавить пациента (POST)")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/2.%20Patient/addPatient")
    @ParameterizedTest
    @MethodSource("getParamsPatient")
    public void testAddPatient(  Patient patient, Long idDocument ){
        try{
            RestAssured.baseURI = PATH;
            Response response = given().header( authorization, bearer )
                                       .queryParam("id", idDocument) 
                                       .when()
                                       .contentType( ContentType.JSON )
                                       .body( patient )
                                       .post("/patients/add");
                     response.then().statusCode( 201 );
            Allure.addAttachment( rezult, TYPE, response.andReturn().asString() );
            Allure.addAttachment( leadTime,  TYPE, String.valueOf( response.time() + " ms."));
        }catch( Exception ex ){
            Allure.addAttachment( error, TYPE, ex.getMessage() );
        }

    }

    @DisplayName("Получение доступных ИД документов")
    private static List<Long> getIdDocuments(){
        List<Long> listLong = new ArrayList<>();
        String url = "jdbc:postgresql://localhost:5432/Klinika";
        String user = "postgres";
        String password = "admin";
        String query = "SELECT d.id_document \n" + 
                        "FROM Document d \n" + 
                        "LEFT JOIN Patient p ON d.id_document = p.document_id \n" + 
                        "WHERE p.document_id IS NULL;";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Long id = rs.getLong("id_document");
                listLong.add(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listLong;
    }

    @DisplayName("Добавить документ")
    private static void addDocument(){
        Document document = Instancio.of(Document.class).ignore(Select.field( Document::getIdDocument )).create();
        document.setIdDocument( -1L );
        try{
            RestAssured.baseURI = PATH;
            Response response = given().header( authorization, bearer )
                                       .when()
                                       .contentType( ContentType.JSON )
                                       .body( document )
                                       .post("/documents/add");
                     response.then().statusCode( 201 );
            Allure.addAttachment( rezult, TYPE, response.andReturn().asString() );
            Allure.addAttachment( leadTime,  TYPE, String.valueOf( response.time() + " ms."));
        }catch( Exception ex ){
            Allure.addAttachment( error, TYPE, ex.getMessage() );
        }
    }





}
