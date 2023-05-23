package com.klinik.service;

import com.klinik.entity.Doctor;
import com.klinik.repositories.DoctorRerository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class DoctorService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private DoctorRerository repository;

    public List<Doctor> allDoctor() throws Exception{
        return repository.findAll();
    }

    public List<Doctor> findByFIO(String word ) throws Exception{
        return repository.findDoctorByFIO( word );
    }

    public Doctor findById( Long id) throws Exception{
        return ( Doctor ) em.createQuery( "SELECT u FROM Doctor u WHERE u.id_doctor = :id")
                            .setParameter( "id", id)
                            .getResultList()
                            .stream().findFirst().orElse( null );
    }
}
