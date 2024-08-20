package com.klinik.rest;

import static io.restassured.RestAssured.given;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klinik.request.AuthRequest;
import com.klinik.request.reports.ReportDrugTreatmentRequest;
import com.klinik.request.reports.ReportPatientRequest;
import com.klinik.response.AuthResponse;
import static org.hamcrest.Matchers.lessThan;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;
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
public class RestReportTest {

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
    public static Stream<Arguments> getReportPatientRequest() throws Exception{
        ReportPatientRequest reportPatientRequest = new ReportPatientRequest();
        reportPatientRequest.setIdPatient(4L );
        reportPatientRequest.setStart( LocalDateTime.now().minusYears( 5 ));
        reportPatientRequest.setEnd( LocalDateTime.now());
        return Stream.of( Arguments.of( reportPatientRequest ));
    }

    @Description("Отчет по записям пациента к врачу за период времени ( GET )")
    @DisplayName("Отчет по записям пациента к врачу за период времени ( GET )")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/Report/findInformationAboutRecordPatient")
    @ParameterizedTest
    @MethodSource("getReportPatientRequest")
    public void testGetReportPatient( ReportPatientRequest reportPatientRequest ){
        try{
            RestAssured.baseURI = PATH;
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            Response response = given().log()
                                       .all()
                                       .header(authorization, bearer)
                                       .queryParam("idPatient", reportPatientRequest.getIdPatient())
                                       .queryParam("start", reportPatientRequest.getStart().format(formatter))
                                       .queryParam("end", reportPatientRequest.getEnd().format(formatter))
                                       .when()
                                       .contentType(ContentType.JSON)
                                       .get("/reports/report-patient/{reportPatientRequest}", "idPatient");
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
    public static Stream<Arguments> getReportDrugTreatmentRequest() throws Exception{
        ReportDrugTreatmentRequest reportDrugTreatmentRequest = new ReportDrugTreatmentRequest();
        reportDrugTreatmentRequest.setFrom( LocalDateTime.now().minusYears( 5 ));
        reportDrugTreatmentRequest.setTo( LocalDateTime.now());
        return Stream.of( Arguments.of( reportDrugTreatmentRequest ));
    }

    @Description("Отчет о медикаментозном лечении за период времени ( GET )")
    @DisplayName("Отчет о медикаментозном лечении за период времени ( GET )")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/Report/findInformationAboutRecordPatient")
    @ParameterizedTest
    @MethodSource("getReportDrugTreatmentRequest")
    public void testGetDrugTreatment( ReportDrugTreatmentRequest reportDrugTreatmentRequest ){
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            RestAssured.baseURI = PATH;
            Response response = given().log()
                                        .all()
                                        .header(authorization, bearer)
                                        .queryParam( "from",reportDrugTreatmentRequest.getFrom().format( formatter ))
                                        .queryParam( "to", reportDrugTreatmentRequest.getTo().format( formatter ))
                                        .when()
                                        .contentType(ContentType.JSON)
                                        .get("/reports/drug-treatment/{reportDrugTreatmentRequest}", "from" );

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
    public static Stream<Arguments> getRehabilitationTreatments() throws Exception{
        return Stream.of( Arguments.of(  LocalDateTime.now().minusYears( 5 ), LocalDateTime.now()));
    }

    @Description("Отчет о медикаментозном лечении за период времени ( GET )")
    @DisplayName("Отчет о медикаментозном лечении за период времени ( GET )")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/Report/findInformationAboutRecordPatient")
    @ParameterizedTest
    @MethodSource("getRehabilitationTreatments")
    public void testGetReportDrugTretment( LocalDateTime dateFrom,  LocalDateTime dateTo  ){
        try{
            RestAssured.baseURI = PATH;
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            Response response = given().log()
                                       .all()
                                       .header(authorization, bearer)
                                       .queryParam( "dateFrom", dateFrom.format( formatter ))
                                       .queryParam( "dateTo", dateTo.format( formatter ) )
                                       .when()
                                       .contentType( ContentType.JSON )
                                       .get( "/reports/rehabilitation-treatments/{from}", dateFrom );
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

    @Description("Отчет о полной информации по пациенту( GET )")
    @DisplayName("Отчет о полной информации по пациенту( GET )")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/Report/fullInformationPatient")
    @ParameterizedTest
    @CsvSource({"1", "2"})
    public void testGetReportInfoPatient( Long id  ){
        try{
            RestAssured.baseURI = PATH;
            Response response = given().log()
                                       .all()
                                       .header(authorization, bearer)
                                       .queryParam( "idCard", id )
                                       .when()
                                       .contentType( ContentType.JSON )
                                       .get( "/reports/info-patient/{id-card}", id );
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
