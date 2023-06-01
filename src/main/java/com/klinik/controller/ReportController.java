package com.klinik.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.klinik.excep.MyException;
import com.klinik.response.BaseResponse;
import com.klinik.response.BaseResponseError;
import com.klinik.response.ReportDrug;
import com.klinik.response.report.RecordPatientReport;
import com.klinik.service.report.ReportService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping("/Reports")
@RestController
@Tag(name = "Report", description = "Отчеты:")
public class ReportController {

    @Autowired
    private ReportService service;

    @Operation( description = "Отчет по виду ребилитационного лечения за период времени", summary = "Отчет по виду ребилитационного лечения за период времени")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Получен отчет по виду ребилитационного лечения за период времени", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",       content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) })
    })
    @GetMapping("/report_rehabilitation_treatment_for_time")
    public String report( @Parameter( description = "Дата начала выборки:", example = "2021-05-24T14:02:35.584")   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
                                @Parameter( description = "Дата конца выборки:", example = "2023-12-24T14:02:35.584")   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo ) throws Exception{
        
        BaseResponse response = new BaseResponse<>( 200, "успешно");
        try{
            response.setResponse(service.getStatReport( dateFrom, dateTo));
            return response.toString();
        }catch( MyException ex){
            return BaseResponse.error( 999, ex).toString();
        }
    }


    @Operation( description = "Отчет о полной информации по пациенту", summary = "Отчет о полной информации по пациенту")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Получен отчет о полной информации по пациенту", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",       content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
    @GetMapping("/report_full_info_patient")
    public String fullInformationPatient(  @Parameter( description = "Ид карты пациента:", example = "1")  Long idCard ) throws Exception{ 
        return service.reportInformationAboutPatient( idCard ).toString();
    }

    @Operation( description = "Отчет по записям пациента к врачу за период времени", summary = "Отчет по записям пациента к врачу за период времени")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Получен отчет по записям пациента к врачу за период времени", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = RecordPatientReport.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",       content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
    @GetMapping("/report_info_report_patient")
    public String findInformationAboutRecordPatient( @Parameter( description = "ИД пациента:", example = "1") Long IdPatient,
                                                                  @Parameter( description = "Дата начала выборки:", example = "2023-01-24T14:02:35.584") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
                                                                  @Parameter( description = "Дата начала выборки:", example = "2023-12-24T14:02:35.584") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo ) throws Exception{
        BaseResponse response = new BaseResponse( 200, "успешно");
        response.setResponse( service.reportByPatietnWithRecordPatient( IdPatient, dateFrom, dateTo ));
        return response.toString();
    }


    @Operation( description = "Отчет о медикаментозном лечении за период времени", summary = "Отчет о медикаментозном лечении за период времени")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Получен отчет о медикаментозном лечении за период времени", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",       content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
    @GetMapping( "/report_drug_treatment")
    public String getReportDrug(@Parameter( description = "Дата начала выборки:", example = "2023-01-24T14:02:35.584") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
                                                        @Parameter( description = "Дата начала выборки:", example = "2023-12-24T14:02:35.584") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo ) throws Exception{
        BaseResponse<List<ReportDrug>> report = new BaseResponse<>(200, "успешно" );
        try{
            report.setResponse( service.reportStatDrug( dateFrom, dateTo ));
            return report.toString();
        }catch( Exception ex ){
            return BaseResponse.error( 999, ex ).toString();
        }
    }
    
}
