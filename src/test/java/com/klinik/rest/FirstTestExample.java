package com.klinik.rest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;

@Owner(value = "Barysevich K. A.")
@Epic(value = "class FirstTestExample")
@DisplayName("class FirstTestExample")
public class FirstTestExample {

       @Test
       @Feature("Test Method")
       @DisplayName("Test Method")
       @Description("Test Method")
       public void testHowToDoParam() {
           String parameter = "HELLO MOUSE TEST";
           Allure.parameter( "testMethod parameter ", parameter );
           System.out.println( parameter );
           assertNotNull(parameter);
       }
    
}
