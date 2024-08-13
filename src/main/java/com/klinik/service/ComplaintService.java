package com.klinik.service;

import java.util.List;
import com.klinik.entity.Complaint;
import com.klinik.repositories.ComplaintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComplaintService {

    private final ComplaintRepository complaintRepository;

    public List<Complaint> listComplaints(){
        return complaintRepository.findAll();
    }
    public Complaint saveСomplaint( Complaint сomplaint ) throws Exception{
        if( complaintRepository.findById( сomplaint.getIdComplaint() ).isEmpty() == false) throw new IllegalArgumentException( "Справочник жалоба с таким ИД уже существует");
        if( complaintRepository.findByName( сomplaint.getFunctionalImpairment() ).isEmpty() == false ) throw new IllegalArgumentException( "Справочник жалоба с таким наименованием уже существует");
        return complaintRepository.save( сomplaint );
    }

}
