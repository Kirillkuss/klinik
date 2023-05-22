package com.klinik.service;

import com.klinik.entity.Patient;
import com.klinik.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class PatientService {

    private EntityManager em;

    @Autowired
    private PatientRepository repository;

    public List<Patient> getAllPatients() throws Exception{
        return repository.findAll();
    }

    public Patient addPatient( Patient patient ) throws Exception{
        return repository.save( patient );
    }

    public List<Patient> findByWord( String word ) throws Exception{
        return repository.findPatientByWord( word );
    }
}
