package com.klinik.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.boot.test.context.SpringBootTest;
import com.klinik.entity.TypeComplaint;
import com.klinik.excep.MyException;
import com.klinik.repositories.ComplaintRepository;
import com.klinik.repositories.TypeComplaintRepository;

@SpringBootTest
public class TypeComplaintTest {

    private List<TypeComplaint> listComplaints = new ArrayList<>();

    @InjectMocks 
    private TypeComplaintService typeComplaintService;

    @Mock
    private TypeComplaintRepository typeComplaintRepository;
    @Mock
    private ComplaintRepository complaintRepository;

    @BeforeEach
    public void setup(){
    }

    @Test
    public void testFindById() throws Exception{
        assertThrows( MyException.class, () ->{
            Mockito.when( typeComplaintService.findByIdComplaint( 1L )).thenReturn( listComplaints );
            Mockito.when( typeComplaintService.findByIdComplaint( 1L )).then(( InvocationOnMock inv ) ->{
                return ( List<TypeComplaint> ) inv.callRealMethod();
            });
        });
    }

    
}
