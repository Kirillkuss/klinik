package com.klinik.rest;

import static io.restassured.RestAssured.given;

import java.util.stream.Stream;

import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import com.klinik.entity.Doctor;
import com.klinik.entity.Document;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@Owner(value = "Barysevich K. A.")
@Epic(value = "Тестирование АПИ - DocumentController")
@DisplayName("Тестирование АПИ - DocumentController")
public class RestDocumentTest {

    @Feature("Получение списка документов")
    @Description("Получение списка документов")
    @DisplayName("Вызов метода GET: http://localhost:8082/web/documents/list")
    @Link(name = "swagger", url = "http://localhost:8082/web/swagger-ui/index.html#/3.%20Documents/getAllDocuments")
    @Test
    public  void testGetAllDocument(){
        try{
            RestAssured.baseURI = "http://localhost:8082";
            Response response = given().when().get("/web/documents");
            response.then().statusCode(200);
            Allure.addAttachment("Результат:", "application/json", response.andReturn().asString() );
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", "application/json", ex.getMessage() );
        }
    }

    @Feature("Получение списка документов Lazy")
    @Description("Получение списка документов Lazy")
    @DisplayName("Вызов метода GET: http://localhost:8082/web/documents/list?page=1&size=2")
    @Link(name = "swagger", url = "http://localhost:8082/web/swagger-ui/index.html#/3.%20Documents/getLazyDocument")
    @ParameterizedTest
    @CsvSource({"1, 3", "5, 13", "13, 34"})
    public void testGetDocumentsLazy( int page, int size ){
        try{
            RestAssured.baseURI = "http://localhost:8082";
            Response response = given().queryParam("page", page).queryParam("size", size).when().get("/web/documents/list");
            response.then().statusCode(200);
            Allure.addAttachment("Результат:", "application/json", response.andReturn().asString() );
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", "application/json", ex.getMessage() );
        }
    }

    @Feature("Найти документ")
    @Description("Найти документ")
    @DisplayName("Вызов метода GET: http://localhost:8082/web/documents/find?word=123243455")
    @Link(name = "swagger", url = "http://localhost:8082/web/swagger-ui/index.html#/3.%20Documents/findByWord_1")
    @ParameterizedTest
    @CsvSource({"123-456-789-01", "123243455"})
    public void testGetByWord( String word ){
        try{
            RestAssured.baseURI = "http://localhost:8082";
            Response response = given().queryParam("word", word ).when().get("/web/documents/find");
            response.then().statusCode(200);
            Allure.addAttachment("Результат:", "application/json", response.andReturn().asString() );
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", "application/json", ex.getMessage() );
        }
    }

    @DisplayName("Параметры для тестирования")
    public static Stream<Arguments> getParams() throws Exception{
        Document document =  Instancio.of(Document.class).ignore(Select.field(Document::getIdDocument )).create();
        document.setIdDocument( -1L );
        return Stream.of( Arguments.of( document ));
    }

    @Feature("Добавить документ")
    @Description("Добавить документ")
    @DisplayName("Вызов метода POST: http://localhost:8082/web/documents/add")
    @Link(name = "swagger", url = "http://localhost:8082/web/swagger-ui/index.html#/3.%20Documents/addDocument")
    @ParameterizedTest
    @MethodSource("getParams")
    public void testAddDocument( Document document ){
        try{
            RestAssured.baseURI = "http://localhost:8082";
            Response response = given().when().contentType(ContentType.JSON).body( document ).post("/web/documents/add");
            response.then().statusCode( 201 );
            Allure.addAttachment("Результат:", "application/json", response.andReturn().asString() );
        }catch( Exception ex ){
            Allure.addAttachment("Ошибка:", "application/json", ex.getMessage() );
        }
    }
    
}
