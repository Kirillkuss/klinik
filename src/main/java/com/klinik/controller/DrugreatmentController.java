package com.klinik.controller;

import com.klinik.entity.Drug_treatment;
import com.klinik.service.ServiceDrugTreatment;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RequestMapping( value = "DrugTreatment")
@RestController
@Tag(name = "DrugTreatment", description = "Медикаментозное лечение")
public class DrugreatmentController {

    @Autowired
    private ServiceDrugTreatment service;

    @GetMapping( "/getAll")
    public List<Drug_treatment> listAll() throws Exception{
        return service.getAll();
    }

    @GetMapping( "/findById")
    public Drug_treatment findById( Long id ) throws Exception{
        return service.findById( id );
    }

    @PostMapping( "/addDrug_treatment")
    public Drug_treatment addDrug_treatment( Drug_treatment drug_treatment ) throws Exception{
        return service.addDrugTreatment( drug_treatment );
    }

}
