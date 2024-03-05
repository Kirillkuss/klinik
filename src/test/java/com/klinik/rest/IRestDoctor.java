package com.klinik.rest;

import org.junit.jupiter.api.DisplayName;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;

public interface IRestDoctor {

    @Feature("Получение списка врачей")
    @Description("Получение списка врачей")
    @DisplayName("Вызов метода GET: http://localhost:8082/web/doctors?page=page&size=size")
    @Link(name = "swagger", url = "http://localhost:8082/web/swagger-ui/index.html#/1.%20Doctors/getAllDoc")
    public void testGetAllDocuments( int page, int size );
    
}
