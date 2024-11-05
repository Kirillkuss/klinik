package com.klinik.ui;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

public class LoginSuccess {
    
    public static WebDriver getSuccessLogin(){
        WebDriver driver; 
        try{
            System.setProperty("webdriver.chrome.driver", "D:/chromedriver-win64/chromedriver.exe");
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--remote-allow-origins=*");
            driver = new ChromeDriver(chromeOptions);
            driver.get( "http://localhost:8082/web/login" );
            driver.manage().window().maximize();
            new Actions(driver).pause( Duration.ofSeconds(2)).perform();
            driver.findElement( By.xpath( "//*[@id='username']" )).click();
            driver.findElement( By.xpath( "//*[@id='username']" )).sendKeys( "admin" );
            driver.findElement( By.xpath( "//*[@id='password']" )).click();
            driver.findElement( By.xpath( "//*[@id='password']" )).sendKeys( "admin" );
            driver.findElement(By.xpath("//button[text()='Войти']")).click();
            new Actions( driver ).pause( Duration.ofSeconds(2)).perform();
            return driver;
        }catch( Exception ex ){
            System.setProperty("webdriver.chrome.driver", "D:/chromedriver-win64/chromedriver.exe");
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--remote-allow-origins=*");
            driver = new ChromeDriver(chromeOptions);
            return driver;
        }  
    }
}
