package com.klinik.service;

import com.klinik.entity.Drug_treatment;
import com.klinik.excep.MyException;
import com.klinik.repositories.DrugTreatmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DrugTreatmentService {

    private final DrugTreatmentRepository drugTreatmentRepository;

    public List<Drug_treatment> getAll() throws Exception{
        return drugTreatmentRepository.findAll();
    }
    public Drug_treatment addDrugTreatment( Drug_treatment drug_treatment ) throws Exception{
        if ( drugTreatmentRepository.findById( drug_treatment.getId_drug()).isPresent() == true ) throw new MyException( 409, "Медикаментозное лечение с таким ИД уже существует");
        if ( drugTreatmentRepository.findByName( drug_treatment.getName() ).isPresent() == true ) throw new MyException( 409, "Медикаментозное лечение с таким наименование уже существует");
        return drugTreatmentRepository.save( drug_treatment );
    }
}
