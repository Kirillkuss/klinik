package com.klinik.service;

import com.klinik.aspect.logger.annotation.ExecuteTimeLog;
import com.klinik.entity.CardPatient;
import com.klinik.entity.Patient;
import com.klinik.repositories.CardPatientRepository;
import com.klinik.repositories.PatientRepository;
import com.klinik.repositories.TypeComplaintRepository;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardPatientService {
    
    private final EntityManager em;

    private final TypeComplaintRepository typeComplaintRepository;
    private final CardPatientRepository   cardPatientRepository;
    private final PatientRepository       patientRepository;
    
    public CardPatient saveCardPatient( CardPatient cardСatient,  Long idPatient ) throws Exception{
        if( cardPatientRepository.findByPatientId( idPatient ).isPresent()) throw new IllegalArgumentException( "Карта пациента с таким ИД пациента уже существует");
        if( cardPatientRepository.findById( cardСatient.getIdCardPatient() ).isPresent() ) throw new IllegalArgumentException( "Карта с таким ИД уже существует");
        Optional<Patient> patient = patientRepository.findById( idPatient );
        if( patient.isEmpty() ) throw new IllegalArgumentException ( "Пациента с таким ИД не существует");
        cardСatient.setPatient( patient.get());
        return cardPatientRepository.save( cardСatient );
    }
    @ExecuteTimeLog(operation = "findByPatientId")
    public CardPatient findByPatientId( Long id ){ 
        return cardPatientRepository.findByPatientId( id )
                                    .orElseThrow( () -> new NoSuchElementException( "Карты с таким ИД пациента не существует" ));
    }
    @ExecuteTimeLog(operation = "findByIdCard")
    public CardPatient findByIdCard( Long id ){
        return cardPatientRepository.findById( id )
                                    .orElseThrow( () -> new NoSuchElementException( "Карты с таким ИД карты не существует" ));
    }
    @Transactional
    public void addCardPatientComplaint( Long idCard, Long idComplaint ) throws Exception{
        checkaddCardPatientComplaint( idCard, idComplaint);
        em.createNativeQuery( "INSERT INTO Card_patient_Complaint(card_patient_id, type_complaint_id) VALUES (?,?)")
                .setParameter(1, idCard)
                .setParameter( 2, idComplaint)
                .executeUpdate();
    }

    private void checkaddCardPatientComplaint( Long idCard, Long idComplaint ){
        if ( cardPatientRepository.findById( idCard ).isEmpty()) throw new IllegalArgumentException (  "Карта с таким ИД не существует");
        if ( typeComplaintRepository.findById( idComplaint ).isEmpty() ) throw new IllegalArgumentException ( "Под жалобы с таким ИД не существует");
        if ( cardPatientRepository.findByIdCardAndIdComplaint( idCard, idComplaint ).isPresent() ) throw new IllegalArgumentException ( "Под жалоба с таким ИД уже добавлена в карту пацинета");
    }
    /**
     * Поиск карты пациента по документу пациента ( полис/снилс/номер )
     * @param parametr - параметр поиска
     * @return Card_patient
     * @throws Exception
     */
    @ExecuteTimeLog(operation = "findByNPSCardPatient")
    public CardPatient findByNPS( String parametr ) throws Exception{
        return cardPatientRepository.findByNPS( parametr ).orElseThrow();
    }


}
