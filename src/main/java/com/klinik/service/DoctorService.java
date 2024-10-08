package com.klinik.service;

import com.klinik.aspect.logger.annotation.ExecuteTimeLog;
import com.klinik.entity.Doctor;
import com.klinik.repositories.DoctorRerository;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.NoSuchElementException;


@Slf4j
@Service
public class DoctorService {

    @Autowired public  DoctorRerository doctorRerository;
    @Autowired private EntityManager    entityManager;

    @SuppressWarnings("unchecked")
    public List<Doctor> allDoctor( int page, int size ){
        log.info( "allDoctor - page >> " + page + " size >> " + size );
        long startTime = System.currentTimeMillis();
        // 1. Method execution time - 1200 - 3200 ms
        /**List<Doctor> response =  doctorRerository.findAll()
                                                .stream()
                                                .skip(( page - 1 ) * size )
                                                .limit( size )
                                                .toList();*/
        // 2. Method execution time - 2 - 15 ms                                        
        List<Doctor> response = entityManager.createNativeQuery( "select * from Doctor", Doctor.class)  
                                            .setFirstResult((page - 1) * size)
                                            .setMaxResults(size)
                                            .getResultList(); 
        // 3. Method execution time - 60 - 120 ms                                     
        //List<Doctor> response = doctorRerository.findAll( PageRequest.of( page - 1, size )).getContent();
        log.info( "Method execution time - allDoctor: " + (System.currentTimeMillis() - startTime) + " ms" ); 
        return response;
                                             
    }

    @SuppressWarnings("unchecked")
    @Cacheable("getLazyDoctorCache") 
    @ExecuteTimeLog(operation = "getLazyDoctor")
    public List<Doctor> getLazyDoctor( int page, int size ){
        return entityManager.createNativeQuery( "select * from Doctor", Doctor.class)
                            .setFirstResult((page - 1) * size)
                            .setMaxResults(size)
                            .getResultList();
    }

    public Long getCountDoctors(){
        long startTime = System.currentTimeMillis();
        log.info( "getCountDoctors >> " );
        // Method execution time ~ 60 ms 
        long response =  doctorRerository.count();

        // Method execution time ~ 2400 ms 
        //long response =  doctorRerository.findAll().stream().count();
        
        // Method execution time ~ 60 ms 
        //long response = (long)entityManager.createNativeQuery( "select COUNT(*) from Doctor", Long.class).getResultList().stream().findFirst().orElse( null );
        log.info( "Method execution time - getCountDoctors: " + (System.currentTimeMillis() - startTime) + " ms" ); 
        return response;
    }

    @ExecuteTimeLog(operation = "findByFIO")
    public List<Doctor> findByFIO( String word, int page, int size ) throws Exception{
        List<Doctor> response = doctorRerository.findDoctorByFIO( "%"+ word + "%" )
                                                .stream()
                                                .skip(( page - 1 ) * size )
                                                .limit( size )
                                                .toList();
        if( response.isEmpty() ) throw new NoSuchElementException( "По данному запросу ничего не найдено");
        return response;
    }

    public Doctor saveDoctor( Doctor doctor ) throws Exception{
        doctor.setIdDoctor( new Random().nextLong() );
        Doctor response = doctorRerository.save( doctor );
        log.info( " saveDoctor >> " + response );
        return response;
    }

}
