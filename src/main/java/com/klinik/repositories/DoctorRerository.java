package com.klinik.repositories;

import com.klinik.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DoctorRerository extends JpaRepository<Doctor, Long> {

    @Modifying
    @Query( "SELECT u FROM Doctor u WHERE u.surname = :word or u.name = :word or u.fullName = :word ")
    List<Doctor> findDoctorByFIO( String word );

    @Query("SELECT u FROM Doctor u WHERE u.idDoctor = :id")
    Doctor findByIdDoctor( Long id );
}
