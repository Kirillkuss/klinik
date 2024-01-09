package com.klinik.repositories;

import com.klinik.entity.RecordPatient;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordPatientRepository extends JpaRepository<RecordPatient,Long> {

    @Query("SELECT u FROM RecordPatient u WHERE u.cardPatientId = :id and (( u.dateRecord >= :fromLDT)  and (  u.dateRecord <= :toLDT))" )
    List<RecordPatient> findByParamTwo( Long id, LocalDateTime fromLDT, LocalDateTime toLDT) throws Exception;

    @Query( value = "SELECT r.* FROM Record_patient r left join Card_patient c on c.id_card_patient = card_patient_id left join Patient p on p.id_patient = c.patient_id where p.id_patient = ?1 and (r.date_record BETWEEN ?2 and ?3 )", nativeQuery = true  )
    List<RecordPatient> findByParam( Long idCardPatient, LocalDateTime fromLDT, LocalDateTime toLDT) throws Exception;
}


