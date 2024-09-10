package com.klinik.ui.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import com.klinik.entity.CardPatient;
import com.klinik.entity.Document;
import com.klinik.entity.Patient;
import com.klinik.rest.RestToken;
import java.time.Duration;
import java.util.List;
import com.klinik.ui.LoginSuccess;

@DisplayName("Добавление карты пациента через ui")
public class AddCardPatientTest {

    private WebDriver driver;
    private CardPatient cardPatient;

    private final String queryPatient = "SELECT p.id_patient FROM patient p\n" + 
                                     "LEFT JOIN card_patient cp ON cp.patient_id = p.id_patient\n" + 
                                     "WHERE cp.patient_id IS NULL;";
                                     
    private final String queryDocument = "SELECT d.id_document \n" + 
                                          "FROM Document d \n" + 
                                          "LEFT JOIN Patient p ON d.id_document = p.document_id \n" + 
                                          "WHERE p.document_id IS NULL;";
    private List<Long> lisIdPatients;

    @BeforeEach
    public void setUp() {
        lisIdPatients = RestToken.getStremValue( queryPatient, "id_patient" );
        if ( lisIdPatients.stream().count() < 2 ){
            List<Long> listIdDocument = RestToken.getStremValue( queryDocument, "id_document" );
            if( listIdDocument.stream().count()  < 2 ){
                RestToken.getStremValue( RestToken.getAddDocumentQuery( RestToken.getDocument() ), "id_document" );
                listIdDocument = RestToken.getStremValue( queryDocument, "id_document" );
            }
            RestToken.getStremValue( RestToken.getAddPatientQuery( RestToken.getPatient(), listIdDocument.stream()
                                                                                               .findFirst()
                                                                                               .orElseThrow() ), "id_patient"); 
        }
        cardPatient   = RestToken.getCardPatient();
        driver        = LoginSuccess.getSuccessLogin();
    }


    
    @AfterEach
    public void tearDown() {
        new Actions(driver).pause( Duration.ofSeconds(2)).perform();
        driver.quit();
    }

    @Test
    @DisplayName("Добавление карты пациента")
    public void testAddCardPatient() throws Exception{
        try {
            driver.findElement( By.xpath( "//*[@id='accButtonPatientCard']" )).click();
            new Actions( driver ).pause( Duration.ofSeconds(1)).perform();
            driver.findElement(By.id("buttonCard")).click();
            new Actions( driver ).pause( Duration.ofSeconds(1)).perform();
            driver.findElement(By.className("btn-outline-dark")).click();
            new Actions( driver ).pause( Duration.ofSeconds(1)).perform();

            driver.findElement( By.xpath( "//*[@id='diagnosis']" )).click();
            driver.findElement( By.xpath( "//*[@id='diagnosis']" )).sendKeys( cardPatient.getDiagnosis());
            driver.findElement(By.xpath("//*[@id='savePatient']" )).click();
            new Actions( driver ).pause( Duration.ofSeconds(2)).perform();

            WebElement allergySelect =  driver.findElement( By.xpath( "//*[@id='allergy']" ));
            Select select = new Select(allergySelect);
            if( cardPatient.getAllergy() == true  ){
                select.selectByValue("True");
            }else{
                select.selectByValue("False");
            }
            driver.findElement(By.xpath("//*[@id='savePatient']" )).click();
            new Actions( driver ).pause( Duration.ofSeconds(2)).perform();

            driver.findElement( By.xpath( "//*[@id='note']" )).click();
            driver.findElement( By.xpath( "//*[@id='note']" )).sendKeys( cardPatient.getNote() );
            driver.findElement(By.xpath("//*[@id='savePatient']" )).click();
            new Actions( driver ).pause( Duration.ofSeconds(2)).perform();

            driver.findElement( By.xpath( "//*[@id='сonclusion']" )).click();
            driver.findElement( By.xpath( "//*[@id='сonclusion']" )).sendKeys( cardPatient.getСonclusion() );
            driver.findElement(By.xpath("//*[@id='savePatient']" )).click();
            new Actions( driver ).pause( Duration.ofSeconds(2)).perform();

            driver.findElement( By.xpath( "//*[@id='idPatient']" )).click();
            driver.findElement( By.xpath( "//*[@id='idPatient']" )).sendKeys( lisIdPatients.stream()
                                                                                           .findFirst()
                                                                                           .orElseThrow()
                                                                                           .toString() );
            driver.findElement(By.xpath("//*[@id='savePatient']" )).click();
            new Actions( driver ).pause( Duration.ofSeconds(2)).perform();
            
        } catch (Exception e) {
            System.out.println( "ERROR " + e.getMessage() );
        }

    }
    
}
