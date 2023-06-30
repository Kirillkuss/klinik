package com.klinik.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.klinik.entity.TypeComplaint;
import com.klinik.repositories.TypeComplaintRepository;

@Service
public class TypeComplaintService {
    
    @Autowired
    private TypeComplaintRepository typeComplaintRepository;

    public List<TypeComplaint> findByAll() throws Exception{
        return typeComplaintRepository.findAll();
    }

    public TypeComplaint findById( Long id ) throws Exception{
        return typeComplaintRepository.findById( id ).stream().findFirst().orElse(null);
    }

    public TypeComplaint saveTypeComplaint( TypeComplaint complaint ) throws Exception{
        return typeComplaintRepository.save( complaint );
    }

    public TypeComplaint findByName( String name ) throws Exception{
        return typeComplaintRepository.findName( name );
    }

     public List<TypeComplaint> findByIdComplaint( Long id ) throws Exception{
        return typeComplaintRepository.findByIdComplaint( id );
    }
}
