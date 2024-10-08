package com.klinik.controller.model;

import com.klinik.entity.Patient;
import com.klinik.excep.MyException;
import com.klinik.repositories.PatientRepository;
import com.klinik.rest.model.IPatient;
import com.klinik.service.PatientService;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PatientController implements IPatient{

    private final PatientService  patientService;
    private final PatientRepository patientRepository;
    
    public ResponseEntity<List<Patient>> getAllPatients() throws Exception, MyException{
        return new ResponseEntity<>(patientService.getAllPatients(), HttpStatus.OK );
    }
    public ResponseEntity<Patient> addPatient(Patient patient, Long id) throws Exception, MyException{
        return new ResponseEntity<>( patientService.addPatient( patient, id ), HttpStatus.CREATED );
    }
    public ResponseEntity<List<Patient>> findByWord( String word ) throws Exception, MyException{
        return new ResponseEntity<>( patientService.findByWord( word ), HttpStatus.OK );
    }
    @Override
    public ResponseEntity<List<Patient>> getLazyLoad(int page, int size) {
        return new ResponseEntity<>( patientService.getLazyLoad( page, size ), HttpStatus.OK );
    }
    @Override
    public ResponseEntity<Long> getCountPatient() {
        return new ResponseEntity<>( patientRepository.count(), HttpStatus.OK );
    }

 }
