package com.klinik.controller;

import com.klinik.entity.Record_patient;
import com.klinik.excep.MyException;
import com.klinik.rest.IRecordPatinet;
import com.klinik.service.CardPatientService;
import com.klinik.service.DoctorService;
import com.klinik.service.RecordPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class RecordPatientController implements IRecordPatinet {

    @Autowired private RecordPatientService recordPatientService;
    @Autowired private DoctorService        serviceDoctor;
    @Autowired private CardPatientService   servicePatientCard;

    public ResponseEntity<List<Record_patient>> allListRecordPatient() throws Exception, MyException{
        return new ResponseEntity<>( recordPatientService.allListRecordPatient(), HttpStatus.OK );
    }
    public ResponseEntity<Record_patient> addRecordPatient( Record_patient record_patient, Long doctor_id, Long card_patient_id ) throws Exception, MyException{
        if ( serviceDoctor.findById( doctor_id ) == null )                          throw new MyException( 400, "Нет доктора с таким идентификатором");
        if ( servicePatientCard.findByIdCard( card_patient_id ) == null )           throw new MyException( 400, "Нет карты пациента с таким идентификатором");
        if ( recordPatientService.findById( record_patient.getId_record()) != null) throw new MyException( 409, "Запись к врачу с таким ИД уже существует, установите другой ИД записи к врачу");
        record_patient.setDoctor(serviceDoctor.findById( doctor_id ));;
        record_patient.setCard_patient_id( card_patient_id );
        return new ResponseEntity<>( recordPatientService.saveRecordPatient( record_patient), HttpStatus.CREATED );                
    }

    public ResponseEntity<List<Record_patient>> findByParams( Long id, LocalDateTime dateFrom, LocalDateTime dateTo ) throws Exception, MyException{
        return new ResponseEntity<>( recordPatientService.findByParam(id, dateFrom, dateTo), HttpStatus.OK );
    }

    
}
