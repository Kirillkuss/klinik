package com.klinik.service;

import com.klinik.entity.Doctor;
import com.klinik.excep.MyException;
import com.klinik.repositories.DoctorRerository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRerository doctorRerository;

    public List<Doctor> allDoctor(){
        return doctorRerository.findAll();
    }
    
    public List<Doctor> findByFIO( String word ) throws Exception{
        List<Doctor> response = doctorRerository.findDoctorByFIO( word );
        if( response.isEmpty() ) throw new MyException( 404, "По данному запросу ничего не найдено");
        return response;
    }

    public Doctor saveDoctor( Doctor doctor ) throws Exception{
        if ( doctorRerository.findById( doctor.getIdDoctor() ).isPresent()) throw new MyException( 409, "Пользователь с таким ИД уще существует");
        return doctorRerository.save( doctor );
    }

}
