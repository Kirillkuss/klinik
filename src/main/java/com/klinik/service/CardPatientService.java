package com.klinik.service;

import com.klinik.entity.CardPatient;
import com.klinik.entity.Patient;
import com.klinik.excep.MyException;
import com.klinik.repositories.CardPatientRepository;
import com.klinik.repositories.PatientRepository;
import com.klinik.repositories.TypeComplaintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardPatientService {
    

    @PersistenceContext
    EntityManager em;

    private final TypeComplaintRepository typeComplaintRepository;
    private final CardPatientRepository   cardPatientRepository;
    private final PatientRepository       patientRepository;
    public CardPatient saveCardPatient( CardPatient cardСatient,  Long idPatient ) throws Exception{
        if( cardPatientRepository.findByPatientId( idPatient ).isPresent()) throw new MyException( 409, "Карта пациента с таким ИД пациента уже существует");
        if( cardPatientRepository.findById( cardСatient.getIdCardPatient() ).isPresent() ) throw new MyException ( 409, "Карта с таким ИД уже существует");
        Optional<Patient> patient = patientRepository.findById( idPatient );
        if( patient.isEmpty() ) throw new MyException ( 400, "Пациента с таким ИД не существует");
        cardСatient.setPatient( patient.get());
        return cardPatientRepository.save( cardСatient );
    }
    public CardPatient findByPatientId( Long id ){ 
        return cardPatientRepository.findByPatientId( id )
                                    .orElseThrow( () -> new NoSuchElementException( "Карты с таким ИД пациента не существует" ));
    }
    public CardPatient findByIdCard( Long id ){
        return cardPatientRepository.findById( id )
                                    .orElseThrow( () -> new NoSuchElementException( "Карты с таким ИД карты не существует" ));
    }
    @Transactional
    public void addCardPatientComplaint( Long IdCard, Long IdComplaint ) throws Exception{
        if ( cardPatientRepository.findById( IdCard ).isEmpty()) throw new MyException ( 400, "Карта с таким ИД не существует");
        if ( typeComplaintRepository.findById( IdComplaint ).isEmpty() ) throw new MyException ( 400, "Под жалобы с таким ИД не существует");
        if ( cardPatientRepository.findByIdCardAndIdComplaint(IdCard, IdComplaint).isPresent() ) throw new MyException ( 409, "Под жалоба с таким ИД уже добавлена в карту пацинета");
        em.createNativeQuery( "INSERT INTO Card_patient_Complaint(card_patient_id, type_complaint_id) VALUES (?,?)")
                .setParameter(1, IdCard)
                .setParameter( 2, IdComplaint)
                .executeUpdate();
    }
    /**
     * Поиск карты пациента по документу пациента ( полис/снилс/номер )
     * @param parametr - параметр поиска
     * @return Card_patient
     * @throws Exception
     */
    public CardPatient findByNPS( String parametr ) throws Exception{
        return cardPatientRepository.findByNPS( parametr ).orElseThrow();
    }


}
