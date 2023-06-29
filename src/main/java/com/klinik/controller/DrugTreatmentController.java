package com.klinik.controller;

import com.klinik.entity.Drug;
import com.klinik.entity.Drug_treatment;
import com.klinik.excep.MyException;
import com.klinik.response.BaseResponse;
import com.klinik.rest.IDrugTreatment;
import com.klinik.service.DrugService;
import com.klinik.service.ServiceDrugTreatment;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DrugTreatmentController implements IDrugTreatment{

    @ExceptionHandler(Throwable.class)
    public BaseResponse errBaseResponse( Throwable ex ){
        return BaseResponse.error( 999, ex );
    }

    @ExceptionHandler(MyException.class)
    public BaseResponse errBaseResponse( MyException ex ){
        return BaseResponse.error( ex.getCode(), ex );
    }

    @Autowired
    private ServiceDrugTreatment serviceDrugTreatment;
    @Autowired
    private DrugService          drugService; 
    public BaseResponse listAll() throws Exception{
        return new BaseResponse<>( 200, "success", serviceDrugTreatment.getAll());
    }
    public BaseResponse findById( Long id ) throws Exception{
        if( serviceDrugTreatment.findById( id ) == null ) throw new MyException( 408, "Мед. лечения с таким ИД не существует");
        return new BaseResponse<>( 200, "success", drugService.findByIdDrugTreatment( id ) );
    }
    public BaseResponse addDrug_treatment( Drug_treatment drug_treatment ) throws Exception{
        if ( serviceDrugTreatment.findById( drug_treatment.getId_drug()) != null ) throw new MyException( 491, "Медикаментозное лечение с таким ИД уже существует");
        if ( serviceDrugTreatment.findByName( drug_treatment.getName() ) != null ) throw new MyException( 492, "Медикаментозное лечение с таким наименование уже существует");
        return new BaseResponse<>( 200, "success", serviceDrugTreatment.addDrugTreatment( drug_treatment ));
    }
    public BaseResponse saveDrug( Drug drug, @Parameter( description = "ИД мед. лечения", example = "1" ) Long idDrugTreatment ) throws Exception{
        if( serviceDrugTreatment.findById( idDrugTreatment ) == null )  throw new MyException( 493, "Медикаментозное лечение с таким ИД не существует");
        if (drugService.findById( drug.getId_dr() ) != null )           throw new MyException( 494, "Препарат с такми ИД уже существует");
        if (drugService.findByName(drug.getName()) != null )            throw new MyException( 494, "Препарат с такми наименованием уже существует");
        drug.setDrugTreatment( serviceDrugTreatment.findById( idDrugTreatment ));
        return new BaseResponse<>( 200, "success", drugService.saveDrug( drug ));
    }
}
