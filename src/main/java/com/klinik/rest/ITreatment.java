package com.klinik.rest;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.klinik.entity.Treatment;
import com.klinik.response.BaseResponseError;
import com.klinik.response.ResponseTreatment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping( value = "Treatment")
@Tag(name = "7. Treatment", description = "Лечение пациентов:")
public interface ITreatment {
    //@GetMapping(value = "/getAllTreatment")
    @Operation( description = "Получение списка всех лечений", summary = "Получение списка всех лечений")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Получен список лечений", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = ResponseTreatment.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",          content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",         content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
    public ResponseEntity<List<Treatment>> getAllTreatment() throws Exception;
    @PostMapping( value = "/addTreatment")
    @Operation( description = "Добавить лечение для пациента", summary = "Добавить лечение для пациента")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Добавлено новое лечение паиценту", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = ResponseTreatment.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",                    content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",                   content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
    public ResponseEntity<Treatment> addTreatment(  Treatment treatment,
                                                    @Parameter( description = "ИД медикаментозного лечения (Препарата):",  example = "1") Long drug_id,
                                                    @Parameter( description = "Ид карты пациента:",                        example = "1") Long card_patient_id,
                                                    @Parameter( description = "Ид реабилитационного лечения:",             example = "1") Long rehabilitation_solution_id,
                                                    @Parameter( description = "Ид доктор:",                                example = "1") Long doctor_id ) throws Exception;
    @GetMapping(value = "/findByParamIdCardAndDateStart")
    @Operation( description = "Получение списка лечений по параметрам", summary = "Получение списка лечений по параметрам")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Получен список лечений по параметрам", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = ResponseTreatment.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",                        content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",                       content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
    public ResponseEntity<List<Treatment>> findByParamIdCardAndDateStart( @Parameter( description = "Ид карты",                example = "1") Long id,
                                                                          @Parameter( description = "Время начала лечения с:", example = "2023-01-20T12:47:07.605") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
                                                                          @Parameter( description = "Время начала лечения по", example = "2023-09-20T12:47:07.605") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo) throws Exception;
    @GetMapping(value = "/findByParamIdCardAndIdRh")
    @Operation( description = "Получение списка лечений по параметрам", summary = "Получение списка лечений по параметрам")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Получен список лечений по параметрам", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = ResponseTreatment.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",                        content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",                       content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
    public ResponseEntity<List<Treatment>> findByParamIdCardAndIdRh( @Parameter( description = "Ид карты пациента",            example = "1") Long idCard, 
                                                                     @Parameter( description = "Ид реабилитационного лечения", example = "1") Long idReSol ) throws Exception;

}
