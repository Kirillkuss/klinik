package com.klinik.controller.report;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.klinik.request.reports.ReportDrugTreatmentRequest;
import com.klinik.request.reports.ReportPatientRequest;
import com.klinik.response.ReportDrug;
import com.klinik.response.report.CardPatinetReport;
import com.klinik.response.report.RecordPatientReport;
import com.klinik.response.report.ResponseReport;
import com.klinik.rest.report.IReport;
import com.klinik.service.report.ReportService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ReportController implements IReport{

    private final ReportService reportService;
    public ResponseEntity<List<ResponseReport>> report( LocalDateTime dateFrom, LocalDateTime dateTo ) throws Exception{
        return new ResponseEntity<>( reportService.getStatReport( dateFrom, dateTo), HttpStatus.OK );
    }
    public ResponseEntity<CardPatinetReport> fullInformationPatient( Long idCard ) throws Exception{ 
        return new ResponseEntity<>( reportService.reportInformationAboutPatient( idCard ), HttpStatus.OK );
    }
    public ResponseEntity<RecordPatientReport> findInformationAboutRecordPatient( ReportPatientRequest reportPatientRequest ) throws Exception{
        return new ResponseEntity<>( reportService.reportByPatietnWithRecordPatient( reportPatientRequest ), HttpStatus.OK);
    }
    public ResponseEntity<List<ReportDrug>> getReportDrug(  ReportDrugTreatmentRequest reportDrugTreatmentRequest ) throws Exception{
        return new ResponseEntity<>( reportService.reportStatDrug( reportDrugTreatmentRequest ), HttpStatus.OK);
    }
    
}
