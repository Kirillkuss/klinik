package com.klinik.controller;

import com.klinik.entity.Doctor;
import com.klinik.excep.MyException;
import com.klinik.response.BaseResponse;
import com.klinik.rest.IDoctor;
import com.klinik.service.DoctorService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DoctorController implements IDoctor{

    @ExceptionHandler(Throwable.class)
    public BaseResponse errBaseResponse( Throwable ex ){
        return BaseResponse.error( 999, ex );
    }

    @ExceptionHandler(MyException.class)
    public BaseResponse errBaseResponse( MyException ex ){
        return BaseResponse.error( ex.getCode(), ex );
    }

    @Autowired
    DoctorService doctorService;
    public BaseResponse getAllDoc() throws Exception{
        return new BaseResponse<>( 200, "success", doctorService.allDoctor() );
    }

    public BaseResponse findByFIO(@Parameter( description = "ФИО врача") String word ) throws Exception{
        if( doctorService.findByFIO( word ).isEmpty() == true ) throw new MyException( 417, "По данному запросу ничего не найдено");
        return new BaseResponse<>( 200, "success", doctorService.findByFIO( word )); 
    }

    public BaseResponse addDoctor( Doctor doctor ) throws Exception{
        if (doctorService.findById( doctor.getId_doctor() ) != null ) throw new MyException( 418, "Пользователь с таким ИД уще существует");
        return new BaseResponse<>( 200, "success", doctorService.saveDoctor( doctor ));
    }
}
