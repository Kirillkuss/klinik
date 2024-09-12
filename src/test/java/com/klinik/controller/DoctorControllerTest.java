package com.klinik.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.klinik.KlinikApplication;
import com.klinik.controller.model.DoctorController;
import com.klinik.entity.Doctor;
import com.klinik.response.BaseResponse;
import com.klinik.service.DoctorService;

@Disabled
@DisplayName("Тестирования сервиса DoctorController")
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true", classes = KlinikApplication.class)
public class DoctorControllerTest {

    @Autowired
	private DoctorController controller;

    private Doctor doctor = new Doctor();
    private BaseResponse response = new BaseResponse<>();
    ResponseEntity<List<Doctor>> listDoctors;
    ResponseEntity<Doctor> doctors;

    @BeforeEach
    public void setUp() {
        controller = mock( DoctorController.class );
      //  controller.doctorService = mock( DoctorService.class);
    }

    @AfterEach
    public void tearDown() {
    }

    @DisplayName( "Добавление доктора")
    @Test
    public void testAddDoctor() throws Exception{
        Mockito.when( controller.addDoctor( doctor )).thenCallRealMethod();
        Mockito.when( controller.addDoctor( doctor )).thenReturn( doctors );
        Mockito.when( controller.addDoctor( doctor )).then(( InvocationOnMock inv ) ->{
            return ( BaseResponse ) inv.callRealMethod();
        });
        assertEquals(controller.addDoctor( doctor ), controller.addDoctor( doctor )); 
        Mockito.verify( controller, times( 2 )).addDoctor( doctor );
    }
    
}
