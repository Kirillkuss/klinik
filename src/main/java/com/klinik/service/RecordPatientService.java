package com.klinik.service;

import com.klinik.entity.RecordPatient;
import com.klinik.repositories.CardPatientRepository;
import com.klinik.repositories.DoctorRerository;
import com.klinik.repositories.RecordPatientRepository;
import com.klinik.request.RequestRecordPatient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecordPatientService {

    private final RecordPatientRepository recordPatientRepository;
    private final DoctorRerository        doctorRerository;
    private final CardPatientRepository   cardPatientRepository;
    
    public List<RecordPatient> findAll() {
        return recordPatientRepository.findAll();
    }
    
    public RecordPatient saveRecordPatient( RequestRecordPatient requestRecordPatient  ) throws Exception{
        RecordPatient recordPatient = new RecordPatient();
        if ( requestRecordPatient.getDateAppointment().isBefore( requestRecordPatient.getDateRecord()) ) throw new IllegalArgumentException( "Дата приема не может быть раньше даты записи");
        recordPatient.setDateRecord( requestRecordPatient.getDateRecord() );
        recordPatient.setDateAppointment(requestRecordPatient.getDateAppointment());
        recordPatient.setNumberRoom(requestRecordPatient.getNumberRoom());
        recordPatient.setDoctor( doctorRerository.findById( requestRecordPatient.getIdDoctor() ).orElseThrow(() -> new NoSuchElementException( "Указан неверный идентификатор доктора") ) );
        recordPatient.setCardPatientId( cardPatientRepository.findById( requestRecordPatient.getIdCardPatient() ).map( s -> s.getIdCardPatient()).orElseThrow(() -> new NoSuchElementException( "Указан неверный идентификатор карты пациента") ));
        log.info("saveRecordPatient >>>>");
        return recordPatientRepository.save( recordPatient );
    }
    public List<RecordPatient> findByParam( Long id, LocalDateTime dateFrom, LocalDateTime dateTo ) throws Exception{
        log.info("findByParam RecordPatient >>>> ");
     return recordPatientRepository.findByParamTwo(id, dateFrom, dateTo);
    }
}
