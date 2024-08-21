package com.klinik.rest;

import static io.restassured.RestAssured.given;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import java.time.format.DateTimeFormatter;
import com.klinik.request.RequestTreatment;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import static org.hamcrest.Matchers.lessThan;

@Owner(value = "Barysevich K. A.")
@Epic(value = "Тестирование АПИ - TreatmentController")
@DisplayName("Тестирование АПИ - TreatmentController")
public class RestTreatmentTest {

    private static final String PATH = "http://localhost:8082";
    private static final String TYPE = "application/json";
    private static final String authorization = "Authorization";
    private static String bearer;

    @BeforeAll
    @DisplayName("Получение токена") 
    public static void setUpClass() {
        bearer = RestToken.getToken();
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
                    .time( lessThan(2000L ))
                    .statusCode( 200 );
            Allure.addAttachment("Результат:", TYPE, response.andReturn().asString() );
            Allure.addAttachment("Время выполнения:",  TYPE, String.valueOf( response.time() + " ms."));
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", TYPE, ex.getMessage() );
        }
    }

    @Description("Получение списка лечений по параметрам(GET)")
    @DisplayName("Получение списка лечений по параметрам(GET)")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/7.%20Treatment/findByParamIdCardAndDateStart")
    @ParameterizedTest
    @CsvSource({"1, 1"})
    public void testListTretmentsFindIdCardAndIdRehabilitationSolution( Long idCard, Long idRehabilitationSolution){
        try{
            RestAssured.baseURI = PATH;
            Response response = given().log()
                                       .all()
                                       .header(authorization, bearer)
                                       .queryParam("idCard", idCard)
                                       .queryParam("idRehabilitationSolution", idRehabilitationSolution)
                                       .when()
                                       .contentType(ContentType.JSON)
                                       .get("/treatments/find/treatment/{id-card}", idCard );
            response.then()
                    .time( lessThan(2000L ))
                    .log()
                    .body()
                    .statusCode( 200 );
            Allure.addAttachment("Результат:", TYPE, response.andReturn().asString() );
            Allure.addAttachment("Время выполнения:",  TYPE, String.valueOf( response.time() + " ms."));
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", TYPE, ex.getMessage() );
        }
    }

    @DisplayName("Параметры для тестирования")
    public static Stream<Arguments> getAddTreatment() throws Exception{
        return Stream.of( Arguments.of( new RequestTreatment( LocalDateTime.now().minusDays(10),
                                                              LocalDateTime.now(),
                                                              1L,
                                                              1L,
                                                              1L,
                                                              1L )));
    }

    @Description("Добавить лечение для пациента(POST)")  
    @DisplayName("Добавить лечение для пациента(POST)")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/7.%20Treatment/addTreatment")
    @ParameterizedTest
    @MethodSource("getAddTreatment")
    public void testAddTreatment( RequestTreatment requestTreatment ){
        try{
            RestAssured.baseURI = PATH;
            Response response = given().log()
                                       .all()
                                       .header( authorization, bearer )
                                       .when()
                                       .body( requestTreatment )
                                       .contentType(ContentType.JSON)
                                       .post("/treatments/treatment/add" );
            response.then()
                    .time( lessThan(2000L ))
                    .log()
                    .body()
                    .statusCode( 201 );
            Allure.addAttachment("Результат:", TYPE, response.andReturn().asString() );
            Allure.addAttachment("Время выполнения:",  TYPE, String.valueOf( response.time() + " ms."));
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", TYPE, ex.getMessage() );
        }
    }

    
}
