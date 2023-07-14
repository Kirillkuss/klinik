package com.klinik.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import com.klinik.entity.Document;
import com.klinik.repositories.DocumentRepository;
import com.klinik.service.DocumentService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;

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
