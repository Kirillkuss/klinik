package com.klinik.ui;

import java.time.Duration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import com.klinik.entity.Document;
import com.klinik.rest.RestToken;

@Disabled
@DisplayName("Тесирование UI - Добавление документа")
public class AddDocumentTest {

    private final String LOGIN    = "admin";
    private final String PASSWORD = "admin";
    private WebDriver driver;
    private Document document;

    @BeforeEach
    public void setUp() {
        document = RestToken.getDocument();

        System.setProperty("webdriver.chrome.driver", "D:/chromedriver/chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(chromeOptions);
        try{
            driver.get( "http://localhost:8082/web/login" );
            driver.manage().window().maximize();
            new Actions(driver).pause( Duration.ofSeconds(2)).perform();
        }catch( Exception ex ){
            System.out.println( "ERROR BEFORE >>> " + ex.getMessage() );
        }
    }
    
    @AfterEach
    public void tearDown() {
        new Actions(driver).pause( Duration.ofSeconds(2)).perform();
        driver.quit();
    }

    @DisplayName("Вход в систему")
    private void getLoginTest( String login, String password ){
        try{
            driver.findElement( By.xpath( "//*[@id='username']" )).click();
            if ( login != null ) driver.findElement( By.xpath( "//*[@id='username']" )).sendKeys( login );
            driver.findElement( By.xpath( "//*[@id='password']" )).click();
            if( password != null ) driver.findElement( By.xpath( "//*[@id='password']" )).sendKeys( password );
            driver.findElement(By.xpath("//button[text()='Войти']")).click();
            new Actions( driver ).pause( Duration.ofSeconds(2)).perform();
        }catch( Exception ex ){
            System.out.println( "ERROR " + ex.getMessage() );
        }  
    }

    @Test
    public void testAddDocument(){
        getLoginTest( LOGIN, PASSWORD );
        try{
            driver.findElement(By.className("btn-outline-dark")).click();

            driver.findElement( By.xpath( "//*[@id='idDocument']" )).click();
            driver.findElement( By.xpath( "//*[@id='idDocument']" )).sendKeys( document.getIdDocument().toString() );
            driver.findElement(By.xpath("//*[@id='addDocumentTwo']" )).click();
            new Actions( driver ).pause( Duration.ofSeconds(2)).perform();

            driver.findElement( By.xpath( "//*[@id='typeDocument']" )).click();
            driver.findElement( By.xpath( "//*[@id='typeDocument']" )).sendKeys( document.getTypeDocument() );
            driver.findElement(By.xpath("//*[@id='addDocumentTwo']" )).click();
            new Actions( driver ).pause( Duration.ofSeconds(2)).perform();

            driver.findElement( By.xpath( "//*[@id='seria']" )).click();
            driver.findElement( By.xpath( "//*[@id='seria']" )).sendKeys( document.getSeria() );
            driver.findElement(By.xpath("//*[@id='addDocumentTwo']" )).click();
            new Actions( driver ).pause( Duration.ofSeconds(2)).perform();

            driver.findElement( By.xpath( "//*[@id='numar']" )).click();
            driver.findElement( By.xpath( "//*[@id='numar']" )).sendKeys( document.getNumar() );
            driver.findElement(By.xpath("//*[@id='addDocumentTwo']" )).click();
            new Actions( driver ).pause( Duration.ofSeconds(2)).perform();

            driver.findElement( By.xpath( "//*[@id='snils']" )).click();
            driver.findElement( By.xpath( "//*[@id='snils']" )).sendKeys( document.getSnils() );
            driver.findElement(By.xpath("//*[@id='addDocumentTwo']" )).click();
            new Actions( driver ).pause( Duration.ofSeconds(2)).perform();

            driver.findElement( By.xpath( "//*[@id='polis']" )).click();
            driver.findElement( By.xpath( "//*[@id='polis']" )).sendKeys( document.getPolis() );
            driver.findElement(By.xpath("//*[@id='addDocumentTwo']" )).click();
            new Actions( driver ).pause( Duration.ofSeconds(2)).perform();

            /**driver.findElement( By.xpath( "//*[@id='idDocument']" )).click();
            driver.findElement( By.xpath( "//*[@id='idDocument']" )).clear();
            driver.findElement( By.xpath( "//*[@id='idDocument']" )).sendKeys( document.getIdDocument().toString() );
            driver.findElement(By.xpath("//*[@id='addDocumentTwo']" )).click();
            new Actions( driver ).pause( Duration.ofSeconds(2)).perform();*/

        }catch( Exception ex ){
            System.out.println("ERROR AddDocumentTest >>> " + ex.getMessage() );
        }
    }
    
}
