package com.klinik.rest;

import static io.restassured.RestAssured.given;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klinik.entity.RecordPatient;
import com.klinik.request.AuthRequest;
import com.klinik.request.RequestRecordPatient;
import com.klinik.response.AuthResponse;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@Owner(value = "Barysevich K. A.")
@Epic(value = "Тестирование АПИ - RecordPatientController")
@DisplayName("Тестирование АПИ - RecordPatientController")
public class RestRecordPatientTest {

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

   /**  @DisplayName("Параметры для тестирования")
    public static Stream<Arguments> getParamsRecord() throws Exception{
        RecordPatient recordPatient = Instancio.of(RecordPatient.class).ignore(Select.field( RecordPatient::getIdRecord)).create();
        recordPatient.setIdRecord( -1L );
        return Stream.of( Arguments.of( recordPatient, 4L, 5L ));
    }*/

    @Description("Добавить запись пациента к врачу (POST)")
    @DisplayName("Добавить запись пациента к врачу (POST)")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/5.%20Records%20Patients/addRecordPatient")
    //@ParameterizedTest
   // @MethodSource("getParamsRecord")
    public void testAddRecordPatientOld( RecordPatient recordPatient, Long idDoctor, Long idCardPatient ) throws Exception {
        try{
            RestAssured.baseURI = PATH;
            Response response = given().header( authorization, bearer )
                                       .queryParam( "record", recordPatient)
                                       .queryParam( "id-doctor", idDoctor)
                                       .queryParam( "id-card", idCardPatient)
                                       .when()
                                       .contentType( ContentType.JSON )
                                       .post("/record-patients/add/{record}{id-doctor}{id-card}");
                     response.then()
                             .statusCode(200);
            Allure.addAttachment("Результат:", TYPE, response.andReturn().asString() );
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", TYPE, ex.getMessage() );
        }
    }

    @DisplayName("Параметры для тестирования")
    public static Stream<Arguments> getRequestRecordPatient() throws Exception{
        RequestRecordPatient requestRecordPatient = new RequestRecordPatient();
        requestRecordPatient.setDateAppointment( LocalDateTime.now());
        requestRecordPatient.setDateRecord( LocalDateTime.now().minusDays(10));
        requestRecordPatient.setNumberRoom( 500L );
        requestRecordPatient.setIdCardPatient( 5L );
        requestRecordPatient.setIdDoctor( 10L );
        return Stream.of( Arguments.of( requestRecordPatient ));
    }

    @Description("Добавить запись пациента к врачу (POST)")
    @DisplayName("Добавить запись пациента к врачу (POST)")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/5.%20Records%20Patients/addRecordPatient")
    @ParameterizedTest
    @MethodSource("getRequestRecordPatient")
    public void testAddRecordPatient( RequestRecordPatient requestRecordPatient ) throws Exception {
        try{
            RestAssured.baseURI = PATH;
            Response response = given().header( authorization, bearer )
                                       .when()
                                       .contentType( ContentType.JSON )
                                       .body( requestRecordPatient )
                                       .post("/record-patients/add");
                     response.then()
                             .statusCode(201);
            Allure.addAttachment("Результат:", TYPE, response.andReturn().asString() );
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", TYPE, ex.getMessage() );
        }
    }

    @DisplayName("Параметры для тестирования")
    public static Stream<Arguments> getFindParam() throws Exception{
        LocalDateTime from = LocalDateTime.now().minusYears( 10 );
        LocalDateTime to = LocalDateTime.now();
        return Stream.of( Arguments.of( 2L , from, to ));
    }

    @Description("Список всех записей пациентов к врачу по параметрам  (GET)")
    @DisplayName("Список всех записей пациентов к врачу по параметрам  (GET)")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/5.%20Records%20Patients/findByParams")
    @ParameterizedTest
    @MethodSource("getFindParam")
    public void testFindRecordPatient( Long id, LocalDateTime from, LocalDateTime to ) throws Exception {
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            RestAssured.baseURI = PATH;
            Response response = given().header(authorization, bearer)
                                       .queryParam("id", id)
                                       .queryParam("dateFrom", from.format( formatter ))
                                       .queryParam("dateTo", to.format( formatter ))
                                       .when()
                                       .contentType(ContentType.JSON)
                                       .get("/record-patients/find/{id}", id ); 
                     response.then().statusCode(200); 
            Allure.addAttachment("Результат:", TYPE, response.andReturn().asString() );
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", TYPE, ex.getMessage() );
        }
    }

    /**@Description("Список всех записей пациентов к врачу по параметрам  (GET)")
    @DisplayName("Список всех записей пациентов к врачу по параметрам  (GET)")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/5.%20Records%20Patients/findByParams")
    @ParameterizedTest
    @MethodSource("getFindParam")
    public void testFindRecordPatient(  Long id, LocalDateTime from, LocalDateTime to) throws Exception {
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            RestAssured.baseURI = PATH;
            Response response = given().header(authorization, bearer)
            .queryParam("id", id) 
            .queryParam("dateFrom", from.format(formatter))
            .queryParam("dateTo", to.format(formatter)) 
            .when()
            .contentType(ContentType.JSON)
            .get("/record-patients/find"); 
                     response.then()
                             .statusCode(200);
            Allure.addAttachment("Результат:", TYPE, response.andReturn().asString() );
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", TYPE, ex.getMessage() );
        }
    }*/




    
}
