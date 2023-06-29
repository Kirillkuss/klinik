package com.klinik.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import com.klinik.entity.TypeComplaint;
import com.klinik.excep.MyException;
import com.klinik.response.BaseResponse;
import com.klinik.rest.IComplaint;
import com.klinik.service.ComplaintService;
import com.klinik.service.TypeComplaintService;

@RestController
public class СomplaintController implements IComplaint {
    
    @ExceptionHandler(Throwable.class)
    public BaseResponse errBaseResponse( Throwable ex ){
        return BaseResponse.error( 999, ex );
    }

    @ExceptionHandler(MyException.class)
    public BaseResponse errBaseResponse( MyException ex ){
        return BaseResponse.error( ex.getCode(), ex );
    }

    @Autowired private ComplaintService     complaintService;
    @Autowired private TypeComplaintService typeComplaintService;
    public BaseResponse findAll() throws Exception{
        return new BaseResponse<>( 200, "suceess", complaintService.listComplaints());
    }
    public BaseResponse saveСomplaint( com.klinik.entity.Сomplaint сomplaint ) throws Exception{
        if( complaintService.findById( сomplaint.getId_complaint() ) !=null )            throw new MyException( 460, "Справочник жалоба с таким ИЖ уже существует");
        if( complaintService.findByName( сomplaint.getFunctional_impairment() ) !=null ) throw new MyException( 461, "Справочник жалоба с таким наименованием уже существует");
        return new BaseResponse<>( 200, "success", complaintService.saveСomplaint( сomplaint ));
    }
    public BaseResponse saveTypeComplaint( TypeComplaint request, Long idComplaint ) throws Exception{
        if( complaintService.findById( idComplaint) == null )                        throw new MyException( 462, "Жалобы с таким ИД не существует");
        if( typeComplaintService.findByNme( request.getName()) != null)              throw new MyException( 463, "Под жалоба с таким наименование уже существует");
        if( typeComplaintService.findById( request.getId_type_complaint()) != null ) throw new MyException( 464, "Под жалоба с таким ИД уже существует");
        request.setComplaint( complaintService.findById( idComplaint ));
        return new BaseResponse<>(200, "success", typeComplaintService.saveTypeComplaint( request ));
    }
    public BaseResponse listComplaintWithTypeComplaints( Long Id ) throws Exception{
        if( complaintService.findById( Id ) == null ) throw new MyException( 462, "Жалобы с таким ИД не существует");
        return new BaseResponse<>( 200, "success", typeComplaintService.findByIdComplaint( Id ));
    }
 
}
