package com.klinik.service;

import com.klinik.entity.Doctor;
import com.klinik.repositories.DoctorRerository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRerository repository;

    public List<Doctor> allDoctor() throws Exception{
        return repository.findAll();
    }

    public List<Doctor> findByFIO(String word ) throws Exception{
        return repository.findDoctorByFIO( word );
    }
}
