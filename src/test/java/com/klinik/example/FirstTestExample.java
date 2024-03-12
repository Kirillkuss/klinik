package com.klinik.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.qameta.allure.Allure;
import io.qameta.allure.Param;

@DisplayName("class FirstTestExample")
public class FirstTestExample {

       @Test
       @DisplayName("Test Method")
       public void testMethod() {
           String parameter = "HELLO MOUSE TEST";
           Allure.parameter( "testMethod parameter ", parameter );
           System.out.println( parameter );
       }
    
}
