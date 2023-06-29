package com.klinik.controller;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import com.klinik.excep.MyException;
import com.klinik.response.BaseResponse;
import com.klinik.rest.IReport;
import com.klinik.service.report.ReportService;

@RestController
public class ReportController implements IReport{

    @ExceptionHandler(Throwable.class)
    public BaseResponse errBaseResponse( Throwable ex ){
        return BaseResponse.error( 999, ex );
    }

    @ExceptionHandler(MyException.class)
    public BaseResponse errBaseResponse( MyException ex ){
        return BaseResponse.error( ex.getCode(), ex );
    }

    @Autowired
    private ReportService reportService;
    public BaseResponse report( LocalDateTime dateFrom, LocalDateTime dateTo ) throws Exception{
        return new BaseResponse<>( 200, "success", reportService.getStatReport( dateFrom, dateTo));
    }
    public BaseResponse fullInformationPatient( Long idCard ) throws Exception{ 
        return new BaseResponse<>( 200, "success", reportService.reportInformationAboutPatient( idCard ));
    }
    public BaseResponse findInformationAboutRecordPatient( Long IdPatient, LocalDateTime dateFrom, LocalDateTime dateTo ) throws Exception{
        return new BaseResponse<>( 200, "успешно", reportService.reportByPatietnWithRecordPatient( IdPatient, dateFrom, dateTo ));
    }
    public BaseResponse getReportDrug( LocalDateTime dateFrom, LocalDateTime dateTo ) throws Exception{
        return new BaseResponse<>(200, "успешно", reportService.reportStatDrug( dateFrom, dateTo ));
    }
    
}
