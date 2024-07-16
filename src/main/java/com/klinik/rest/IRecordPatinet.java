package com.klinik.rest;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.klinik.entity.RecordPatient;
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

@RequestMapping( value = "record-patients")
@Tag(name = "5. Records Patients", description = "Записи пациентов:")
@ApiResponses(value = {
        @ApiResponse( responseCode = "200", description = "Успешно",        content = { @Content( array = @ArraySchema(schema = @Schema( implementation = RecordPatient.class))) }),
        @ApiResponse( responseCode = "400", description = "Плохой запрос",  content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
        @ApiResponse( responseCode = "500", description = "Ошибка сервера", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
public interface IRecordPatinet {
        //@GetMapping(value = "/list")
    @Operation( description = "Список всех записей пациентов к врачу", summary = "Список всех записей пациентов к врачу")
    public ResponseEntity<List<RecordPatient>> allListRecordPatient() throws Exception, MyException;
    @PostMapping (value = "/add/{record}{id-doctor}{id-card}")
    @Operation( description = "Добавить запись пациента к врачу", summary = "Добавить запись пациента к врачу")
    public ResponseEntity<RecordPatient> addRecordPatient( RecordPatient record,
                                                            @Parameter( description = "Ид доктора") Long idDoctor,
                                                            @Parameter( description = "Ид карты пациента") Long idCard) throws Exception, MyException;
    @GetMapping(value = "/find/{id}{from}{to}")
    @Operation( description = "Список всех записей пациентов к врачу по параметрам", summary = "Список всех записей пациентов к врачу по параметрам ")
    public ResponseEntity<List<RecordPatient>> findByParams( @Parameter(description = "ИД карты пациента", example = "1") Long id,
                                                              @Parameter(description = "Дата записи с:", example = "2023-02-19T12:47:07.605")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
                                                              @Parameter(description = "Дата записи по:", example = "2023-05-19T12:47:07.605") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo ) throws Exception, MyException;
    
}
