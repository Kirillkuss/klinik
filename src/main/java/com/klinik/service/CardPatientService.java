package com.klinik.service;

import com.klinik.entity.Card_patient;
import com.klinik.repositories.CardPatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardPatientService {

    @Autowired
    private CardPatientRepository repository;

    public List<Card_patient> allListCardPatient() throws Exception{
        return repository.findAll();
    }
}
