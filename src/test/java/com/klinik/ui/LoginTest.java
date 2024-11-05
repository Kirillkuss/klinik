package com.klinik.ui;

import java.time.Duration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

@Disabled
@DisplayName("Тесирование UI - Вход в систему")
public class LoginTest {

    private final String LOGIN = "admin";
    private final String PASSWORD = "admin";
    private WebDriver driver;


    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "D:/chromedriver-win64/chromedriver.exe");
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
            new Actions( driver ).pause( Duration.ofSeconds(3)).perform();
        }catch( Exception ex ){
            System.out.println( "ERROR " + ex.getMessage() );
        }
    }

    @Test
    @DisplayName("Успешный вход в систему")
    public void testLoginSuccess() throws Exception{
        getLoginTest( LOGIN, PASSWORD );
    }

    @ParameterizedTest
    @CsvSource({"test, test"})
    @DisplayName("Неудачный вход в систему")
    public void testLoginUserFailure(String errorUser, String errorPassword){
        getLoginTest(errorUser, errorPassword );
    }

    @ParameterizedTest
    @CsvSource({"cBEUs8fR, ksjkffdsdg"})
    @DisplayName("Блокировка пользователя - выполнять когда пользователь разблокирован")
    public void testBlockUser(String user, String errorPassword){
        getLoginTest( user, errorPassword );
        getLoginTest( user, errorPassword );
        getLoginTest( user, errorPassword );
    }

    @Test
    @DisplayName("Проверка на заполняемость полей")
    public void testCheckEmptyFieldss(){
        getLoginTest( null, null );
        getLoginTest( null, "login" );
        driver.findElement( By.xpath( "//*[@id='password']" )).clear();
        getLoginTest( "login", null );

        new Actions( driver ).pause( Duration.ofSeconds(3)).perform();
        driver.findElement( By.xpath( "//*[@id='username']" )).clear();
        getLoginTest( "login", "login" );
    }
    
}
