package com.klinik.controller;

import com.klinik.entity.Drug_treatment;
import com.klinik.excep.MyException;
import com.klinik.response.BaseResponse;
import com.klinik.response.BaseResponseError;
import com.klinik.service.ServiceDrugTreatment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RequestMapping( value = "DrugTreatment")
@RestController
@Tag(name = "8. Drug Treatment", description = "Справочник: Медикаментозное лечение")
public class DrugTreatmentController {

    @Autowired
    private ServiceDrugTreatment service;

    @GetMapping( "/getAllDrugTreatments")
    @Operation( description = "Список всех медикаментозных лечений", summary = "Список всех медикаментозных лечений")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Found the drug treatments", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class ))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class  ))) })
    })
    public List<Drug_treatment> listAll() throws Exception{
        return service.getAll();
    }

    @GetMapping( "/findById")
    @Operation( description = "Поиск по ИД медикаментозного лечения", summary = "Поиск по ИД медикаментозного лечения")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Found the drug treatment by ID", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class ))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class  ))) })
    })
    public Drug_treatment findById( Long id ) throws Exception{
        return service.findById( id );
    }

    @Operation( description = "Добавить медикаментозного лечения", summary = "Добавить медикаментозного лечения")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Add the drug treatment", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class ))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class  ))) })
    })
    @PostMapping( "/addDrug_treatment")
    public BaseResponse addDrug_treatment( Drug_treatment drug_treatment ) throws Exception{
        BaseResponse response = new BaseResponse( 200, "success");
        try{
            if ( service.findById( drug_treatment.getId_drug()) != null ) throw new MyException( 491, "Медикаментозное лечение с таким ИД уже существует");
            if ( service.findByName( drug_treatment.getName() ) != null ) throw new MyException( 492, "Медикаментозное лечение с таким наименование уже существует");
            response.setResponse( service.addDrugTreatment( drug_treatment ));
            return response;
        }catch( MyException ex ){
            return BaseResponse.error( ex.getCode(), ex );
        }

    }

}
