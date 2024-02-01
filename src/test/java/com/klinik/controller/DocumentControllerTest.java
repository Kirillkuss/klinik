package com.klinik.controller;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.klinik.entity.Document;
import com.klinik.service.DocumentService;

@DisplayName( "Класс предназначен для тестирования конторллера DocumentController")
@WebMvcTest(DocumentController.class)
public class DocumentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DocumentService service;

    List<Document> listDocument = new ArrayList<>();

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @DisplayName( "Получение списка всех пользователей")
    @Test
    public void testGetAllDocuments() throws Exception{
        Mockito.when( service.getAllDocuments()).thenCallRealMethod();
        //given(service.getAllDocuments()).willReturn(listDocument);
       // mvc.perform( get("/documents/list").contentType(MediaType.APPLICATION_JSON)).andExpect( status().isOk());
        //given(service.getAllDocuments()).willReturn(listDocument);
        //Mockito.when( service.getAllDocuments()).thenReturn(listDocument);
    }
    
}
