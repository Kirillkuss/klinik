package com.klinik.rest;

import static io.restassured.RestAssured.given;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import com.klinik.entity.RehabilitationSolution;
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

@Disabled
@Owner(value = "Barysevich K. A.")
@Epic(value = "Тестирование АПИ - RehabilitationSolutionController")
@DisplayName("Тестирование АПИ - RehabilitationSolutionController")
public class RestRehabilitationSolutionTest {

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
        //bearer        = RestToken.getToken();
        PATH          = RestToken.PATH;
        TYPE          = RestToken.TYPE;
        authorization = RestToken.authorization;
        rezult        = RestToken.rezult;
        error         = RestToken.error;
        leadTime      = RestToken.leadTime;
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
                                       //.header(authorization, bearer)
                                       .when()
                                       .contentType(ContentType.JSON)
                                       .get("/rehabilitation-treatments/all");
                     response.then()
                             .log()
                             .body()
                             .time( lessThan(2000L ))
                             .statusCode( 200 );
            Allure.addAttachment( rezult, TYPE, response.andReturn().asString() );
            Allure.addAttachment( leadTime, TYPE, String.valueOf( response.time() + " ms."));
        }catch( Exception ex ){
            Allure.addAttachment( error, TYPE, ex.getMessage() );
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
            Allure.addAttachment( rezult, TYPE, response.andReturn().asString() );
            Allure.addAttachment( leadTime, TYPE, String.valueOf( response.time() + " ms."));
        }catch( Exception ex ){
            Allure.addAttachment( error, TYPE, ex.getMessage() );
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
            Allure.addAttachment( rezult, TYPE, response.andReturn().asString() );
            Allure.addAttachment( leadTime, TYPE, String.valueOf( response.time() + " ms."));
        }catch( Exception ex ){
            Allure.addAttachment( error, TYPE, ex.getMessage() );
        }
    }
    
}
