package com.klinik.repositories;

import com.klinik.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DoctorRerository extends JpaRepository<Doctor, Long> {

    @Modifying
    //@Query( "SELECT u FROM Doctor u WHERE u.surname LIKE : word or u.name LIKE :word or u.fullName LIKE :word ")
    @Query( "SELECT u FROM Doctor u WHERE CONCAT( u.surname, ' ', u.name, ' ', u.fullName ) LIKE :word ")
    List<Doctor> findDoctorByFIO( @Param("word") String word );

    @Query("SELECT u FROM Doctor u WHERE u.idDoctor = :id")
    Doctor findByIdDoctor( Long id );
}
