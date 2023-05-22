package com.klinik.service;

import com.klinik.entity.Treatment;
import com.klinik.repositories.TreatmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreatmentService {

    @Autowired
    private TreatmentRepository repository;

    public List<Treatment> allListTreatment() throws Exception{
        return repository.findAll();
    }
}
