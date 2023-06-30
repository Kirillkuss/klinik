package com.klinik.service;

import java.util.List;
import com.klinik.entity.Сomplaint;
import com.klinik.repositories.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    public Сomplaint findById( Long id ) throws Exception{
        return complaintRepository.findByIdComplaint( id );
    }

    public List<Сomplaint> listComplaints() throws Exception{
        return complaintRepository.findAll();
    }

    public Сomplaint saveСomplaint( Сomplaint сomplaint ) throws Exception{
        return complaintRepository.save( сomplaint );
    }

    public Сomplaint findByName( String name ) throws Exception{
        return complaintRepository.findByName( name );
    }

}
