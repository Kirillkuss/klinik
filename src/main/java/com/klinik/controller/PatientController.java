package com.klinik.controller;

import com.klinik.entity.Patient;
import com.klinik.excep.MyException;
import com.klinik.response.BaseResponse;
import com.klinik.response.BaseResponseError;
import com.klinik.rest.IPatient;
import com.klinik.service.DocumentService;
import com.klinik.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class PatientController implements IPatient{

    @ExceptionHandler(Throwable.class)
    public BaseResponse errBaseResponse( Throwable ex ){
        return BaseResponse.error( 999, ex );
    }

    @ExceptionHandler(MyException.class)
    public BaseResponse errBaseResponse( MyException ex ){
        return BaseResponse.error( ex.getCode(), ex );
    }

    @Autowired
    private PatientService  patientService;
    
    @Autowired
    private DocumentService documentService;
    public BaseResponse getAllPatients() throws Exception, MyException{
        return new BaseResponse<>( 200, "success", patientService.getAllPatients());
    }
    public BaseResponse addPatient(Patient patient, Long id) throws Exception, MyException{
        if( patientService.findByPhone( patient.getPhone() ) != null )  throw new MyException( 423, "Пользователь с таким номером телефона уже существует, укажите другой");
        if( patientService.findByIdDocument( id ) != null )             throw new MyException( 420, "Не верное значение ИД документа, попробуйте другой");
        if( patientService.findById( patient.getId_patient()) != null ) throw new MyException( 421, "Пользователь с таким ИД уже существует");
        if( documentService.findById( id ) == null)                     throw new MyException( 422, "Документ с таким ИД не существует");
        patient.setDocument(  documentService.findById( id ) );
        return new BaseResponse<>( 200, "success", patientService.addPatient( patient ));
    }
    public BaseResponse findByWord( String word ) throws Exception, MyException{
        if ( patientService.findByWord( word ).isEmpty() == true ) throw new MyException( 999, "По данному запросу ничего не найдено");
        return new BaseResponse<>( 200, "success",patientService.findByWord( word ));
    }

 }
