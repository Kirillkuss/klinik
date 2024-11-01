package com.klinik.rest;

import static io.restassured.RestAssured.given;
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
import com.klinik.entity.DrugTreatment;
import com.klinik.request.DrugRequest;
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
@Epic(value = "Тестирование АПИ - DrugTreatmentController")
@DisplayName("Тестирование АПИ - DrugTreatmentController")
public class RestDrugTreatment {

    private static String PATH;
    private static String TYPE;
    private static String rezult;
    private static String error;
    private static String JSESSIONID;
    public static  String leadTime;
    
    @BeforeAll
    @DisplayName("Получение входных параметров для выполения запросов") 
    public static void setUpClass() {
        JSESSIONID = RestSession.getSessionId();
        PATH       = RestSession.PATH;
        TYPE       = RestSession.TYPE;
        rezult     = RestSession.rezult;
        error      = RestSession.error;
        leadTime   = RestSession.leadTime;
    }

    @Description("Список всех медикаментозных лечений ( GET )")
    @DisplayName("Список всех медикаментозных лечений ( GET )")
    @Link(name = "swagger", url = "http://localhost:8082/web/swagger-ui/index.html#/8.%20Drug%20Treatment/listAll")
    @RepeatedTest(2)
    public void testListDrugTretments(){
        try{
            RestAssured.baseURI = PATH;
            Response response = given().log()
                                       .all()
                                       .cookie("JSESSIONID", JSESSIONID )
                                       .when()
                                       .contentType(ContentType.JSON)
                                       .get("/drug-treatments/list");
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

    @Description("Поиск по ИД медикаментозного лечения c препаратами ( GET )")
    @DisplayName("Поиск по ИД медикаментозного лечения c препаратами ( GET )")
    @Link(name = "swagger", url = "http://localhost:8082/web/swagger-ui/index.html#/8.%20Drug%20Treatment/findById")
    @ParameterizedTest
    @CsvSource({"1", "3"})
    public void testListDrugTretmentsById( Long id ){
        try{
            RestAssured.baseURI = PATH;
            Response response = given().log()
                                       .all()
                                       .cookie("JSESSIONID", JSESSIONID )
                                       .queryParam( "id", id)
                                       .when()
                                       .contentType( ContentType.JSON )
                                       .get( "/drug-treatments/drug-treatment/{id}", id );
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
    public static Stream<Arguments> getDrugTreatment() throws Exception{
        DrugTreatment drugTreatment = Instancio.of(DrugTreatment.class).ignore(Select.field( DrugTreatment::getIdDrugTreatment )).create();
        drugTreatment.setIdDrugTreatment( -1L );
        return Stream.of( Arguments.of( drugTreatment));
    }

    @Description("Добавить медикаментозного лечения ( POST )")
    @DisplayName("Добавить медикаментозного лечения ( POST )")
    @Link(name = "swagger", url = "http://localhost:8082/web/swagger-ui/index.html#/8.%20Drug%20Treatment/addDrugTreatment")
    @ParameterizedTest
    @MethodSource("getDrugTreatment")
    public void testAddDrugTreatment( DrugTreatment drugTreatment ){
        try{
            RestAssured.baseURI = PATH;
            Response response = given().log()
                                       .all()
                                       .cookie("JSESSIONID", JSESSIONID )
                                       .when()
                                       .contentType( ContentType.JSON )
                                       .body( drugTreatment )
                                       .post( "/drug-treatments/add/drug-treatment");
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

    @DisplayName("Параметры для тестирования")
    public static Stream<Arguments> getDrugRequest() throws Exception{
        DrugRequest drugRequest = Instancio.of(DrugRequest.class).create();
        drugRequest.setIdDrugTreatment( 4L );
        return Stream.of( Arguments.of( drugRequest ));
    }

    @Description("Добавить медикаментозного лечения ( POST )")
    @DisplayName("Добавить медикаментозного лечения ( POST )")
    @Link(name = "swagger", url = "http://localhost:8082/web/swagger-ui/index.html#/8.%20Drug%20Treatment/addDrugTreatment")
    @ParameterizedTest
    @MethodSource("getDrugRequest")
    public void testAddDrug( DrugRequest drugRequest ){
        try{
            RestAssured.baseURI = PATH;
            Response response = given().log()
                                       .all()
                                       .cookie("JSESSIONID", JSESSIONID )
                                       .when()
                                       .contentType( ContentType.JSON )
                                       .body( drugRequest )
                                       .post( "/drug-treatments/add/drug");
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
    
}
