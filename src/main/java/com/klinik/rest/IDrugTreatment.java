package com.klinik.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.klinik.entity.Drug;
import com.klinik.entity.DrugTreatment;
import com.klinik.response.BaseResponseError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping( value = "drug-treatments")
@Tag(name = "8. Drug Treatment", description = "Справочник: Медикаментозное лечение и препараты")
@ApiResponses(value = {
        @ApiResponse( responseCode = "200", description = "Успешно",        content = { @Content( array = @ArraySchema(schema = @Schema( implementation = DrugTreatment.class ))) }),
        @ApiResponse( responseCode = "400", description = "Плохой запрос",  content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
        @ApiResponse( responseCode = "500", description = "Ошибка сервера", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class  ))) })
    })
public interface IDrugTreatment {
    @GetMapping( "/list")
    @Operation( description = "Список всех медикаментозных лечений", summary = "Список всех медикаментозных лечений")
    public ResponseEntity<List<DrugTreatment>> listAll() throws Exception;
    @GetMapping( "/drug-treatment/{id}")
    @Operation( description = "Поиск по ИД медикаментозного лечения c препаратами", summary = "Поиск по ИД медикаментозного лечения с препаратами")
    public ResponseEntity<List<Drug>> findById( @Parameter(description = "ИД медикаментозного лечения",example = "1") Long id ) throws Exception;
    @Operation( description = "Добавить медикаментозного лечения", summary = "Добавить медикаментозного лечения")
    @PostMapping( "/add/drug-treatment/{drug-treatment}")
    public ResponseEntity<DrugTreatment> addDrugTreatment( DrugTreatment drugTreatment ) throws Exception;
    @Operation( description = "Добавить препарат для медикаментозного лечения", summary = "Добавить препарат для медикаментозного лечения")
    @PostMapping("/add/drug/{request}{id-drug-treatment}")
    public ResponseEntity<Drug> saveDrug( Drug drug, @Parameter( description = "ИД мед. лечения", example = "1" ) Long idDrugTreatment ) throws Exception;
    
}
