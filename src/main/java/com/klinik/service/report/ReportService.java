package com.klinik.service.report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {

    @PersistenceContext
    private EntityManager em;

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
        try {
            String sql = "SELECT t.name as name_solution, COUNT( u.rehabilitation_solution_id ) as count_solution, COUNT(DISTINCT u.card_patient_id) as count_patient FROM Treatment u"
                    + " left join Rehabilitation_solution t on t.id_rehabilitation_solution = u.rehabilitation_solution_id"
                    + " where  u.time_start_treatment BETWEEN ? and ?  group by t.name";
            Session session;
            session = em.unwrap(Session.class);
            session.doWork((Connection conn) -> {
                try (PreparedStatement st = conn.prepareStatement(sql)) {
                    st.setTimestamp(1, Timestamp.valueOf(dateFrom));
                    st.setTimestamp(2, Timestamp.valueOf(dateTo));
                    try (ResultSet set = st.executeQuery()) {
                        while (set.next()) {
                            ResponseReport responseReport = new ResponseReport();
                            responseReport.setName_rehabilitation_treatment(set.getString(1));
                            responseReport.setCount_treatment(set.getLong(2));
                            responseReport.setCount_patient(set.getLong(3));
                            report.add(responseReport);
                        }
                    }
                } catch (SQLException ex) {
                    java.util.logging.Logger.getLogger(ReportService.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ReportService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return report;
    }

    public List<ReportDrug> reportStatDrug(LocalDateTime dateFrom, LocalDateTime dateTo) throws Exception {
        List<ReportDrug> response = new ArrayList<>();
        try {
            String request = "SELECT dt.name , COUNT( u.drug_id ) as count_drug_treatment, COUNT(DISTINCT u.card_patient_id) as count_patient FROM Treatment u "
                    + " left join Drug d on d.id_dr = u.drug_id "
                    + " left join Drug_treatment dt on dt.id_drug = d.drug_id"
                    + " where u.time_start_treatment BETWEEN ? and ? group by dt.name ";
            Session session = em.unwrap(Session.class);
            session.doWork((Connection conn) -> {
                try (PreparedStatement ps = conn.prepareStatement(request)) {
                    ps.setTimestamp(1, Timestamp.valueOf(dateFrom));
                    ps.setTimestamp(2, Timestamp.valueOf(dateTo));
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            ReportDrug drug = new ReportDrug();
                            drug.setNameDrugTreatment(rs.getString(1));
                            drug.setCountDrugTreatment(rs.getLong(2));
                            drug.setCountPatient(rs.getLong(3));
                            response.add(drug);
                        }
                    }
                }
            });
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ReportService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return response;
    }

    /**
     * Отчет о полной информации по пациенту
     * 
     * @param idCardPatient - ид карты пациента
     * @return ResponsePatientReport
     * @throws Exception
     */
    public CardPatinetReport reportInformationAboutPatient(Long idCardPatient) throws Exception, MyException {
        CardPatinetReport response = new CardPatinetReport();
        response.setCard(cardPatientRepository.findById(idCardPatient)
                .orElseThrow(() -> new NoSuchElementException("Карты пациента с таким ИД не существует")));
        try {
            String sql2 = "SELECT t.name as name_solution, COUNT( u.rehabilitation_solution_id ) as count_solution FROM Treatment u"
                    + " left join Rehabilitation_solution t on t.id_rehabilitation_solution = u.rehabilitation_solution_id"
                    + " left join Card_patient c on  c.id_card_patient =u.card_patient_id"
                    + " where  c.id_card_patient  = ?  group by t.name";
            Session session;
            session = em.unwrap(Session.class);
            session.doWork((Connection conn) -> {
                try {
                    List<ResponseReport> treatment = new ArrayList<>();
                    try (PreparedStatement st2 = conn.prepareStatement(sql2)) {
                        st2.setLong(1, idCardPatient);
                        try (ResultSet rs2 = st2.executeQuery()) {
                            while (rs2.next()) {
                                ResponseReport responseReport = new ResponseReport();
                                responseReport.setCount_treatment(rs2.getLong(2));
                                responseReport.setName_rehabilitation_treatment(rs2.getString(1));
                                treatment.add(responseReport);
                                response.setTreatment(treatment);
                            }
                        }
                    }

                } catch (SQLException ex) {
                    java.util.logging.Logger.getLogger(ReportService.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ReportService.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        report.setCard(cardPatientRepository.findByPatientId(IdPatient).orElseThrow(() -> new NoSuchElementException( "Пациента с таким ИД не существует" )));
        report.setCountRecordForTime(list.stream().count());
        report.setListRecordPatient(list);
        return report;
    }

}
