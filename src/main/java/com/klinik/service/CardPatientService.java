package com.klinik.service;

import com.klinik.entity.Card_patient;
import com.klinik.repositories.CardPatientRepository;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

@Service
public class CardPatientService {

    @PersistenceContext
    EntityManager em;

    @Autowired
    private CardPatientRepository repository;

    public List<Card_patient> allListCardPatient() throws Exception{
        return repository.findAll();
    }

    public Card_patient saveCardPatient( Card_patient card_patient ) throws Exception{
        return repository.save( card_patient );
    }

    public Card_patient findByPatientId( Long id ) throws Exception{
        return ( Card_patient ) em.createQuery("SELECT u FROM Card_patient u where u.patient.id_patient = :id")
                                  .setParameter("id", id )
                                  .getResultList()
                                  .stream().findFirst().orElse( null );
    }

    public Card_patient findByIdCard( Long id ) throws Exception{
        return ( Card_patient ) em.createQuery("SELECT u FROM Card_patient u where u.id_card_patient = :id")
                .setParameter("id", id )
                .getResultList()
                .stream().findFirst().orElse( null );
    }

    @Transactional
    public void addCardPatientComplaint( Long IdCard, Long IdComplaint ) throws Exception{
        em.createNativeQuery( "INSERT INTO Card_patient_Complaint(card_patient_id, complaint_id) VALUES (?,?)")
                .setParameter(1, IdCard)
                .setParameter( 2, IdComplaint)
                .executeUpdate();
    }

    public Card_patient findByIdCardAndIdComplaint( Long idCard, Long IdComplaint ) throws Exception{
        String sql = "SELECT u.id_card_patient FROM Card_patient u "
                    + " left join Card_patient_Complaint cpc on cpc.card_patient_id = u.id_card_patient "
                    + " left join Complaint c on c.id_complaint = cpc.complaint_id "
                    +" WHERE u.id_card_patient = ? and c.id_complaint = ? ";
        Session session;
        Card_patient card = new Card_patient();
        try{
            session = em.unwrap( Session.class);
            session.doWork( (Connection conn ) ->{
                try( PreparedStatement st = conn.prepareStatement( sql )){
                    st.setLong(1, idCard);
                    st.setLong( 2, IdComplaint);
                    try( ResultSet set = st.executeQuery()){
                        while( set.next()){
                            card.setId_card_patient( set.getLong(1));
                        }
                    }
                }
            });

        }catch ( Exception ex ){
        }        
        return card;
    }


}
