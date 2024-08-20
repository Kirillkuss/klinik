package com.klinik.rest;

import static io.restassured.RestAssured.given;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klinik.entity.RehabilitationSolution;
import com.klinik.request.AuthRequest;
import com.klinik.response.AuthResponse;
import static org.hamcrest.Matchers.lessThan;
import java.util.stream.Stream;
import org.instancio.Instancio;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@Owner(value = "Barysevich K. A.")
@Epic(value = "Тестирование АПИ - RehabilitationSolutionController")
@DisplayName("Тестирование АПИ - RehabilitationSolutionController")
public class RestRehabilitationSolutionTest {

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

    @Description("Список всех Реабилитационных лечений ( GET )")
    @DisplayName("Список всех Реабилитационных лечений ( GET )")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/9.%20Rehabilitation%20Treatment/getAllRehabilitationSolution")
    @RepeatedTest(2)
    public void testGetListRehabilitationSolutionAll(){
        try{
            RestAssured.baseURI = PATH;
            Response response = given().log()
                                       .all()
                                       .header(authorization, bearer)
                                       .when()
                                       .contentType(ContentType.JSON)
                                       .get("/rehabilitation-treatments/all");
            response.then()
                    .log()
                    .body()
                    .time( lessThan(2000L ))
                    .statusCode( 200 );
            Allure.addAttachment("Результат:", TYPE, response.andReturn().asString() );
            Allure.addAttachment("Время выполнения:",  TYPE, String.valueOf( response.time() + " ms."));
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", TYPE, ex.getMessage() );
        }
    }

    @Description("Список всех Реабилитационных лечений ( GET )")
    @DisplayName("Список всех Реабилитационных лечений ( GET )")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/9.%20Rehabilitation%20Treatment/getAllRehabilitationSolution")
    @ParameterizedTest
    @CsvSource({"Кинезитерапия1", "Кинезитерапия6"})
    public void testGetRehabilitationSolutionFindName( String name ){
        try{
            RestAssured.baseURI = PATH;
            Response response = given().log()
                                       .all()
                                       .header(authorization, bearer)
                                       .queryParam( "name", name )
                                       .when()
                                       .contentType(ContentType.JSON)
                                       .get("/rehabilitation-treatments/find/{name}", name );
            response.then()
                    .log()
                    .body()
                    .time( lessThan(2000L ))
                    .statusCode( 200 );
            Allure.addAttachment("Результат:", TYPE, response.andReturn().asString() );
            Allure.addAttachment("Время выполнения:",  TYPE, String.valueOf( response.time() + " ms."));
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", TYPE, ex.getMessage() );
        }
    }

    
    @DisplayName("Параметры для тестирования")
    public static Stream<Arguments> getRehabilitationSolution() throws Exception{
        RehabilitationSolution rehabilitationSolution = Instancio.of(RehabilitationSolution.class).create();
        return Stream.of( Arguments.of( rehabilitationSolution));
    }

    @Description("Добавить способ лечения ( POST )")
    @DisplayName("Добавить способ лечения ( POST )")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/9.%20Rehabilitation%20Treatment/getAllRehabilitationSolution")
    @ParameterizedTest
    @MethodSource("getRehabilitationSolution")
    public void testAddRehabilitationSolution( RehabilitationSolution rehabilitationSolution ){
        try{
            RestAssured.baseURI = PATH;
            Response response = given().log()
                                       .all()
                                       .header(authorization, bearer)
                                       .when()
                                       .contentType(ContentType.JSON)
                                       .body( rehabilitationSolution )
                                       .post("/rehabilitation-treatments/add" );
            response.then()
                    .log()
                    .body()
                    .time( lessThan(2000L ))
                    .statusCode( 201 );
            Allure.addAttachment("Результат:", TYPE, response.andReturn().asString() );
            Allure.addAttachment("Время выполнения:",  TYPE, String.valueOf( response.time() + " ms."));
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", TYPE, ex.getMessage() );
        }
    }
    
}
