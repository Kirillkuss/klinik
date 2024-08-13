package com.klinik.service;

import com.klinik.entity.RecordPatient;
import com.klinik.excep.MyException;
import com.klinik.repositories.CardPatientRepository;
import com.klinik.repositories.DoctorRerository;
import com.klinik.repositories.RecordPatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RecordPatientService {

    private final RecordPatientRepository recordPatientRepository;
    private final DoctorRerository        doctorRerository;
    private final CardPatientRepository   cardPatientRepository;
    
    public List<RecordPatient> findAll() {
        return recordPatientRepository.findAll();
    }
    public RecordPatient saveRecordPatient( RecordPatient recordPatient, Long idDoctor, Long idCardPatient ) throws Exception{
        if ( recordPatientRepository.findById( recordPatient.getIdRecord()).isPresent()) throw new MyException( 409, "Запись к врачу с таким ИД уже существует, установите другой ИД записи к врачу");
        recordPatient.setDoctor( doctorRerository.findById( idDoctor ).orElseThrow(() -> new NoSuchElementException( "Указан неверный идентификатор доктора") ) );
        recordPatient.setCardPatientId( cardPatientRepository.findById( idCardPatient ).map( s -> s.getIdCardPatient()).orElseThrow(() -> new NoSuchElementException( "Указан неверный идентификатор карты пациента") ));
        return recordPatientRepository.save( recordPatient );
    }
    public List<RecordPatient> findByParam( Long id, LocalDateTime dateFrom, LocalDateTime dateTo ) throws Exception{
     return recordPatientRepository.findByParamTwo(id, dateFrom, dateTo);
    }
}
