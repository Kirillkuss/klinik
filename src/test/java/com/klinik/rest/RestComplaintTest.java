package com.klinik.rest;

import static io.restassured.RestAssured.given;
import java.util.stream.Stream;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import com.klinik.entity.Complaint;
import com.klinik.request.RequestTypeComplaint;
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
@Epic(value = "Тестирование АПИ - ComplaintController")
@DisplayName("Тестирование АПИ - ComplaintController")
public class RestComplaintTest {
    
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

    @Description("Получение справочника жалобы (GET)")
    @DisplayName("Получение справочника жалобы (GET)")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/6.%20%D0%A1omplaint/findAll")
    @Test
    public void testListComplaints(){
        try{
            RestAssured.baseURI = PATH;
            Response response = given()//.header( authorization, bearer )
                                       .when()
                                       .contentType( ContentType.JSON )
                                       .get("/complaints/list");
                     response.then().statusCode( 200 );
            Allure.addAttachment( rezult, TYPE, response.andReturn().asString() );
            Allure.addAttachment( leadTime,  TYPE, String.valueOf( response.time() + " ms."));
        }catch( Exception ex ){
            Allure.addAttachment( error, TYPE, ex.getMessage() );
        }
    }

    
    @Description("Получение жалобы с под жалобами (GET)")
    @DisplayName("Получение жалобы с под жалобами (GET)")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/6.%20%D0%A1omplaint/listComplaintWithTypeComplaints")
    @ParameterizedTest
    @CsvSource({"1", "2" })
    public void testComplaintsTypeId( Long id ){
        try{
            RestAssured.baseURI = PATH;
            Response response = given().header( authorization, bearer )
                                       .queryParam( "id", id )
                                       .when()
                                       .contentType( ContentType.JSON )
                                       .get("/complaints/type/{id}", id );
                     response.then().statusCode( 200 );
            Allure.addAttachment( rezult, TYPE, response.andReturn().asString() );
            Allure.addAttachment( leadTime,  TYPE, String.valueOf( response.time() + " ms."));
        }catch( Exception ex ){
            Allure.addAttachment( error, TYPE, ex.getMessage() );
        }
    }

    @DisplayName("Параметры для тестирования")
    public static Stream<Arguments> getComplaint() throws Exception{
        Complaint complaint = Instancio.of(Complaint.class).ignore(Select.field( Complaint::getIdComplaint )).create();
        complaint.setIdComplaint( -1L );
        return Stream.of( Arguments.of( complaint));
    }

    @Description("Добавление справочника жалобы (POST)")
    @DisplayName("Добавление справочника жалобы (POST)")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/6.%20%D0%A1omplaint/listComplaintWithTypeComplaints")
    @ParameterizedTest
    @MethodSource("getComplaint")
    public void testAddComplaintsAndTypeComplaint( Complaint complaint ){
        try{
            RestAssured.baseURI = PATH;
            Response response = given().header( authorization, bearer )
                                       .when()
                                       .contentType( ContentType.JSON )
                                       .body(complaint)
                                       .post("/complaints/complaint");
                     response.then().statusCode( 201 );
            Allure.addAttachment( rezult, TYPE, response.andReturn().asString() );
            Allure.addAttachment( leadTime, TYPE, String.valueOf( response.time() + " ms."));
        }catch( Exception ex ){
            Allure.addAttachment( error, TYPE, ex.getMessage() );
        }
    }

    @DisplayName("Параметры для тестирования")
    public static Stream<Arguments> getTypeComplaint() throws Exception{
        RequestTypeComplaint requestTypeComplaint = Instancio.of(RequestTypeComplaint.class).ignore(Select.field( RequestTypeComplaint::getIdComplaint )).create();
        requestTypeComplaint.setIdComplaint( 2L );
        return Stream.of( Arguments.of( requestTypeComplaint));
    }

    @Description("Добавление под жалобы(POST)")
    @DisplayName("Добавление под жалобы(POST)")
    @Link(name = "swagger", url = "http://localhost:8082/swagger-ui/index.html#/6.%20%D0%A1omplaint/saveTypeComplaint")
    @ParameterizedTest
    @MethodSource("getTypeComplaint")
    public void testFirst(RequestTypeComplaint requestTypeComplaint){
        try{
            RestAssured.baseURI = PATH;
            Response response = given().header( authorization, bearer )
                                       .when()
                                       .contentType( ContentType.JSON )
                                       .body( requestTypeComplaint )
                                       .post("/complaints/typecomplain");
                     response.then().statusCode( 201 );
            Allure.addAttachment( rezult, TYPE, response.andReturn().asString() );
            Allure.addAttachment( leadTime, TYPE, String.valueOf( response.time() + " ms."));
        }catch( Exception ex ){
            Allure.addAttachment( error, TYPE, ex.getMessage() );
        }
    }


}
