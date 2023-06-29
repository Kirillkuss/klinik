package com.klinik.controller;

import com.klinik.entity.Doctor;
import com.klinik.excep.MyException;
import com.klinik.response.BaseResponse;
import com.klinik.rest.IDoctor;
import com.klinik.service.DoctorService;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DoctorController implements IDoctor{

    @ExceptionHandler(Throwable.class)
    public ResponseEntity errBaseResponse( Throwable ex ){
        return ResponseEntity.internalServerError().body( BaseResponse.error( 999, ex ) );
    }

    @ExceptionHandler(MyException.class)
    public ResponseEntity errBaseResponse( MyException ex ){
        return ResponseEntity.badRequest( ).body( BaseResponse.error( ex.getCode(), ex ));
    }

    @Autowired
    DoctorService doctorService;
    public ResponseEntity<List<Doctor>> getAllDoc() throws Exception{
        return new ResponseEntity<>( doctorService.allDoctor(), HttpStatus.OK );
    }

    public ResponseEntity<List<Doctor>> findByFIO(@Parameter( description = "ФИО врача") String word ) throws Exception{
        if( doctorService.findByFIO( word ).isEmpty() == true ) throw new MyException( 417, "По данному запросу ничего не найдено");
        return new ResponseEntity<>( doctorService.findByFIO( word ), HttpStatus.OK); 
    }

    public ResponseEntity<Doctor> addDoctor( Doctor doctor ) throws Exception{
        if (doctorService.findById( doctor.getId_doctor() ) != null ) throw new MyException( 418, "Пользователь с таким ИД уще существует");
        return new ResponseEntity<>(  doctorService.saveDoctor( doctor ), HttpStatus.OK );
    }
}
