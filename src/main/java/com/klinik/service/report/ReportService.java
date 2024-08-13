package com.klinik.service.report;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import com.klinik.entity.RecordPatient;
import com.klinik.excep.MyException;
import com.klinik.repositories.CardPatientRepository;
import com.klinik.repositories.RecordPatientRepository;
import com.klinik.response.ReportDrug;
import com.klinik.response.report.CardPatinetReport;
import com.klinik.response.report.RecordPatientReport;
import com.klinik.response.report.ResponseReport;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import java.sql.Types;
import java.sql.CallableStatement;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final EntityManager entityManager;
    private final CardPatientRepository cardPatientRepository;
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
     * 
     * @param dateFrom - дата и время начала фильтра
     * @param dateTo - дата и время окончания фильтра
     * @return List<ReportDrug>
     * @throws Exception
     */
    public List<ReportDrug> reportStatDrug( LocalDateTime dateFrom, LocalDateTime dateTo ) throws Exception {
        List<ReportDrug> response = new ArrayList<>();
            entityManager.unwrap(Session.class).doWork((Connection conn) -> {
                try (CallableStatement cs = conn.prepareCall("{ call report_stat_drug( ?,?,? ) }")) {
                    conn.setAutoCommit(false);
                    cs.setTimestamp(1, Timestamp.valueOf(dateFrom));
                    cs.setTimestamp(2, Timestamp.valueOf(dateTo));
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
        response.setCard(cardPatientRepository.findById(idCardPatient).orElseThrow(() -> new NoSuchElementException("Карты пациента с таким ИД не существует")));
        entityManager.unwrap(Session.class).doWork((Connection conn) -> {
            try(CallableStatement cs = conn.prepareCall("{ call record_patient( ?,?)}")){
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
     * 
     * @param IdPatient - Ид пацинента 
     * @param dateFrom  - Время записи с 
     * @param dateTo    - Время записи по 
     * @return RecordPatientReport
     * @throws Exception
     */
    public RecordPatientReport reportByPatietnWithRecordPatient( Long IdPatient, LocalDateTime dateFrom, LocalDateTime dateTo ) throws Exception {
        RecordPatientReport report = new RecordPatientReport();
        List<RecordPatient> list = recordPatientRepository.findByParam(IdPatient, dateFrom, dateTo);
        report.setCard(cardPatientRepository.findByPatientId( IdPatient )
                                            .orElseThrow( () -> new NoSuchElementException( "Пациента с таким ИД не существует" )));
        report.setCountRecordForTime( list.stream().count() );
        report.setListRecordPatient( list );
        return report;
    }

}
