package com.klinik.rest;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.klinik.entity.Record_patient;
import com.klinik.excep.MyException;
import com.klinik.response.BaseResponseError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping( value = "RecordsPatients")
@Tag(name = "5. Records Patients", description = "Записи пациентов:")
public interface IRecordPatinet {
        //@GetMapping(value = "/getAllRecordPatient")
    @Operation( description = "Список всех записей пациентов к врачу", summary = "Список всех записей пациентов к врачу")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Список всех записей пациентов к врачу", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = Record_patient.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",                         content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",                        content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
    public ResponseEntity<List<Record_patient>> allListRecordPatient() throws Exception, MyException;
    @PostMapping (value = "/addRecordPatient")
    @Operation( description = "Добавить запись пациента к врачу", summary = "Добавить запись пациента к врачу")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "201", description = "Запись к врачу добавлена", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = Record_patient.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",            content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",           content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
    public ResponseEntity<Record_patient> addRecordPatient( Record_patient record_patient,
                                                            @Parameter( description = "Ид доктора") Long doctor_id,
                                                            @Parameter( description = "Ид карты пациента") Long card_patient_id) throws Exception, MyException;
    @GetMapping(value = "/findByParams")
    @Operation( description = "Список всех записей пациентов к врачу по параметрам", summary = "Список всех записей пациентов к врачу по параметрам ")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Получен список записей к врачу", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = Record_patient.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",                  content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",                 content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
    public ResponseEntity<List<Record_patient>> findByParams( @Parameter(description = "ИД карты пациента", example = "1") Long id,
                                                              @Parameter(description = "Дата записи с:", example = "2023-02-19T12:47:07.605")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
                                                              @Parameter(description = "Дата записи по:", example = "2023-05-19T12:47:07.605") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo ) throws Exception, MyException;
    
}
