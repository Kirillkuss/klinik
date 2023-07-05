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
}
