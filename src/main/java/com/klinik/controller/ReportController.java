package com.klinik.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.klinik.response.ReportDrug;
import com.klinik.response.report.CardPatinetReport;
import com.klinik.response.report.RecordPatientReport;
import com.klinik.response.report.ResponseReport;
import com.klinik.rest.IReport;
import com.klinik.service.report.ReportService;

@RestController
public class ReportController implements IReport{

    @Autowired
    private ReportService reportService;
    public ResponseEntity<List<ResponseReport>> report( LocalDateTime dateFrom, LocalDateTime dateTo ) throws Exception{
        return new ResponseEntity<>( reportService.getStatReport( dateFrom, dateTo), HttpStatus.OK );
    }
    public ResponseEntity<CardPatinetReport> fullInformationPatient( Long idCard ) throws Exception{ 
        return new ResponseEntity<>( reportService.reportInformationAboutPatient( idCard ), HttpStatus.OK );
    }
    public ResponseEntity<RecordPatientReport> findInformationAboutRecordPatient( Long IdPatient, LocalDateTime dateFrom, LocalDateTime dateTo ) throws Exception{
        return new ResponseEntity<>( reportService.reportByPatietnWithRecordPatient( IdPatient, dateFrom, dateTo ), HttpStatus.OK);
    }
    public ResponseEntity<List<ReportDrug>> getReportDrug( LocalDateTime dateFrom, LocalDateTime dateTo ) throws Exception{
        return new ResponseEntity<>( reportService.reportStatDrug( dateFrom, dateTo), HttpStatus.OK);
    }
    
}
