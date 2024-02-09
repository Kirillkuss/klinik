package com.klinik.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import com.klinik.entity.Complaint;
import com.klinik.excep.MyException;
import com.klinik.repositories.ComplaintRepository;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;

@DisplayName("Тестирование сервиса и репозитория для сущности жалобы")
@SpringBootTest( webEnvironment = WebEnvironment.RANDOM_PORT )
public class ComplaintServiceTest {

    @Autowired
    public ComplaintService complaintService;

    @Autowired
    public ComplaintRepository complaintRepository;

    Complaint complaint = new Complaint();

    @DisplayName( "Получение списка жалоб")
    @Description( "Получение списка жалоб")
    @RepeatedTest( 7 )
    public void testListComplaints() throws Exception{
        assertNotNull( complaintService.listComplaints() );
        assertEquals( complaintService.listComplaints(), complaintService.listComplaints() );
        Allure.addAttachment("Результат:", "text/plain", complaintService.listComplaints().toString() );
    }

    @Test
    @DisplayName( "Добавить жалобу" ) 
    @Description( "Добавить жалобу" )
    public void testSaveСomplaint() throws Exception{
        assertThrows( InvalidDataAccessApiUsageException.class, () -> complaintService.saveСomplaint( complaint ));
        complaint = complaintRepository.findById( 1L ).orElse( null );
        assertThrows( MyException.class, () -> complaintService.saveСomplaint( complaint ) );
        complaint.setIdComplaint( -34354L);
        assertThrows( MyException.class, () -> complaintService.saveСomplaint( complaint ) );
    }
    
}
