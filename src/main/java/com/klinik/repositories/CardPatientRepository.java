package com.klinik.repositories;

import com.klinik.entity.CardPatient;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CardPatientRepository extends JpaRepository<CardPatient, Long> {
    @Query("SELECT u FROM CardPatient u where u.patient.idPatient = :id")
    Optional<CardPatient> findByPatientId( Long id );
    //@Query( "SELECT cp FROM CardPatient cp where cp.patient.document.numar LIKE :parametr or cp.patient.document.snils LIKE :parametr or cp.patient.document.polis LIKE :parametr")
    @Query( "SELECT cp FROM CardPatient cp where cp.patient.document.numar LIKE :parametr or cp.patient.document.snils LIKE :parametr or cp.patient.document.polis LIKE :parametr  or CONCAT( cp.patient.surname, ' ', cp.patient.name, ' ', cp.patient.fullName ) LIKE :parametr ")
    List<CardPatient> findByNPS( @Param("parametr") String parametr );
    @Query( value = "SELECT * FROM Card_patient u left join Card_patient_Complaint cpc on cpc.card_patient_id = u.id_card_patient left join Type_complaint c on c.id_type_complaint = cpc.type_complaint_id WHERE u.id_card_patient = ?1 and c.id_type_complaint = ?2", nativeQuery = true )
    Optional<CardPatient> findByIdCardAndIdComplaint(Long idCard, Long idTypeComplaint);
}
