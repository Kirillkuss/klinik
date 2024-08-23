package com.klinik.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.klinik.entity.TypeComplaint;
import com.klinik.repositories.ComplaintRepository;
import com.klinik.repositories.TypeComplaintRepository;
import java.util.ArrayList;
import java.util.List;

@DisplayName("Тестирование сервиса TypeComplaintService")
public class TypeComplaintServiceTest {

    @Mock
    private ComplaintRepository complaintRepository;
    @Mock
    private TypeComplaintRepository typeComplaintRepository;
    @InjectMocks
    private TypeComplaintService typeComplaintService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Тестирование метода typeComplaintService.findAll()")
    public void testFindAlTypeComplaint() {
        TypeComplaint typeComplaint = new TypeComplaint();
        typeComplaint.setName("TypeComplaintName");
        when(typeComplaintRepository.findAll()).thenReturn(List.of(typeComplaint));
        List<TypeComplaint> result = typeComplaintService.findAll();

        System.out.println("result >>> " + result );
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("TypeComplaintName", result.get(0).getName());
    }
    
}
