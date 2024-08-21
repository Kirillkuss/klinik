package com.klinik.rest;

import static io.restassured.RestAssured.given;
import org.junit.jupiter.api.DisplayName;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klinik.request.AuthRequest;
import com.klinik.response.AuthResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class RestToken {

    public static final String PATH = "http://localhost:8082";
    public static final String TYPE = "application/json";
    public static final String authorization = "Authorization";
    public static final String rezult = "Результат: ";
    public static final String error  = "Ошибка: ";
    public static final String leadTime = "Время выполнения: ";
    private static String token;
    private static String bearer;

    
    @DisplayName("Получение токена") 
    public static String getToken() {
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
            return bearer;
        }catch( Exception ex ){
            ex.printStackTrace( System.err );
            return bearer;
        }
    }
}
