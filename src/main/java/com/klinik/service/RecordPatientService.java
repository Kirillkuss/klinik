package com.klinik.service;

import com.klinik.entity.Record_patient;
import com.klinik.repositories.RecordPatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class RecordPatientService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private RecordPatientRepository recordPatientRepository;

    public List<Record_patient> allListRecordPatient() throws Exception{
        return recordPatientRepository.findAll();
    }

    public Record_patient saveRecordPatient( Record_patient record_patient ) throws Exception{
        return recordPatientRepository.save( record_patient );
    }

    public Record_patient findById( Long id ) throws Exception{
        return recordPatientRepository.findByIdRecordPatient( id );
    }

    public List<Record_patient> findByParam( Long id, LocalDateTime dateFrom, LocalDateTime dateTo ) throws Exception{
     return recordPatientRepository.findByParamTwo(id, dateFrom, dateTo);
    }
}
