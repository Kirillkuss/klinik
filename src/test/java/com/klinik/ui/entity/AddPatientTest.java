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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import com.klinik.entity.Gender;
import com.klinik.entity.Patient;
import com.klinik.rest.RestSession;
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
        patient = RestSession.getPatient();
        driver = LoginSuccess.getSuccessLogin();
    }

    @AfterEach
    public void tearDown() {
        new Actions(driver).pause( Duration.ofSeconds(2)).perform();
        driver.quit();
    }

    @Test
    @DisplayName("Добавление пациента")
    public void testAddPatient() throws Exception{
        List<Long> listDocumentId = RestSession.getStremValue( queryDocument, "id_document");
        if ( listDocumentId.stream().count() < 2 ) RestSession.getStremValue( RestSession.getAddDocumentQuery( RestSession.getDocument() ), "id_document");
        try{
            driver.findElement( By.xpath( "//*[@id='accButtonPatient']" )).click();
            new Actions( driver ).pause( Duration.ofSeconds(1)).perform();
            driver.findElement(By.id("buttonPatient")).click();
            new Actions( driver ).pause( Duration.ofSeconds(1)).perform();
            driver.findElement(By.xpath("//button[@data-bs-target='#exampleModal']")).click();
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

            WebElement genderSelect =  driver.findElement( By.xpath( "//*[@id='gender']" ));
            Select select = new Select(genderSelect);
            if( patient.getGender() == Gender.MAN ){
                select.selectByValue("MAN");
            }else{
                select.selectByValue("WOMAN");
            }
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
