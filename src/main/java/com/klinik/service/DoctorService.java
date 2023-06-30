package com.klinik.service;

import com.klinik.entity.Doctor;
import com.klinik.repositories.DoctorRerository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DoctorService {

    @Autowired
    DoctorRerository doctorRerository;

    public List<Doctor> allDoctor() throws Exception{
        return doctorRerository.findAll();
    }

    public List<Doctor> findByFIO(String word ) throws Exception{
        return doctorRerository.findDoctorByFIO( word );
    }

    public Doctor findById( Long id) throws Exception{
        return doctorRerository.findByIdDoctor( id );
    }

    public Doctor saveDoctor( Doctor doctor ) throws Exception{
        return doctorRerository.save( doctor );
    }

}
