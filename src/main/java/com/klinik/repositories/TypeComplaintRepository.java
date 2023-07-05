package com.klinik.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.klinik.entity.TypeComplaint;

@Repository
public interface TypeComplaintRepository extends JpaRepository<TypeComplaint, Long> {
    @Query( "SELECT u from TypeComplaint u WHERE u.name = :name")
    Optional<TypeComplaint> findName( String name);
    @Query( "SELECT u FROM  TypeComplaint u WHERE u.complaint.idComplaint = :id")
    List<TypeComplaint> findByIdComplaint( Long id );
}
