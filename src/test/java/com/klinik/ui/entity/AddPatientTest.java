package com.klinik.ui.entity;

import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import com.klinik.entity.Document;
import com.klinik.entity.Patient;
import com.klinik.rest.RestToken;
import com.klinik.ui.LoginSuccess;

@Disabled
@DisplayName("Добавление пациента через ui")
public class AddPatientTest {

    private WebDriver driver;
    private Patient patient;

    private static String queryDocument = "SELECT d.id_document \n" + 
                                          "FROM Document d \n" + 
                                          "LEFT JOIN Patient p ON d.id_document = p.document_id \n" + 
                                          "WHERE p.document_id IS NULL;";
    
    @BeforeEach
    public void setUp() {
        patient = RestToken.getPatient();
        driver = LoginSuccess.getSuccessLogin();
    }

    @AfterEach
    public void tearDown() {
        new Actions(driver).pause( Duration.ofSeconds(2)).perform();
        driver.quit();
    }

    private String getAddDocumentQuery( Document document ){
        return String.format( "INSERT INTO Document (type_document, seria, numar, snils, polis) " +
                              "VALUES ('%s', '%s', '%s', '%s', '%s');",
                              document.getTypeDocument(),
                              document.getSeria(),
                              document.getNumar(),
                              document.getSnils(),
                              document.getPolis());  
    }

    @Test
    @DisplayName("Добавление пациента")
    public void testAddPatient() throws Exception{
        List<Long> listDocumentId = RestToken.getStremValue( queryDocument, "id_document");
        if ( listDocumentId.stream().count() < 2 ) RestToken.getStremValue( getAddDocumentQuery(RestToken.getDocument()), "id_document");
        try{
            driver.findElement( By.xpath( "//*[@id='accButtonPatient']" )).click();
            new Actions( driver ).pause( Duration.ofSeconds(1)).perform();
            driver.findElement(By.id("buttonPatient")).click();
            new Actions( driver ).pause( Duration.ofSeconds(1)).perform();
            driver.findElement(By.className("btn-outline-dark")).click();
            new Actions( driver ).pause( Duration.ofSeconds(1)).perform();

            driver.findElement( By.xpath( "//*[@id='surname']" )).click();
            driver.findElement( By.xpath( "//*[@id='surname']" )).sendKeys( patient.getSurname() );
            driver.findElement(By.xpath("//*[@id='savePatient']" )).click();
            new Actions( driver ).pause( Duration.ofSeconds(2)).perform();

            driver.findElement( By.xpath( "//*[@id='name']" )).click();
            driver.findElement( By.xpath( "//*[@id='name']" )).sendKeys( patient.getName() );
            driver.findElement(By.xpath("//*[@id='savePatient']" )).click();
            new Actions( driver ).pause( Duration.ofSeconds(2)).perform();

            driver.findElement( By.xpath( "//*[@id='fullName']" )).click();
            driver.findElement( By.xpath( "//*[@id='fullName']" )).sendKeys( patient.getFullName() );
            driver.findElement(By.xpath("//*[@id='savePatient']" )).click();

            driver.findElement( By.xpath( "//*[@id='gender']" )).click();
            driver.findElement( By.xpath( "//*[@id='gender']" )).sendKeys( patient.getGender().name() );
            driver.findElement(By.xpath("//*[@id='savePatient']" )).click();
            new Actions( driver ).pause( Duration.ofSeconds(2)).perform();

            driver.findElement( By.xpath( "//*[@id='phone']" )).click();
            driver.findElement( By.xpath( "//*[@id='phone']" )).sendKeys( patient.getPhone());
            driver.findElement(By.xpath("//*[@id='savePatient']" )).click();
            new Actions( driver ).pause( Duration.ofSeconds(2)).perform();

            driver.findElement( By.xpath( "//*[@id='address']" )).click();
            driver.findElement( By.xpath( "//*[@id='address']" )).sendKeys( patient.getAddress() );
            driver.findElement(By.xpath("//*[@id='savePatient']" )).click();
            new Actions( driver ).pause( Duration.ofSeconds(2)).perform();

            driver.findElement( By.xpath( "//*[@id='idDocument']" )).click();
            driver.findElement( By.xpath( "//*[@id='idDocument']" )).sendKeys(  listDocumentId.stream().findFirst().orElseThrow().toString() );
            driver.findElement(By.xpath("//*[@id='savePatient']" )).click();
            new Actions( driver ).pause( Duration.ofSeconds(2)).perform();
            
            new Actions( driver ).pause( Duration.ofSeconds(2)).perform();

        }catch( Exception ex ){
            System.out.println( "ERROR " + ex.getMessage() );
        }    
        }


    
}
