package com.klinik.ui.entity;

import java.time.Duration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import com.klinik.entity.Doctor;
import com.klinik.rest.RestToken;
import com.klinik.ui.LoginSuccess;

@Disabled
@DisplayName("Добавление доктора через ui")
public class AddDoctorTest {

    private WebDriver driver;
    private Doctor doctor;
    
    @BeforeEach
    public void setUp() {
        doctor = RestToken.getDoctor();
        driver = LoginSuccess.getSuccessLogin();
    }

    @AfterEach
    public void tearDown() {
        new Actions(driver).pause( Duration.ofSeconds(2)).perform();
        driver.quit();
    }

    @Test
    @DisplayName("Добавление врача")
    public void testAddDoctor(){
        try{
            driver.findElement( By.xpath( "//*[@id='accButtonDoctor']" )).click();
            new Actions( driver ).pause( Duration.ofSeconds(1)).perform();
            driver.findElement(By.id("buttonDoctor")).click();
            new Actions( driver ).pause( Duration.ofSeconds(1)).perform();
            driver.findElement(By.xpath("//button[@data-bs-target='#exampleModal']")).click();
            new Actions( driver ).pause( Duration.ofSeconds(1)).perform();

            driver.findElement( By.xpath( "//*[@id='surname']" )).click();
            driver.findElement( By.xpath( "//*[@id='surname']" )).sendKeys( doctor.getSurname() );
            driver.findElement(By.xpath("//*[@id='saveDoctor']" )).click();
            new Actions( driver ).pause( Duration.ofSeconds(2)).perform();

            driver.findElement( By.xpath( "//*[@id='name']" )).click();
            driver.findElement( By.xpath( "//*[@id='name']" )).sendKeys( doctor.getName());
            driver.findElement(By.xpath("//*[@id='saveDoctor']" )).click();
            new Actions( driver ).pause( Duration.ofSeconds(2)).perform();

            driver.findElement( By.xpath( "//*[@id='fullName']" )).click();
            driver.findElement( By.xpath( "//*[@id='fullName']" )).sendKeys( doctor.getFullName() );
            driver.findElement(By.xpath("//*[@id='saveDoctor']" )).click();
            new Actions( driver ).pause( Duration.ofSeconds(2)).perform();

        }catch( Exception ex ){
            System.out.println( "ERROR " + ex.getMessage() );
        }    
        }
}
