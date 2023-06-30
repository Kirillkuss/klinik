package com.klinik.service;

import com.klinik.entity.Patient;
import com.klinik.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> getAllPatients() throws Exception{
        return patientRepository.findAll();
    }

    public Patient addPatient( Patient patient ) throws Exception{
        return patientRepository.save( patient );
    }

    public List<Patient> findByWord( String word ) throws Exception{
        return patientRepository.findPatientByWord( word );
    }

    public Patient findById( Long id) throws Exception{
        return patientRepository.findByIdPatinet( id );
    }

    public Patient findByIdDocument( Long id_document ) throws Exception{
        return patientRepository.findPatientByIdDocument(id_document);
    }

    public Patient findByPhone( String phone ) throws Exception{
        return patientRepository.findByPhone(phone);
    }
}
