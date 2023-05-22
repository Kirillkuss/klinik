package com.klinik.service;

import com.klinik.entity.Document;
import com.klinik.entity.Patient;
import com.klinik.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class PatientService {

    @PersistenceContext
    EntityManager em;


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

    public Patient findById( Long id) throws Exception{
        return (Patient) em.createQuery(" SELECT u FROM Patient u where u.id_patient = :id")
                            .setParameter( "id", id)
                            .getResultList().stream().findFirst().orElse( null );
    }

    public Patient findByIdDocument( Long id ) throws Exception{
        return repository.findPatientByIdDocument( id );
    }

}
