package com.klinik.controller;

import com.klinik.entity.Drug_treatment;
import com.klinik.excep.MyException;
import com.klinik.response.BaseResponse;
import com.klinik.response.BaseResponseError;
import com.klinik.service.ServiceDrugTreatment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
            @ApiResponse( responseCode = "200", description = "Получен список всех медикаментозных лечений", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class ))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",       content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class  ))) })
    })
    public String listAll() throws Exception{
        return service.getAll().toString();
    }

    @GetMapping( "/findById")
    @Operation( description = "Поиск по ИД медикаментозного лечения", summary = "Поиск по ИД медикаментозного лечения")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Найдено медикоментозное лечение по ИД", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class ))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",       content = { @Content(array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class  ))) })
    })
    public String findById( @Parameter(description = "ИД медикаментозного лечения",example = "1") Long id ) throws Exception{
        return service.findById( id ).toString();
    }

    @Operation( description = "Добавить медикаментозного лечения", summary = "Добавить медикаментозного лечения")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Добавлено медикаментозное лечение", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class ))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",       content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class  ))) })
    })
    @PostMapping( "/addDrug_treatment")
    public String addDrug_treatment( Drug_treatment drug_treatment ) throws Exception{
        BaseResponse response = new BaseResponse( 200, "успешно");
        try{
            if ( service.findById( drug_treatment.getId_drug()) != null ) throw new MyException( 491, "Медикаментозное лечение с таким ИД уже существует");
            if ( service.findByName( drug_treatment.getName() ) != null ) throw new MyException( 492, "Медикаментозное лечение с таким наименование уже существует");
            response.setResponse( service.addDrugTreatment( drug_treatment ));
            return response.toString();
        }catch( MyException ex ){
            return BaseResponse.error( ex.getCode(), ex ).toString();
        }

    }

}