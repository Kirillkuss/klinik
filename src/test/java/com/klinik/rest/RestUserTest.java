package com.klinik.rest;

import static io.restassured.RestAssured.given;
import java.util.stream.Stream;
import org.instancio.Instancio;
import org.instancio.Select;
import static org.instancio.Select.field;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import com.klinik.request.UserRequest;
import static org.hamcrest.Matchers.lessThan;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@Owner(value = "Barysevich K. A.")
@Epic(value = "Тестирование АПИ - ReportController")
@DisplayName("Тестирование АПИ - ReportController")
public class RestUserTest {

    private static final String PATH = "http://localhost:8082";
    private static final String TYPE = "application/json";
    private static final String authorization = "Authorization";
    private static String token;
    private static String bearer;

    @BeforeAll
    @DisplayName("Получение токена") 
    public static void setUpClass() {
        bearer = RestToken.getToken();
    }
    @Description("List Users  ( GET )")
    @DisplayName("List Users  ( GET )")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/USERS/getUsers")
    @Test
    public void testGetListUser(){
        try{
            RestAssured.baseURI = PATH;
            Response response = given().log()
                                       .all()
                                       .header(authorization, bearer)
                                       .when()
                                       .contentType(ContentType.JSON)
                                       .get("/users");
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
    public static Stream<Arguments> getUser() throws Exception{
        UserRequest userRequest  = Instancio.of(UserRequest.class)
                                            .generate( field(UserRequest::getLogin),gen -> gen.text().pattern("#a#a#a#a#a#a#a#a"))
                                            .generate( field(UserRequest::getPassword),gen -> gen.text().pattern("#a#a#a#a#a#a#a#a#a#a#a#a" ))
                                            .generate( field(UserRequest::getEmail),gen -> gen.text().pattern("#a#a#a#a#a#a@email.com" ))
                                            .ignore(Select.field( UserRequest::getRole )).create();
        /**UserRequest userRequest  = Instancio.of(UserRequest.class)
                                            .generate(field(UserRequest::getLogin), gen -> gen.text().pattern("[a-z]{8}")) 
                                            .generate(field(UserRequest::getPassword), gen -> gen.text().pattern("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{12,}")) 
                                            .generate(field(UserRequest::getEmail), gen -> gen.text().pattern("[a-zA-Z0-9._%+-]{1,15}@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}")) 
                                            .ignore(Select.field( UserRequest::getRole )).create();*/
        userRequest.setRole("USER");
        return Stream.of( Arguments.of( userRequest ));
    }

    @Description("Добавить user ( POST )")
    @DisplayName("Добавить user ( POST )")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/USERS/addUser")
    @ParameterizedTest
    @MethodSource("getUser")
    public void testGetReportPatient(  UserRequest userRequest  ){
        try{
            RestAssured.baseURI = PATH;
            Response response = given().log()
                                       .all()
                                       .header(authorization, bearer)
                                       .when()
                                       .contentType(ContentType.JSON)
                                       .body( userRequest )
                                       .post("/users");
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

    @Description("Обновить keystore ( GET )")
    @DisplayName("Обновить keystore ( GET )")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/USERS/updateKeystore")
    @Test
    public void testUpdateKeystore( ){
        try{
            RestAssured.baseURI = PATH;
            Response response = given().log()
                                       .all()
                                       .header(authorization, bearer)
                                       .when()
                                       .contentType(ContentType.JSON)
                                       .get("/users/keystore");
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

    @Description("Обновить ключи public.pem and private.pem ( GET )")
    @DisplayName("Обновить ключи public.pem and private.pem ( GET )")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/USERS/updateKeys")
    @Test
    public void testUpdateKeys( ){
        try{
            RestAssured.baseURI = PATH;
            Response response = given().log()
                                       .all()
                                       .header(authorization, bearer)
                                       .when()
                                       .contentType(ContentType.JSON)
                                       .get("/users/keys");
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

    @Description("Обновить encryption ( GET )")
    @DisplayName("Обновить encryption ( GET )")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/USERS/updateEncryption")
    @Test
    public void testUpdateEncription( ){
        try{
            RestAssured.baseURI = PATH;
            Response response = given().log()
                                       .all()
                                       .header(authorization, bearer)
                                       .when()
                                       .contentType(ContentType.JSON)
                                       .get("/users/encrypt");
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

    @Description("Обновить ключи в базе ( GET )")
    @DisplayName("Обновить ключи в базе ( GET )")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/USERS/updateDataBase")
    @Test
    public void testUpdateKeysDataBase( ){
        try{
            RestAssured.baseURI = PATH;
            Response response = given().log()
                                       .all()
                                       .header(authorization, bearer)
                                       .when()
                                       .contentType(ContentType.JSON)
                                       .get("/users/database/keys");
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

    
}
