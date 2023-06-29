package com.klinik.controller;

import com.klinik.entity.Patient;
import com.klinik.excep.MyException;
import com.klinik.response.BaseResponse;
import com.klinik.rest.IPatient;
import com.klinik.service.DocumentService;
import com.klinik.service.PatientService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class PatientController implements IPatient{

    @ExceptionHandler(Throwable.class)
    public ResponseEntity errBaseResponse( Throwable ex ){
        return ResponseEntity.internalServerError().body( BaseResponse.error( 999, ex ) );
    }

    @ExceptionHandler(MyException.class)
    public ResponseEntity errBaseResponse( MyException ex ){
        return ResponseEntity.badRequest( ).body( BaseResponse.error( ex.getCode(), ex ));
    }

    @Autowired
    private PatientService  patientService;
    
    @Autowired
    private DocumentService documentService;
    public ResponseEntity<List<Patient>> getAllPatients() throws Exception, MyException{
        return new ResponseEntity<>(patientService.getAllPatients(), HttpStatus.OK );
    }
    public ResponseEntity<Patient> addPatient(Patient patient, Long id) throws Exception, MyException{
        if( patientService.findByPhone( patient.getPhone() ) != null )  throw new MyException( 423, "Пользователь с таким номером телефона уже существует, укажите другой");
        if( patientService.findByIdDocument( id ) != null )             throw new MyException( 420, "Не верное значение ИД документа, попробуйте другой");
        if( patientService.findById( patient.getId_patient()) != null ) throw new MyException( 421, "Пользователь с таким ИД уже существует");
        if( documentService.findById( id ) == null)                     throw new MyException( 422, "Документ с таким ИД не существует");
        patient.setDocument(  documentService.findById( id ) );
        return new ResponseEntity<>( patientService.addPatient( patient ), HttpStatus.CREATED );
    }
    public ResponseEntity<List<Patient>> findByWord( String word ) throws Exception, MyException{
        if ( patientService.findByWord( word ).isEmpty() == true ) throw new MyException( 999, "По данному запросу ничего не найдено");
        return new ResponseEntity<>( patientService.findByWord( word ), HttpStatus.OK );
    }

 }
