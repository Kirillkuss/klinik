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
import com.klinik.entity.Document;
import com.klinik.entity.Patient;
import static org.instancio.Select.field;
import org.instancio.Instancio;
import org.instancio.Select;
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
@Epic(value = "Тестирование АПИ - DocumentController")
@DisplayName("Тестирование АПИ - DocumentController")
public class RestDocumentTest {

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

    @Description("Получение всех документов (GET)")
    @DisplayName("Получение всех документов (GET)")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/3.%20Documents/getAllDocuments")
    @RepeatedTest( 1 )
    @TmsLink("TEST-3545")
    public void testGetAllDocuments() throws Exception {
        try{
            RestAssured.baseURI = PATH;
            Response response = given().header( authorization, bearer )
                                       .when()
                                       .contentType( ContentType.JSON )
                                       .get("/documents/list");
                     response.then().statusCode(200);
            Allure.addAttachment( rezult, TYPE, response.andReturn().asString() );
            Allure.addAttachment( leadTime, TYPE, String.valueOf( response.time() + " ms."));
        }catch( Exception ex ){
            Allure.addAttachment( error, TYPE, ex.getMessage() );
        }
    }

    @Description("Получение всех документов (GET - Lazy)")
    @DisplayName("олучение всех документов  (GET - Lazy)")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/3.%20Documents/getLazyDocumentt")
    @ParameterizedTest
    @CsvSource({" 1, 14", "2, 5", "8, 10"})
    public void testGetLazyDocuments( int page, int size ) throws Exception {
        try{
            RestAssured.baseURI = PATH;
            Response response = given().header( authorization, bearer )
                                       .queryParam("page", page)
                                       .queryParam("size", size)
                                       .when()
                                       .contentType( ContentType.JSON )
                                       .get("/documents/list");
                     response.then().statusCode(200);
            Allure.addAttachment( rezult, TYPE, response.andReturn().asString() );
            Allure.addAttachment( leadTime, TYPE, String.valueOf( response.time() + " ms."));
        }catch( Exception ex ){
            Allure.addAttachment( error, TYPE, ex.getMessage() );
        }
    }

    @Description("Получение документов по слову (GET)")
    @DisplayName("Получение документов по слову (GET)")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/3.%20Documents/getLazyDocumentt")
    @ParameterizedTest
    @CsvSource({"757124751", "914-398-570-25"})
    public void testGetFindWord( String word ) throws Exception {
        try{
            RestAssured.baseURI = PATH;
            Response response = given().header( authorization, bearer )
                                       .queryParam("word", word )
                                       .when()
                                       .contentType( ContentType.JSON )
                                       .get("/documents/find/{word}", word);
                     response.then().statusCode(200);
            Allure.addAttachment( rezult, TYPE, response.andReturn().asString() );
            Allure.addAttachment( leadTime, TYPE, String.valueOf( response.time() + " ms."));
        }catch( Exception ex ){
            Allure.addAttachment( error, TYPE, ex.getMessage() );
        }
    }

    @DisplayName("Параметры для тестирования")
    public static Stream<Arguments> getParams() throws Exception{
        return Stream.of( Arguments.of( RestToken.getDocument() ));
    }

    @Description("Добавить документ")
    @DisplayName("Добавить документ (POST)")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/3.%20Documents/addDocument")
    @ParameterizedTest
    @MethodSource("getParams")
    public void testAddDocument( Document document ){
        try{
            RestAssured.baseURI = PATH;
            Response response = given().header( authorization, bearer )
                                       .when()
                                       .contentType( ContentType.JSON )
                                       .body( document )
                                       .post("/documents/add");
                     response.then().statusCode( 201 );
            Allure.addAttachment( rezult, TYPE, response.andReturn().asString() );
            Allure.addAttachment( leadTime, TYPE, String.valueOf( response.time() + " ms."));
        }catch( Exception ex ){
            Allure.addAttachment( error, TYPE, ex.getMessage() );
        }
    }
  
}
