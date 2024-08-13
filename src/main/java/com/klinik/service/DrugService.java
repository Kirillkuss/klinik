package com.klinik.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.klinik.entity.Drug;
import com.klinik.entity.DrugTreatment;
import com.klinik.repositories.DrugRepository;
import com.klinik.repositories.DrugTreatmentRepository;
import java.util.NoSuchElementException;
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
        if ( drugTreatment.isEmpty() ) throw new IllegalArgumentException( "Медикаментозное лечение с таким ИД не существует");
        checkSaveDrug( drug );
        drug.setDrugTreatment( drugTreatment.get() );
        return drugRepository.save( drug );
    }

    private void checkSaveDrug( Drug drug ){
        if ( drugRepository.findById( drug.getIdDrug()).isPresent() ) throw new IllegalArgumentException( "Препарат с такми ИД уже существует");
        if ( drugRepository.findByName( drug.getName() ).isPresent() ) throw new IllegalArgumentException( "Препарат с такми наименованием уже существует");
    }

    public List<Drug> findByIdDrugTreatment( Long id  ) throws Exception {
        List<Drug> response = drugRepository.findByIdDrugTreatment( id );
        if( response.isEmpty() ) throw new NoSuchElementException(  "По данному запросу ничего не найдено");
        return response;
    }
}
