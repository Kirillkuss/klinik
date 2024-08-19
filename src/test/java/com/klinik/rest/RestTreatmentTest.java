package com.klinik.rest;

import static io.restassured.RestAssured.given;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;
import java.time.format.DateTimeFormatter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klinik.request.AuthRequest;
import com.klinik.response.AuthResponse;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.time.LocalDateTime;

@Owner(value = "Barysevich K. A.")
@Epic(value = "Тестирование АПИ - TreatmentController")
@DisplayName("Тестирование АПИ - TreatmentController")
public class RestTreatmentTest {

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

    @DisplayName("Параметры для тестирования")
    public static Stream<Arguments> getFindParam() throws Exception{
        LocalDateTime from = LocalDateTime.now().minusYears( 10 );
        LocalDateTime to = LocalDateTime.now();
        return Stream.of( Arguments.of( 1L , from, to ));
    }

    @Description("Получение списка лечений по параметрам(GET)")
    @DisplayName("Получение списка лечений по параметрам(GET)")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/7.%20Treatment/findByParamIdCardAndDateStart")
    @ParameterizedTest
    @MethodSource("getFindParam")
    public void testListTretmentsFind( Long idCard, LocalDateTime from, LocalDateTime to ){
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            RestAssured.baseURI = PATH;
            Response response = given().header(authorization, bearer)
                                       .queryParam("id", idCard)
                                       .queryParam("dateFrom", from.format( formatter ))
                                       .queryParam("dateTo", to.format( formatter ))
                                       .when()
                                       .contentType(ContentType.JSON)
                                       .get("/treatments/find/treat/{id-card}", idCard );
            response.then()
                    .statusCode( 200 );
            Allure.addAttachment("Результат:", TYPE, response.andReturn().asString() );
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", TYPE, ex.getMessage() );
        }
    }

    @Description("Получение списка лечений по параметрам(GET)")
    @DisplayName("Получение списка лечений по параметрам(GET)")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/7.%20Treatment/findByParamIdCardAndDateStart")
    @ParameterizedTest
    @CsvSource({"1, 1"})
    public void testListTretmentsFind( Long idCard, Long idRehabilitationSolution){
        try{
            RestAssured.baseURI = PATH;
            Response response = given().header(authorization, bearer)
                                       .queryParam("idCard ", idCard)
                                       .queryParam("idRehabilitationSolution ", idRehabilitationSolution)
                                       .when()
                                       .contentType(ContentType.JSON)
                                       .get("/treatments/find/treatment/{id-card}{id-rehabilitation-solution}" );
            response.then()
                    .statusCode( 200 );
            Allure.addAttachment("Результат:", TYPE, response.andReturn().asString() );
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", TYPE, ex.getMessage() );
        }
    }

    
}
