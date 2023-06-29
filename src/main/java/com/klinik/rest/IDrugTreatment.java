package com.klinik.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.klinik.entity.Drug;
import com.klinik.entity.Drug_treatment;
import com.klinik.response.BaseResponse;
import com.klinik.response.BaseResponseError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping( value = "DrugTreatment")
@Tag(name = "8. Drug Treatment", description = "Справочник: Медикаментозное лечение и препараты")
public interface IDrugTreatment {
    @GetMapping( "/getAllDrugTreatments")
    @Operation( description = "Список всех медикаментозных лечений", summary = "Список всех медикаментозных лечений")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Получен список всех медикаментозных лечений", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = Drug_treatment.class ))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",                               content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",                              content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class  ))) })
    })
    public ResponseEntity<List<Drug_treatment>> listAll() throws Exception;
    @GetMapping( "/findById")
    @Operation( description = "Поиск по ИД медикаментозного лечения c препаратами", summary = "Поиск по ИД медикаментозного лечения с препаратами")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Найдено медикоментозное лечение по ИД", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = Drug.class ))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",                         content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",                        content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class  ))) })
    })
    public ResponseEntity<List<Drug>> findById( @Parameter(description = "ИД медикаментозного лечения",example = "1") Long id ) throws Exception;
    @Operation( description = "Добавить медикаментозного лечения", summary = "Добавить медикаментозного лечения")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Добавлено медикаментозное лечение", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = Drug_treatment.class ))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",                     content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",                    content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class  ))) })
    })
    @PostMapping( "/addDrug_treatment")
    public ResponseEntity<Drug_treatment> addDrug_treatment( Drug_treatment drug_treatment ) throws Exception;
    @Operation( description = "Добавить препарат для медикаментозного лечения", summary = "Добавить препарат для медикаментозного лечения")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Добавлен препарат для медикаментозного лечения", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = Drug.class ))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",                                  content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",                                 content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class  ))) })
    })
    @PostMapping("/addDrug")
    public ResponseEntity<Drug> saveDrug( Drug drug, @Parameter( description = "ИД мед. лечения", example = "1" ) Long idDrugTreatment ) throws Exception;
    
}
