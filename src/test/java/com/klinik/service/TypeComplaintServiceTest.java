package com.klinik.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

import com.klinik.entity.Complaint;
import com.klinik.entity.TypeComplaint;
import com.klinik.repositories.ComplaintRepository;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;

@SpringBootTest( webEnvironment = WebEnvironment.RANDOM_PORT )
@Owner(value = "Barysevich K. A.")
@Epic(value = "Тестирование сервиса с под жалобами - TypeComplaintService")
@DisplayName( "Тестирование сервиса с под жалобами - TypeComplaintService" )
public class TypeComplaintServiceTest {

    @Autowired
    private TypeComplaintService typeComplaintService;

    @Autowired
    public ComplaintRepository complaintRepository;

    @Test
    @Feature( "Получение списка под жалоб" )
    @Description( "Получение списка под жалоб" )
    @DisplayName( "Получение списка под жалоб" )
    public void testFindAllTypeComplaint() throws Exception{
        assertNotEquals( typeComplaintService.findAll(), null );
        assertEquals( typeComplaintService.findAll(), typeComplaintService.findAll());
        Allure.addAttachment("Результат:", "application/json", typeComplaintService.findAll().toString() );
    }

    @Test
    @Transactional
    @Feature( "Добавление под жалобы" )
    @Description( "Добавление под жалобы" )
    @DisplayName( "Добавление под жалобы" )
    public void testSaveTypeComplaint() throws Exception{
        TypeComplaint typeComplaint = new TypeComplaint( -1L, "test" );
        TypeComplaint response = typeComplaintService.saveTypeComplaint( typeComplaint, 1L );
        Allure.addAttachment("Результат:", "application/json", response.toString() );
    }
    
    @Test
    @Feature( "Получение списка под жалом по ид жалобы" )
    @Description( "Получение списка под жалом по ид жалобы" )
    @DisplayName( "Получение списка под жалом по ид жалобы" )
    public void testFindByIdComplaint() throws Exception{
        List<TypeComplaint> list = typeComplaintService.findByIdComplaint( 1L );
        assertFalse( list.isEmpty() );
        assertEquals(  typeComplaintService.findByIdComplaint( 1L ), list );
        Allure.addAttachment("Результат:", "application/json", list.toString() );
    }

}
