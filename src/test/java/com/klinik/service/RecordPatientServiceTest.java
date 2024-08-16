package com.klinik.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import com.klinik.entity.CardPatient;
import com.klinik.entity.Doctor;
import com.klinik.entity.RecordPatient;
import com.klinik.repositories.CardPatientRepository;
import com.klinik.repositories.DoctorRerository;
import com.klinik.repositories.RecordPatientRepository;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
public class RecordPatientServiceTest {

    @Mock
    private RecordPatientRepository recordPatientRepository;

    @Mock
    private DoctorRerository doctorRepository;

    @Mock
    private CardPatientRepository cardPatientRepository;

    @InjectMocks
    private RecordPatientService recordPatientService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks( this );
    }

    @Test
    public void saveRecordPatient_ValidInput_ShouldSaveSuccessfully() throws Exception {
        // Arrange
        RecordPatient recordPatient = new RecordPatient();
        recordPatient.setCardPatientId( 1L );
        Long idDoctor = 1L;
        Long idCardPatient = 1L;
        Doctor doctor = new Doctor();
        CardPatient cardPatient = new CardPatient();

        when(recordPatientRepository.findById(recordPatient.getIdRecord())).thenReturn(Optional.empty());
        when(doctorRepository.findById(idDoctor)).thenReturn(Optional.of(doctor));
        when(cardPatientRepository.findById(idCardPatient)).thenReturn(Optional.of(cardPatient));
        when(cardPatientRepository.findById(idCardPatient)).thenReturn(Optional.of(cardPatient));
    }

    @Test
    public void testAllListRecordPatient() throws Exception{
        List<CardPatient> list = new ArrayList<>();
        Mockito.when( cardPatientRepository.findAll() ).thenReturn( list );
        Mockito.when( cardPatientRepository.findAll() ).then(( InvocationOnMock inv ) ->{
            return ( List<CardPatient> ) inv.callRealMethod();
        });
    }

}

