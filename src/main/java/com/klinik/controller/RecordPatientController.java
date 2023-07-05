package com.klinik.controller;

import com.klinik.entity.Record_patient;
import com.klinik.excep.MyException;
import com.klinik.rest.IRecordPatinet;
import com.klinik.service.RecordPatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecordPatientController implements IRecordPatinet {

    private final RecordPatientService recordPatientService;
    public ResponseEntity<List<Record_patient>> allListRecordPatient() throws Exception, MyException{
        return new ResponseEntity<>( recordPatientService.allListRecordPatient(), HttpStatus.OK );
    }
    public ResponseEntity addRecordPatient( Record_patient record_patient, Long doctor_id, Long card_patient_id ) throws Exception, MyException{
        return new ResponseEntity<>( recordPatientService.saveRecordPatient( record_patient,  doctor_id, card_patient_id ), HttpStatus.CREATED );                
    }
    public ResponseEntity<List<Record_patient>> findByParams( Long id, LocalDateTime dateFrom, LocalDateTime dateTo ) throws Exception, MyException{
        return new ResponseEntity<>( recordPatientService.findByParam( id, dateFrom, dateTo ), HttpStatus.OK );
    } 
}
