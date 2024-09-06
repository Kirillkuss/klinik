package com.klinik.ui;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.klinik.entity.Doctor;
import com.klinik.rest.RestToken;

@DisplayName("Добавление доктора")
public class AddDoctorTest {

    private final String LOGIN    = "admin";
    private final String PASSWORD = "admin";
    private WebDriver driver;
    private Doctor doctor;

    
    @BeforeEach
    public void setUp() {
        doctor = RestToken.getDoctor();
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
    @DisplayName("Добавление врача")
    public void testAddDocument(){
        getLoginTest( LOGIN, PASSWORD );
        try{
            //driver.findElement( By.xpath( "//*[@id='accButtonOne']" )).click(); work
            driver.findElement( By.xpath( "//*[@id='accButtonDoctor']" )).click();
            new Actions( driver ).pause( Duration.ofSeconds(1)).perform();
            driver.findElement(By.id("buttonDoctor")).click();
            new Actions( driver ).pause( Duration.ofSeconds(1)).perform();
            driver.findElement(By.className("btn-outline-dark")).click();
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
