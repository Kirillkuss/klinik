package com.klinik.service;

import com.klinik.entity.Card_patient;
import com.klinik.repositories.CardPatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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


}
