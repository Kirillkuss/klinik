package com.klinik.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import java.util.List;
import java.util.Optional;
import com.klinik.entity.Complaint;
import com.klinik.repositories.ComplaintRepository;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;

@Owner(value = "Barysevich K. A.")
@Epic(value = "Тестирование сервиса ComplaintService")
@DisplayName( value = "Тестирование сервиса ComplaintService")
public class ComplaintServiceTest {

    @Mock private ComplaintRepository complaintRepository;

          private ComplaintService complaintService;

    private final String TYPE   = "application/json";
    private final String RESULT = "Результат: ";
    private final String ERROR  = "Ожидаемая ошибка: ";

    private Complaint complaint;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        complaintService = new ComplaintService( complaintRepository );
        complaint = new Complaint( 1L, "complaint");
    }
    
    @Test
    @DisplayName("Получение списка всех жалоб")
    public void testListComplaints(){
        Mockito.when( complaintRepository.findAll() ).thenReturn( List.of( complaint ));
        assertNotNull( complaintService.listComplaints() );
        assertEquals( complaintService.listComplaints(), complaintService.listComplaints() );
        Allure.addAttachment( RESULT, TYPE, complaintService.listComplaints().toString() );
        Mockito.verify( complaintRepository, times( 4 )).findAll();
    }
    @Test
    @DisplayName("Сохранение жалобы")
    public void testSaveComplaint() throws Exception{
        Allure.parameter("complaint", complaint );
        Mockito.when( complaintRepository.findById( complaint.getIdComplaint() )).thenReturn( Optional.empty() );
        Mockito.when( complaintRepository.findByName( complaint.getFunctionalImpairment())).thenReturn( Optional.empty() );
        Mockito.when( complaintRepository.save( complaint )).thenReturn(  complaint );
        assertNotNull( complaintService.saveСomplaint( complaint ));
        Allure.addAttachment( RESULT, TYPE, complaintService.saveСomplaint( complaint ).toString() );
        Mockito.verify( complaintRepository, times( 2 )).save( complaint );
    }

    @Test
    @DisplayName("Сохранение жалобы - вызов ошибок")
    public void testSaveComplaintError() throws Exception{
        Allure.parameter("complaint", complaint );
        Mockito.when( complaintRepository.findById( complaint.getIdComplaint() )).thenReturn( Optional.of( complaint ));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () ->  complaintService.saveСomplaint( complaint ));
        assertEquals("Справочник жалоба с таким ИД уже существует", exception.getMessage());
        Allure.addAttachment( ERROR, TYPE, exception.getMessage() );

        Mockito.when( complaintRepository.findById( complaint.getIdComplaint() )).thenReturn( Optional.empty() );
        Mockito.when( complaintRepository.findByName( complaint.getFunctionalImpairment())).thenReturn( Optional.of( complaint ));
        IllegalArgumentException exceptionTwo = assertThrows(IllegalArgumentException.class,
        () ->  complaintService.saveСomplaint( complaint ));
        assertEquals("Справочник жалоба с таким наименованием уже существует", exceptionTwo.getMessage());
        Allure.addAttachment( ERROR, TYPE, exceptionTwo.getMessage() );
    }
   
}
