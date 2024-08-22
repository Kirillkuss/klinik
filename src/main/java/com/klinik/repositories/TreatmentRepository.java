package com.klinik.repositories;

import com.klinik.entity.Treatment;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, Long> {

    @Query("SELECT u FROM Treatment u WHERE u.cardPatientId = :id and (( u.timeStartTreatment >= :from)  and (  u.timeStartTreatment <= :to))" )
    List<Treatment> findByParamIdCardAndDateStart( Long id, LocalDateTime from, LocalDateTime to) throws Exception;

    @Query("SELECT u FROM Treatment u WHERE u.cardPatientId = :idCard and u.rehabilitationSolution.idRehabilitationSolution = :idReSol" )
    List<Treatment> findByParamIdCardAndIdRh( Long idCard, Long idReSol) throws Exception;

    @Query( "SELECT u FROM Treatment u WHERE u.idTreatment = :id")
    Treatment findByIdTreatment( Long id ) throws Exception;
}
