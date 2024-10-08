package com.klinik.service;

import com.klinik.entity.DrugTreatment;
import com.klinik.repositories.DrugTreatmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DrugTreatmentService {

    private final DrugTreatmentRepository drugTreatmentRepository;

    public List<DrugTreatment> getAll(){
        return drugTreatmentRepository.findAll();
    }
    public DrugTreatment addDrugTreatment( DrugTreatment drugTreatment ) throws Exception{
        checkDrugTreatment(drugTreatment );
        return drugTreatmentRepository.save( drugTreatment );
    }

    private void checkDrugTreatment( DrugTreatment drugTreatment  ){
        if ( drugTreatmentRepository.findById( drugTreatment.getIdDrugTreatment()).isPresent() ) throw new IllegalArgumentException(  "Медикаментозное лечение с таким ИД уже существует");
        if ( drugTreatmentRepository.findByName( drugTreatment.getName() ).isPresent() ) throw new IllegalArgumentException(  "Медикаментозное лечение с таким наименование уже существует");
    }
}
