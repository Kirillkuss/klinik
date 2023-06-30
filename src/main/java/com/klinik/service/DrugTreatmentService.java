package com.klinik.service;

import com.klinik.entity.Drug_treatment;
import com.klinik.repositories.DrugTreatmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrugTreatmentService {

    @Autowired
    DrugTreatmentRepository drugTreatmentRepositoryс;

    public List<Drug_treatment> getAll() throws Exception{
        return drugTreatmentRepositoryс.findAll();
    }

    public Drug_treatment findById( Long id ) throws Exception{
        return drugTreatmentRepositoryс.findById( id ).stream().findFirst().orElse( null);
    }

    public Drug_treatment addDrugTreatment( Drug_treatment drug_treatment ) throws Exception{
        return drugTreatmentRepositoryс.save( drug_treatment );
    }

    public Drug_treatment findByName( String name ) throws Exception{
        return drugTreatmentRepositoryс.findByName( name );
    }
}
