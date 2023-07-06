package com.klinik.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.klinik.entity.Drug;
import com.klinik.entity.DrugTreatment;
import com.klinik.excep.MyException;
import com.klinik.repositories.DrugRepository;
import com.klinik.repositories.DrugTreatmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DrugService {

    private final DrugRepository          drugRepository;
    private final DrugTreatmentRepository drugTreatmentRepository;
    public List<Drug> findAll(){
        return drugRepository.findAll();
    }
    public Drug saveDrug( Drug drug,  Long idDrugTreatment ) throws Exception{
        Optional<DrugTreatment> drugTreatment = drugTreatmentRepository.findById( idDrugTreatment );
        if ( drugTreatment.isEmpty() ) throw new MyException( 400, "Медикаментозное лечение с таким ИД не существует");
        if ( drugRepository.findById( drug.getIdDrug()).isPresent() ) throw new MyException( 409, "Препарат с такми ИД уже существует");
        if ( drugRepository.findByName( drug.getName() ).isPresent() ) throw new MyException( 409, "Препарат с такми наименованием уже существует");
        drug.setDrugTreatment( drugTreatment.get() );
        return drugRepository.save( drug );
    }
    public List<Drug> findByIdDrugTreatment( Long id  ) throws Exception {
        List<Drug> response = drugRepository.findByIdDrugTreatment( id );
        if( response.isEmpty() ) throw new MyException( 404, "По данному запросу ничего не найдено");
        return response;
    }
}
