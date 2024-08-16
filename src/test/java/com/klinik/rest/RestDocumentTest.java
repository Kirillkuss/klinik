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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klinik.entity.Document;
import com.klinik.request.AuthRequest;
import com.klinik.response.AuthResponse;
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

    private static final String PATH = "http://localhost:8082";
    private static final String TYPE = "application/json";
    private final String authorization = "Authorization";
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

    @Description("Получение всех документов (GET)")
    @DisplayName("олучение всех документов  (GET)")
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
            Allure.addAttachment("Результат:", TYPE, response.andReturn().asString() );
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", TYPE, ex.getMessage() );
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
            Allure.addAttachment("Результат:", TYPE, response.andReturn().asString() );
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", TYPE, ex.getMessage() );
        }
    }

    @Description("Получение документов по слову (GET)")
    @DisplayName("Получение документов по слову (GET)")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/3.%20Documents/getLazyDocumentt")
    @ParameterizedTest
    @CsvSource({"UWTBOOVPA", "BIPMHMWDEJ"})
    public void testGetFindWord( String word ) throws Exception {
        try{
            RestAssured.baseURI = PATH;
            Response response = given().header( authorization, bearer )
                                       .queryParam("word", word )
                                       .when()
                                       .contentType( ContentType.JSON )
                                       .get("/documents/find");
            response.then().statusCode(200);
            Allure.addAttachment("Результат:", TYPE, response.andReturn().asString() );
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", TYPE, ex.getMessage() );
        }
    }

    @DisplayName("Параметры для тестирования")
    public static Stream<Arguments> getParams() throws Exception{
        Document document = Instancio.of(Document.class).ignore(Select.field( Document::getIdDocument )).create();
        document.setIdDocument( -1L );
        return Stream.of( Arguments.of( document));
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
            response.then()
                    .statusCode( 201 );
            Allure.addAttachment("Результат:", TYPE, response.andReturn().asString() );
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", TYPE, ex.getMessage() );
        }
    }
  
}
