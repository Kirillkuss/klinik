package com.klinik.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.klinik.entity.TypeComplaint;
import com.klinik.excep.MyException;
import com.klinik.rest.IComplaint;
import com.klinik.service.ComplaintService;
import com.klinik.service.TypeComplaintService;
import com.klinik.entity.Сomplaint;

@RestController
public class СomplaintController implements IComplaint {
    
    @Autowired private ComplaintService     complaintService;
    @Autowired private TypeComplaintService typeComplaintService;
    public ResponseEntity<List<Сomplaint>> findAll() throws Exception{
        return new ResponseEntity<>( complaintService.listComplaints(), HttpStatus.OK);
    }
    public ResponseEntity<Сomplaint> saveСomplaint( com.klinik.entity.Сomplaint сomplaint ) throws Exception{
        if( complaintService.findById( сomplaint.getId_complaint() ) !=null )            throw new MyException( 409, "Справочник жалоба с таким ИД уже существует");
        if( complaintService.findByName( сomplaint.getFunctional_impairment() ) !=null ) throw new MyException( 409, "Справочник жалоба с таким наименованием уже существует");
        return new ResponseEntity<>(complaintService.saveСomplaint( сomplaint ), HttpStatus.CREATED );
    }
    public ResponseEntity<TypeComplaint> saveTypeComplaint( TypeComplaint request, Long idComplaint ) throws Exception{
        if( complaintService.findById( idComplaint) == null )                        throw new MyException( 400, "Неверный параметр, жалоба с таким ИД не существует");
        if( typeComplaintService.findByName( request.getName()) != null)             throw new MyException( 409, "Под жалоба с таким наименованием уже существует");
        if( typeComplaintService.findById( request.getId_type_complaint()) != null ) throw new MyException( 409, "Под жалоба с таким ИД уже существует");
        request.setComplaint( complaintService.findById( idComplaint ));
        return new ResponseEntity<>( typeComplaintService.saveTypeComplaint( request ), HttpStatus.CREATED );
    }
    public ResponseEntity<List<TypeComplaint>> listComplaintWithTypeComplaints( Long Id ) throws Exception{
        if( complaintService.findById( Id ) == null ) throw new MyException( 404, "Жалобы с таким ИД не существует");
        return new ResponseEntity<>( typeComplaintService.findByIdComplaint( Id ), HttpStatus.OK );
    }
 
}
