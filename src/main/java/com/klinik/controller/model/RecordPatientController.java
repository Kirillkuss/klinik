package com.klinik.controller.model;

import com.klinik.entity.RecordPatient;
import com.klinik.excep.MyException;
import com.klinik.request.RequestRecordPatient;
import com.klinik.rest.model.IRecordPatinet;
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

    public ResponseEntity<List<RecordPatient>> allListRecordPatient(){
        return new ResponseEntity<>( recordPatientService.findAll(), HttpStatus.OK );
    }

    public ResponseEntity addRecordPatient( RequestRecordPatient requestRecordPatient ) throws Exception, MyException{
        
        return new ResponseEntity<>( recordPatientService.saveRecordPatient( requestRecordPatient ), HttpStatus.CREATED );                
    }
    
    public ResponseEntity<List<RecordPatient>> findByParams( Long id, LocalDateTime dateFrom, LocalDateTime dateTo ) throws Exception, MyException{
        return new ResponseEntity<>( recordPatientService.findByParam( id, dateFrom, dateTo ), HttpStatus.OK );
    } 
}
