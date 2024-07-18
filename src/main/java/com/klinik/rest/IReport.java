package com.klinik.rest;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.klinik.response.BaseResponse;
import com.klinik.response.ReportDrug;
import com.klinik.response.report.CardPatinetReport;
import com.klinik.response.report.RecordPatientReport;
import com.klinik.response.report.ResponseReport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping("/reports")
@Tag(name = "Report", description = "Отчеты:")
@ApiResponses(value = {
        @ApiResponse( responseCode = "200", description = "Успешно",        content = { @Content( array = @ArraySchema(schema = @Schema( implementation = ResponseReport.class))) }),
        @ApiResponse( responseCode = "400", description = "Плохой запрос",  content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) }),
        @ApiResponse( responseCode = "500", description = "Ошибка сервера", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) })
    })
public interface IReport {
    @Operation( description = "Отчет по виду ребилитационного лечения за период времени", summary = "Отчет по виду ребилитационного лечения за период времени")
    @GetMapping("/rehabilitation-treatments/{from}{to}")
    public ResponseEntity<List<ResponseReport>> report( @Parameter( description = "Дата начала выборки:", example = "2021-05-24T14:02:35.584")   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                                        @Parameter( description = "Дата конца выборки:", example = "2023-12-24T14:02:35.584")   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to ) throws Exception;
    @Operation( description = "Отчет о полной информации по пациенту", summary = "Отчет о полной информации по пациенту")
    @GetMapping("/info-patient/{id-card}")
    public ResponseEntity<CardPatinetReport> fullInformationPatient( @Parameter( description = "Ид карты пациента:", example = "1")  Long idCard ) throws Exception;

    @Operation( description = "Отчет по записям пациента к врачу за период времени", summary = "Отчет по записям пациента к врачу за период времени")
    @GetMapping("/report-patient/{id-patient}{from}{to}")
    public ResponseEntity<RecordPatientReport> findInformationAboutRecordPatient( @Parameter( description = "ИД пациента:",         example = "1") Long idpatient,
                                                                                  @Parameter( description = "Дата начала выборки:", example = "2023-01-24T14:02:35.584") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                                                                  @Parameter( description = "Дата начала выборки:", example = "2023-12-24T14:02:35.584") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to ) throws Exception;
    @Operation( description = "Отчет о медикаментозном лечении за период времени", summary = "Отчет о медикаментозном лечении за период времени")
    @GetMapping( "/drug-treatment/{from}{to}")
    public ResponseEntity<List<ReportDrug>> getReportDrug(@Parameter( description = "Дата начала выборки:", example = "2023-01-24T14:02:35.584") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                                          @Parameter( description = "Дата начала выборки:", example = "2023-12-24T14:02:35.584") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to ) throws Exception;
    
}

