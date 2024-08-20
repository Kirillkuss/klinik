package com.klinik.service.report;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import com.klinik.entity.RecordPatient;
import com.klinik.excep.MyException;
import com.klinik.repositories.RecordPatientRepository;
import com.klinik.request.reports.ReportDrugTreatmentRequest;
import com.klinik.request.reports.ReportPatientRequest;
import com.klinik.response.ReportDrug;
import com.klinik.response.report.CardPatinetReport;
import com.klinik.response.report.RecordPatientReport;
import com.klinik.response.report.ResponseReport;
import com.klinik.service.CardPatientService;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import java.sql.Types;
import java.sql.CallableStatement;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final EntityManager entityManager;
    private final CardPatientService cardPatientService;
    private final RecordPatientRepository recordPatientRepository;

    /**
     * Отчет по виду ребилитационного лечения за период времени
     * 
     * @param dateFrom - дата с
     * @param dateTo   - дата по
     * @return List<ResponseReport>
     * @throws Exception
     * @throws MyException
     */
    public List<ResponseReport> getStatReport(LocalDateTime dateFrom, LocalDateTime dateTo) throws Exception {
        List<ResponseReport> report = new ArrayList<>();
            entityManager.unwrap(Session.class).doWork((Connection conn) -> {
                try (CallableStatement cs = conn.prepareCall("{ call report_stat( ?,?,? ) }")) {
                    conn.setAutoCommit(false);
                    cs.setTimestamp(1, Timestamp.valueOf( dateFrom ));
                    cs.setTimestamp(2, Timestamp.valueOf( dateTo ));
                    cs.registerOutParameter(3, Types.OTHER);
                    cs.execute();
                    try (ResultSet set =(ResultSet) cs.getObject( 3 )) {
                        while (set.next()) {
                            ResponseReport responseReport = new ResponseReport();
                            responseReport.setNameRehabilitationTreatment(set.getString("name_solution"));
                            responseReport.setCountTreatment(set.getLong("count_solution"));
                            responseReport.setCountPatient(set.getLong("count_patient"));
                            report.add(responseReport);
                        }
                    }
                }
            });
        return report;
    }
    /**
     * Отчет по медикаментозному лечению за промежуток времени
     * @param reportDrugTreatmentRequest - входной параметр
     * @return List ReportDrug
     * @throws Exception
     */
    public List<ReportDrug> reportStatDrug( ReportDrugTreatmentRequest reportDrugTreatmentRequest ) throws Exception {
        List<ReportDrug> response = new ArrayList<>();
            entityManager.unwrap(Session.class).doWork((Connection conn) -> {
                try (CallableStatement cs = conn.prepareCall("{ call report_stat_drug( ?,?,? ) }")) {
                    conn.setAutoCommit(false);
                    cs.setTimestamp(1, Timestamp.valueOf( reportDrugTreatmentRequest.getFrom() ));
                    cs.setTimestamp(2, Timestamp.valueOf( reportDrugTreatmentRequest.getTo() ));
                    cs.registerOutParameter(3, Types.OTHER);
                    cs.execute();
                    try (ResultSet rs = (ResultSet) cs.getObject( 3 )) {
                        while (rs.next()) {
                            ReportDrug drug = new ReportDrug();
                            drug.setNameDrugTreatment(rs.getString("name"));
                            drug.setCountDrugTreatment(rs.getLong("count_drug_treatment"));
                            drug.setCountPatient(rs.getLong("count_patient"));
                            response.add(drug);
                        }
                    }
                }
            });
        return response;
    }

    /**
     * Отчет о полной информации по пациенту
     * 
     * @param idCardPatient - ид карты пациента
     * @return ResponsePatientReport
     * @throws Exception
     */
    public CardPatinetReport reportInformationAboutPatient(Long idCardPatient) throws Exception {
        CardPatinetReport response = new CardPatinetReport();
        response.setCard(cardPatientService.findByIdCard( idCardPatient ));
        entityManager.unwrap(Session.class).doWork((Connection conn) -> {
            try(CallableStatement cs = conn.prepareCall("{ call record_patient( ?,? )}")){
                conn.setAutoCommit(false);
                cs.setLong(1, idCardPatient);
                cs.registerOutParameter(2, Types.OTHER);
                cs.execute();
                List<ResponseReport> treatment = new ArrayList<>();
                try (ResultSet rs = (ResultSet)cs.getObject( 2 )) {
                    while (rs.next()) {
                        ResponseReport responseReport = new ResponseReport();
                        responseReport.setCountTreatment(rs.getLong("count_solution" ));
                        responseReport.setNameRehabilitationTreatment(rs.getString("name_solution" ));
                        treatment.add( responseReport );
                        response.setTreatment( treatment );
                        }
                    }
                }
        });
        return response;
    }
 
    /**
     * Отчет по записям пациента за период времени
     * @param reportPatientRequest - входной параметр
     * @return RecordPatientReport
     * @throws Exception
     */
    public RecordPatientReport reportByPatietnWithRecordPatient( ReportPatientRequest reportPatientRequest ) throws Exception {
        List<RecordPatient> list = recordPatientRepository.findByParam( reportPatientRequest.getIdPatient(),
                                                                        reportPatientRequest.getStart() ,
                                                                        reportPatientRequest.getEnd());
        return new RecordPatientReport( cardPatientService.findByPatientId( reportPatientRequest.getIdPatient()),
                                        list.stream().count(),
                                        list );
    }

    

}
