package com.klinik.controller;

import com.klinik.entity.Doctor;
import com.klinik.excep.MyException;
import com.klinik.rest.IDoctor;
import com.klinik.service.DoctorService;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class DoctorController implements IDoctor{

   private final DoctorService doctorService;
    public ResponseEntity<List<Doctor>> getAllDoc() throws Exception{
        return new ResponseEntity<>( doctorService.allDoctor(), HttpStatus.OK );
    }

    public ResponseEntity<List<Doctor>> findByFIO( String word ) throws Exception{
        if( doctorService.findByFIO( word ).isEmpty() == true ) throw new MyException( 404, "По данному запросу ничего не найдено");
        return new ResponseEntity<>( doctorService.findByFIO( word ), HttpStatus.OK); 
    }

    public ResponseEntity<Doctor> addDoctor( Doctor doctor ) throws Exception{
        if (doctorService.findById( doctor.getId_doctor() ) != null ) throw new MyException( 409, "Пользователь с таким ИД уще существует");
        return new ResponseEntity<>(  doctorService.saveDoctor( doctor ), HttpStatus.OK );
    }
}
