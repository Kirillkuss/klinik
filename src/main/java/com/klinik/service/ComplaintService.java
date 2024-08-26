package com.klinik.service;

import java.util.List;
import com.klinik.entity.Complaint;
import com.klinik.repositories.ComplaintRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ComplaintService {

    private final ComplaintRepository complaintRepository;

    public List<Complaint> listComplaints(){
        return complaintRepository.findAll();
    }
    public Complaint saveСomplaint( Complaint сomplaint ) throws Exception{
        log.info("сomplaint >> " + сomplaint);
        checkSaveComlaint(сomplaint);
        return complaintRepository.save( сomplaint );
    }

    private void checkSaveComlaint( Complaint сomplaint ){
        if( complaintRepository.findById( сomplaint.getIdComplaint() ).isPresent()) throw new IllegalArgumentException( "Справочник жалоба с таким ИД уже существует");
        if( complaintRepository.findByName( сomplaint.getFunctionalImpairment() ).isPresent() ) throw new IllegalArgumentException( "Справочник жалоба с таким наименованием уже существует");
    }

}
