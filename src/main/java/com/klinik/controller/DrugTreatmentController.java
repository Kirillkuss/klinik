package com.klinik.controller;

import com.klinik.entity.Drug;
import com.klinik.entity.Drug_treatment;
import com.klinik.excep.MyException;
import com.klinik.rest.IDrugTreatment;
import com.klinik.service.DrugService;
import com.klinik.service.DrugTreatmentService;
import io.swagger.v3.oas.annotations.Parameter;
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
    public ResponseEntity<List<Drug_treatment>> listAll() throws Exception{
        return new ResponseEntity<>( serviceDrugTreatment.getAll(), HttpStatus.OK);
    }
    public ResponseEntity<List<Drug>> findById( Long id ) throws Exception{
        if( serviceDrugTreatment.findById( id ) == null ) throw new MyException( 404, "Мед. лечения с таким ИД не существует");
        return new ResponseEntity<>( drugService.findByIdDrugTreatment( id ), HttpStatus.OK );
    }
    public ResponseEntity addDrug_treatment( Drug_treatment drug_treatment ) throws Exception{
        if ( serviceDrugTreatment.findById( drug_treatment.getId_drug()) != null ) throw new MyException( 409, "Медикаментозное лечение с таким ИД уже существует");
        if ( serviceDrugTreatment.findByName( drug_treatment.getName() ) != null ) throw new MyException( 409, "Медикаментозное лечение с таким наименование уже существует");
        return new ResponseEntity<>( serviceDrugTreatment.addDrugTreatment( drug_treatment ), HttpStatus.CREATED );
    }
    public ResponseEntity saveDrug( Drug drug, @Parameter( description = "ИД мед. лечения", example = "1" ) Long idDrugTreatment ) throws Exception{
        if( serviceDrugTreatment.findById( idDrugTreatment ) == null )  throw new MyException( 400, "Медикаментозное лечение с таким ИД не существует");
        if (drugService.findById( drug.getId_dr() ) != null )           throw new MyException( 409, "Препарат с такми ИД уже существует");
        if (drugService.findByName(drug.getName()) != null )            throw new MyException( 409, "Препарат с такми наименованием уже существует");
        drug.setDrugTreatment( serviceDrugTreatment.findById( idDrugTreatment ));
        return new ResponseEntity<>( drugService.saveDrug( drug ), HttpStatus.OK);
    }
}
