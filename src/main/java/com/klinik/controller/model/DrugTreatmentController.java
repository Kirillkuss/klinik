package com.klinik.controller.model;

import com.klinik.entity.Drug;
import com.klinik.entity.DrugTreatment;
import com.klinik.request.DrugRequest;
import com.klinik.rest.model.IDrugTreatment;
import com.klinik.service.DrugService;
import com.klinik.service.DrugTreatmentService;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DrugTreatmentController implements IDrugTreatment{

    private final DrugTreatmentService serviceDrugTreatment;
    private final DrugService          drugService; 
    public ResponseEntity<List<DrugTreatment>> listAll() throws Exception{
        return new ResponseEntity<>( serviceDrugTreatment.getAll(), HttpStatus.OK);
    }
    public ResponseEntity<List<Drug>> findById( Long id ) throws Exception{
        return new ResponseEntity<>( drugService.findByIdDrugTreatment( id ), HttpStatus.OK );
    }
    public ResponseEntity addDrugTreatment( DrugTreatment drugTreatment ) throws Exception{
        return new ResponseEntity<>( serviceDrugTreatment.addDrugTreatment( drugTreatment ), HttpStatus.CREATED );
    }
    public ResponseEntity saveDrug( DrugRequest drugRequest ) throws Exception{
        return new ResponseEntity<>( drugService.saveDrug( drugRequest ), HttpStatus.OK);
    }
}
