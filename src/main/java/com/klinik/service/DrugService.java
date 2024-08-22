package com.klinik.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.klinik.entity.Drug;
import com.klinik.entity.DrugTreatment;
import com.klinik.repositories.DrugRepository;
import com.klinik.repositories.DrugTreatmentRepository;
import com.klinik.request.DrugRequest;

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
    public Drug saveDrug( DrugRequest drugRequest ) throws Exception{
        Optional<DrugTreatment> drugTreatment = drugTreatmentRepository.findById( drugRequest.getIdDrugTreatment());
        checkSaveDrug( drugRequest, drugTreatment );
        return drugRepository.save( new Drug( -1L,
                                              drugRequest.getName(),
                                              drugTreatment.orElseThrow() ));
    }

    private void checkSaveDrug( DrugRequest drugRequest, Optional<DrugTreatment> drugTreatment ){
        if ( drugTreatment.isEmpty() ) throw new IllegalArgumentException( "Медикаментозное лечение с таким ИД не существует");
        if ( drugRepository.findByName( drugRequest.getName() ).isPresent() ) throw new IllegalArgumentException( "Препарат с такми наименованием уже существует");
    }

    public List<Drug> findByIdDrugTreatment( Long id  ) throws Exception {
        List<Drug> response = drugRepository.findByIdDrugTreatment( id );
        if( response.isEmpty() ) throw new NoSuchElementException(  "По данному запросу ничего не найдено");
        return response;
    }
}
