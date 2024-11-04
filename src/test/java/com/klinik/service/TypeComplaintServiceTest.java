package com.klinik.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.klinik.entity.Complaint;
import com.klinik.entity.TypeComplaint;
import com.klinik.repositories.ComplaintRepository;
import com.klinik.repositories.TypeComplaintRepository;
import com.klinik.request.RequestTypeComplaint;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import java.util.Optional;
import java.util.List;

@Owner(value = "Barysevich K. A.")
@Epic(value = "Тестирование сервиса TypeComplaintService")
@DisplayName( value = "Тестирование сервиса TypeComplaintService")
public class TypeComplaintServiceTest {

    private final String NAME   = "TypeComplaintFirst";
    private final String TYPE   = "application/json";
    private final String RESULT = "Результат: ";

    @Mock        private ComplaintRepository     complaintRepository;
    @Mock        private TypeComplaintRepository typeComplaintRepository;
    @InjectMocks private TypeComplaintService    typeComplaintService;

    private Complaint            complaint;
    private TypeComplaint        typeComplaint;
    private RequestTypeComplaint requestTypeComplaint;
     

    @BeforeEach
    public void setUp() {
        complaint            = new Complaint(1L, "Симптомы поражения пирамидного тракта");
        typeComplaint        = new TypeComplaint(1L, NAME, null);
        requestTypeComplaint = new RequestTypeComplaint( NAME, 1L );
        MockitoAnnotations.openMocks(this);;
    }

    @Test
    @DisplayName("Поиск всех под жалоб")
    public void testFindAll(){
        when( typeComplaintRepository.findAll() ).thenReturn (List.of( typeComplaint ));
        List<TypeComplaint> result = typeComplaintService.findAll();
        Allure.addAttachment(RESULT, TYPE, result.toString() );
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals( NAME, result.get(0).getName());
    }

    @Test
    @DisplayName("Сохранение под жалобы")
    public void testSaveTypeComplaintSuccess() throws Exception {
        Allure.parameter( "requestTypeComplaint" , requestTypeComplaint );
        when( complaintRepository.findById(1L )).thenReturn( Optional.of( complaint ));
        when( typeComplaintRepository.findName( NAME )).thenReturn( Optional.empty() );
        when( typeComplaintRepository.findById( -1L )).thenReturn( Optional.empty() );
        when( typeComplaintRepository.save( any(TypeComplaint.class ))).thenAnswer( inv -> inv.getArgument(0 ));
        TypeComplaint savedTypeComplaint = typeComplaintService.saveTypeComplaint( requestTypeComplaint );
        Allure.addAttachment(RESULT, TYPE, savedTypeComplaint.toString() );
        assertNotNull(savedTypeComplaint);
        assertEquals( NAME, savedTypeComplaint.getName());
        assertEquals( complaint, savedTypeComplaint.getComplaint() );

    }
    @Test
    @DisplayName("Сохранение под жалобы - проверка на ошибка")
    public void testSaveTypeComplaintWithInvalidComplaintId() {
        String ERROR = "Ожидаемая ошибка :";
        Allure.parameter( "requestTypeComplaint" , requestTypeComplaint );
        //first Error
        when(complaintRepository.findById(1L)).thenReturn( Optional.empty() );
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> typeComplaintService.saveTypeComplaint( requestTypeComplaint ));
        assertEquals("Неверный параметр, жалоба с таким ИД не существует", exception.getMessage());
        Allure.addAttachment( ERROR, TYPE,exception.getMessage() );
        //second Error
        when( typeComplaintRepository.findName( NAME )).thenReturn( Optional.of( typeComplaint ));
        when( complaintRepository.findById( 1L )).thenReturn( Optional.of( complaint ));
        IllegalArgumentException exceptionTwo = assertThrows(IllegalArgumentException.class, () -> typeComplaintService.saveTypeComplaint( requestTypeComplaint ));
        Allure.addAttachment( ERROR, TYPE,exceptionTwo.getMessage() );
        assertEquals("Под жалоба с таким наименованием уже существует", exceptionTwo.getMessage());
    }
    
    @ParameterizedTest
    @CsvSource({"1"})
    @DisplayName("Поиск жалобы по ИД")
    public void testFindByIdComplaintSuccess( Long complaintId ) throws Exception {
        when( complaintRepository.findById( complaintId )).thenReturn(Optional.of( complaint ));
        when( typeComplaintRepository.findByIdComplaint( complaintId )).thenReturn(List.of( typeComplaint ));
        List<TypeComplaint> result = typeComplaintService.findByIdComplaint(complaintId);
        Allure.addAttachment(RESULT, TYPE, result.toString() );
        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
