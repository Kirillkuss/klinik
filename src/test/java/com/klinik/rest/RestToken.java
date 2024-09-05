package com.klinik.rest;

import static io.restassured.RestAssured.given;
import static org.instancio.Select.field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.DisplayName;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klinik.entity.Doctor;
import com.klinik.entity.Document;
import com.klinik.entity.Gender;
import com.klinik.entity.Patient;
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

    @DisplayName("Генерация телефона")
    private static String generatePhoneNumber() {
        String code = Instancio.of(String.class).generate( Select.allStrings(), gen -> gen.oneOf("+37529", "+37525", "+37533", "+37544")).create();
        String number = Instancio.of(String.class).generate( Select.allStrings(), gen -> gen.text().pattern("#d#d#d#d#d#d#d")).create();
        return code+number;
    }

    public static Patient getPatientMan(){
        Patient man = Instancio.of(Patient.class)
                                    .generate(field(Patient::getSurname),  gen -> gen.oneOf("Петров", "Волков", "Попов", "Федоров"))
                                    .generate(field(Patient::getName),  gen -> gen.oneOf("Максим", "Алексей", "Андрей", "Сергей"))
                                    .generate(field(Patient::getFullName),  gen -> gen.oneOf("Александрович", "Михайлович", "Алексеевич", "Анатольевич"))
                                    .generate(field(Patient::getAddress),  gen -> gen.oneOf("Притыцкого 24", "Одинвово 11", "Михалова 154", "Молодежная 21"))
                                    .generate(field(Patient::getGender),  gen -> gen.oneOf(Gender.MAN))
                                    .generate(field(Patient::getPhone),  gen -> gen.oneOf( generatePhoneNumber() ))
                                    .ignore(Select.field( Patient::getIdPatient ))
                                    .create();
                                    man.setIdPatient( -1L );
        return man;
    }

    public static Patient getPatientWoman(){
        Patient woman = Instancio.of(Patient.class)
                                    .generate(field(Patient::getSurname),  gen ->gen.oneOf("Титова", "Федоровна", "Морозова", "Колокольцева"))
                                    .generate(field(Patient::getName),  gen -> gen.oneOf("Татьяна", "Алеся", "Дарья", "Наталья"))
                                    .generate(field(Patient::getFullName), gen ->  gen.oneOf("Александровна", "Михайловна", "Алексеевна", "Анатольевна"))
                                    .generate(field(Patient::getAddress),  gen -> gen.oneOf("Притыцкого 24", "Одинвово 11", "Михалова 154", "Молодежная 21"))
                                    .generate(field(Patient::getGender),  gen -> gen.oneOf(Gender.WOMAN))
                                    .generate(field(Patient::getPhone),  gen -> gen.oneOf( generatePhoneNumber() ))
                                    .ignore(Select.field( Patient::getIdPatient ))
                                    .create();
                                    woman.setIdPatient( -1L );
        return woman;
    }

    
    @DisplayName("Генерация пациента")
    public static Patient getPatient(){
        Random random = new Random();
        int genderSelector = random.nextInt(2);
        if (genderSelector == 0) {
            return getPatientMan();
        } else {
            return getPatientWoman(); 
        }
    }
    @DisplayName("Генерация документа")
    public static Document getDocument(){
        Document document = Instancio.of(Document.class)
                                     .generate(field(Document::getTypeDocument),  gen -> gen.oneOf("Пасспорт", "Водительское уд.", "Свид. о рождении"))
                                     .generate(field(Document::getSeria),  gen -> gen.oneOf("АМ", "ВМ", "КВ", "ЯС"))
                                     .generate(field(Document::getNumar),  gen -> gen.text().pattern("#d#d#d#d#d#d#d#d#d"))
                                     .generate(field(Document::getSnils ),  gen -> gen.text().pattern("#d#d#d-#d#d#d-#d#d#d-#d#d"))
                                     .generate(field(Document::getPolis ),  gen -> gen.text().pattern("#d#d#d#d #d#d#d#d #d#d#d#d #d#d#d#d"))
                                     .ignore(Select.field( Document::getIdDocument )).create();
                 document.setIdDocument( -1L );
        return document;
    }

    public static Doctor getDoctor(){
        Doctor doctor = Instancio.of(Doctor.class)
                                  .generate( field( Doctor::getSurname), gen -> gen.oneOf("Петров", "Волков", "Попов", "Федоров", "SECOND"))
                                  .generate( field( Doctor::getName), gen -> gen.oneOf("Максим", "Алексей", "Андрей", "Сергей", "TEST"))
                                  .generate( field( Doctor::getFullName), gen -> gen.oneOf("Александрович", "Михайлович", "Алексеевич", "Анатольевич", "Mouse"))
                                  .ignore( Select.field( Doctor::getIdDoctor ))
                                  .create();
               doctor.setIdDoctor( -1L );
        return doctor;                            
    }

    public static List<Long> getStremValue( String query, String colum ){
        List<Long> listLong = new ArrayList<>();
        String url = "jdbc:postgresql://localhost:5432/Klinika";
        String user = "postgres";
        String password = "admin";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Long id = rs.getLong(colum);
                listLong.add(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listLong;
    }
}
