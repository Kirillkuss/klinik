package com.klinik.service;

import com.klinik.entity.Record_patient;
import com.klinik.repositories.RecordPatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordPatientService {

    @Autowired
    private RecordPatientRepository repository;

    public List<Record_patient> allListRecordPatient() throws Exception{
        return repository.findAll();
    }

    public Record_patient saveRecordPatient( Record_patient record_patient ) throws Exception{
        return repository.save( record_patient );
    }
}
