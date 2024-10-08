package com.klinik.rest.model;

import java.time.LocalDateTime;
import java.util.List;
import javax.ws.rs.core.MediaType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.klinik.entity.Treatment;
import com.klinik.request.RequestTreatment;
import com.klinik.response.BaseResponseError;
import com.klinik.response.ResponseTreatment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping( value = "treatments")
@Tag(name = "7. Treatment", description = "Лечение пациентов:")
@ApiResponses(value = {
        @ApiResponse( responseCode = "200", description = "Успешно",        content = { @Content( mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema( implementation = ResponseTreatment.class))) }),
        @ApiResponse( responseCode = "400", description = "Плохой запрос",  content = { @Content( mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
        @ApiResponse( responseCode = "500", description = "Ошибка сервера", content = { @Content( mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
@SecurityRequirement(name = "Bearer Authentication")
public interface ITreatment {
    //@GetMapping(value = "/all")
    @Operation( description = "Получение списка всех лечений", summary = "Получение списка всех лечений")
    public ResponseEntity<List<Treatment>> getAllTreatment() throws Exception;
    @PostMapping( value = "/treatment/add")
    @Operation( description = "Добавить лечение для пациента", summary = "Добавить лечение для пациента")
    public ResponseEntity<Treatment> addTreatment(  @RequestBody RequestTreatment requestTreatment ) throws Exception;
    @GetMapping(value = "/find/treat/{id-card}{from}{to}")
    @Operation( description = "Получение списка лечений по параметрам", summary = "Получение списка лечений по параметрам")
    public ResponseEntity<List<Treatment>> findByParamIdCardAndDateStart( @Parameter( description = "Ид карты",                example = "1") Long idCard,
                                                                          @Parameter( description = "Время начала лечения с:", example = "2023-01-20T12:47:07.605") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                                                          @Parameter( description = "Время начала лечения по", example = "2023-09-20T12:47:07.605") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) throws Exception;
    @GetMapping(value = "/find/treatment/{id-card}{id-rehabilitation-solution}")
    @Operation( description = "Получение списка лечений по параметрам", summary = "Получение списка лечений по параметрам")
    public ResponseEntity<List<Treatment>> findByParamIdCardAndIdRh( @Parameter( description = "Ид карты пациента",            example = "1") Long idCard, 
                                                                     @Parameter( description = "Ид реабилитационного лечения", example = "1") Long idRehabilitationSolution ) throws Exception;

}
