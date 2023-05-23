package com.klinik.repositories;

import com.klinik.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Modifying
    @Query( "SELECT u FROM Patient u WHERE u.suraname = :word or u.name = :word or u.full_name = :word or u.phone =:word")
    List<Patient> findPatientByWord( String word );

    @Query( "SELECT u FROM Patient u WHERE u.document.id_document = :id_document")
    Patient findPatientByIdDocument( Long id_document );

    /**@Modifying
    @Query ( "INSERT INTO Patient ( id_patient, suraname, name, full_name, gender, phone, address, document) VALUES (  :id, :surn, :nam, :full_nam, :gende, :phon, :addres )")
    Patient createPatient(  @Param("id") Long id,
                            @Param("surn") String surn,
                            @Param("nam") String nam,
                            @Param("full_nam") String full_nam,
                            @Param("gende") Boolean gende,
                            @Param("phon") String phon,
                            @Param("addres") String addres )**/
}   
