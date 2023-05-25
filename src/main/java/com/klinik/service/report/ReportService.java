package com.klinik.service.report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.klinik.response.report.models.Card_patient;
import com.klinik.response.report.models.Patient;
import com.klinik.response.report.models.Сomplaint;
import com.klinik.excep.MyException;
import com.klinik.response.BaseResponse;
import com.klinik.response.report.ResponsePatientReport;
import com.klinik.response.report.ResponseReport;


@Service
public class ReportService {

    @PersistenceContext
    private EntityManager em;
    
    public List<ResponseReport> getStatReport( LocalDateTime dateFrom, LocalDateTime dateTo) throws Exception, MyException{
        List<ResponseReport> report = new ArrayList<>();
        try{
            String sql = "SELECT t.name as name_solution, COUNT( u.rehabilitation_solution_id ) as count_solution, COUNT(DISTINCT u.card_patient_id) as count_patient FROM Treatment u" 
            +" left join Rehabilitation_solution t on t.id_rehabilitation_solution = u.rehabilitation_solution_id"
            +" where  u.time_start_treatment BETWEEN ? and ?  group by t.name";
            //u.rehabilitation_solution_id=? and
            Session session;
            session = em.unwrap( Session.class );
            session.doWork(( Connection conn) ->{
                try ( PreparedStatement st = conn.prepareStatement( sql )){
                    st.setTimestamp(1 , Timestamp.valueOf( dateFrom ));
                    st.setTimestamp(2 , Timestamp.valueOf( dateTo ));
                    try( ResultSet set = st.executeQuery()){
                        while( set.next()){
                            ResponseReport responseReport = new ResponseReport();
                            responseReport.setName_rehabilitation_treatment( set.getString( 1 ));
                            responseReport.setCount_treatment( set.getLong( 2 ));
                            responseReport.setCount_patient( set.getLong( 3 ));
                            report.add(responseReport);
                        }
                    }
                }catch(SQLException ex ){
                    System.out.println("SQL error >>> " + ex.getErrorCode());
                }
            });
        }catch( Exception ex ){
            System.out.println( ex.getMessage());
        }
        return report;
    }

    /**
     * @param idCardPatient
     * @return
     * @throws Exception
     */
    public ResponsePatientReport reportInformationAboutPatient(Long idCardPatient ) throws Exception{
        ResponsePatientReport response = new ResponsePatientReport();
        try{
            String sql ="SELECT c.diagnosis, c.allergy, c.note, c.сonclusion, p.surname, p.name, p.full_name,p.gender, p.phone, p.address, s.functional_impairment, count( t.id_treatment) FROM Card_patient c"
            + " left join Patient p on id_patient = c.pacient_id"
            + " left join Complaint s on id_complaint = c.complaint_id"
            + " left join Treatment t on card_patient_id = c.id_card_patient"
            + " where c.id_card_patient = ? group by c.diagnosis, c.allergy, c.note, c.сonclusion, p.surname, p.name, p.full_name,p.gender, p.phone, p.address, s.functional_impairment";
            Session session;
            session = em.unwrap( Session.class );
            session.doWork(( Connection conn) ->{
                try ( PreparedStatement st = conn.prepareStatement( sql )){
                    st.setLong(1 , idCardPatient);
                    try( ResultSet rs = st.executeQuery() ){
                        while( rs.next() ){
                            Card_patient card = new Card_patient();
                            card.setDiagnosis( rs.getString( 1 ));
                            card.setAllergy( rs.getBoolean( 2 ));
                            card.setNote( rs.getString( 3 ));
                            card.setСonclusion( rs.getString( 4 ));
                            Patient patient = new Patient();
                            patient.setSurname( rs.getString(5));
                            patient.setName( rs.getString(6));
                            patient.setFull_name( rs.getString(7));
                            patient.setGender( rs.getBoolean(8));
                            patient.setPhone( rs.getString(9));
                            patient.setAddress( rs.getString(10));
                            Сomplaint complaint = new Сomplaint( rs.getString(11));
                            card.setPatient(patient);
                            card.setComplaint( complaint);
                            card.setCount_treatment( rs.getLong( 12 ));
                            response.setCard(card);
                        }
                    }
                }catch(SQLException ex ){
                    System.out.println("SQL error >>> " + ex.getMessage());
                }
            });
        }catch( Exception ex ){
            System.out.println( ex.getMessage());
        }
        return response;
    }
        
       
    
}
