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
import com.klinik.entity.Document;
import com.klinik.rest.RestSession;
import com.klinik.ui.LoginSuccess;

@Disabled
@DisplayName("Тесирование UI - Добавление документа")
public class AddDocumentTest {

    private WebDriver driver;
    private Document document;

    @BeforeEach
    public void setUp() {
        document = RestSession.getDocument();
        driver = LoginSuccess.getSuccessLogin();

    }
    
    @AfterEach
    public void tearDown() {
        new Actions(driver).pause( Duration.ofSeconds(2)).perform();
        driver.quit();
    }


    @Test
    @DisplayName("Добавление доктора")
    public void testAddDocument(){
        try{
            driver.findElement(By.xpath("//button[@data-bs-target='#exampleModal']")).click();

            driver.findElement( By.xpath( "//*[@id='typeDocument']" )).click();
            driver.findElement( By.xpath( "//*[@id='typeDocument']" )).sendKeys( document.getTypeDocument() );
            driver.findElement(By.xpath("//*[@id='saveDocument']" )).click();
            new Actions( driver ).pause( Duration.ofSeconds(2)).perform();

            driver.findElement( By.xpath( "//*[@id='seria']" )).click();
            driver.findElement( By.xpath( "//*[@id='seria']" )).sendKeys( document.getSeria() );
            driver.findElement(By.xpath("//*[@id='saveDocument']" )).click();
            new Actions( driver ).pause( Duration.ofSeconds(2)).perform();

            driver.findElement( By.xpath( "//*[@id='numar']" )).click();
            driver.findElement( By.xpath( "//*[@id='numar']" )).sendKeys( document.getNumar() );
            driver.findElement(By.xpath("//*[@id='saveDocument']" )).click();
            new Actions( driver ).pause( Duration.ofSeconds(2)).perform();

            driver.findElement( By.xpath( "//*[@id='snils']" )).click();
            driver.findElement( By.xpath( "//*[@id='snils']" )).sendKeys( document.getSnils() );
            driver.findElement(By.xpath("//*[@id='saveDocument']" )).click();
            new Actions( driver ).pause( Duration.ofSeconds(2)).perform();

            driver.findElement( By.xpath( "//*[@id='polis']" )).click();
            driver.findElement( By.xpath( "//*[@id='polis']" )).sendKeys( document.getPolis() );
            driver.findElement(By.xpath("//*[@id='saveDocument']" )).click();
            new Actions( driver ).pause( Duration.ofSeconds(2)).perform();

        }catch( Exception ex ){
            System.out.println("ERROR AddDocumentTest >>> " + ex.getMessage() );
        }
    }
    
}
