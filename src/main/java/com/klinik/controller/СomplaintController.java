package com.klinik.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.klinik.entity.TypeComplaint;
import com.klinik.excep.MyException;
import com.klinik.rest.IComplaint;
import com.klinik.service.ComplaintService;
import com.klinik.service.TypeComplaintService;
import lombok.RequiredArgsConstructor;
import com.klinik.entity.Complaint;

@RestController
@RequiredArgsConstructor
public class СomplaintController implements IComplaint {
    
    private final ComplaintService     complaintService;
    private final TypeComplaintService typeComplaintService;
    public ResponseEntity<List<Complaint>> findAll() throws Exception{
        return new ResponseEntity<>( complaintService.listComplaints(), HttpStatus.OK);
    }
    public ResponseEntity<Complaint> saveСomplaint( Complaint сomplaint ) throws Exception{
        return new ResponseEntity<>(complaintService.saveСomplaint( сomplaint ), HttpStatus.CREATED );
    }
    public ResponseEntity<TypeComplaint> saveTypeComplaint( TypeComplaint request, Long idComplaint ) throws Exception{
        return new ResponseEntity<>( typeComplaintService.saveTypeComplaint( request, idComplaint ), HttpStatus.CREATED );
    }
    public ResponseEntity<List<TypeComplaint>> listComplaintWithTypeComplaints( Long Id ) throws Exception{
        return new ResponseEntity<>( typeComplaintService.findByIdComplaint( Id ), HttpStatus.OK );
    }
 
}
