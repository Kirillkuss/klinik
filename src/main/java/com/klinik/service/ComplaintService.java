package com.klinik.service;

import java.util.List;
import com.klinik.entity.Сomplaint;
import com.klinik.excep.MyException;
import com.klinik.repositories.ComplaintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComplaintService {

    private final ComplaintRepository complaintRepository;

    public List<Сomplaint> listComplaints() throws Exception{
        return complaintRepository.findAll();
    }
    
    public Сomplaint saveСomplaint( Сomplaint сomplaint ) throws Exception{
        if( complaintRepository.findById( сomplaint.getId_complaint() ).isEmpty() == false) throw new MyException( 409, "Справочник жалоба с таким ИД уже существует");
        if( complaintRepository.findByName( сomplaint.getFunctional_impairment() ).isEmpty() == false ) throw new MyException( 409, "Справочник жалоба с таким наименованием уже существует");
        return complaintRepository.save( сomplaint );
    }

}
