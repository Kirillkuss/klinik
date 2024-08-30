package com.klinik.service;

import com.klinik.aspect.logger.annotation.ExecuteTimeLog;
import com.klinik.entity.Document;
import com.klinik.entity.Patient;
import com.klinik.repositories.DocumentRepository;
import com.klinik.repositories.PatientRepository;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;
import org.springframework.util.StopWatch;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository  patientRepository;
    private final DocumentRepository documentRepository;
    private final EntityManager      entityManager;

    @ExecuteTimeLog(operation = "getAllPatients")
    public List<Patient> getAllPatients(){
        log.info( "All Patients");
        return patientRepository.findAll();
    }

    public Patient addPatient( Patient patient, Long id ) throws Exception{
        checkPatient( patient, id );
        Optional<Document> document = documentRepository.findById( id );
        if( document.isEmpty()) throw new IllegalArgumentException(  "Документ с таким ИД не существует");
        patient.setDocument( document.get() );
        log.info( "add Patient");
        return patientRepository.save( patient );
    }
    
    private void checkPatient( Patient patient, Long id ){
        if( patientRepository.findByPhone( patient.getPhone() ).isPresent() ) throw new IllegalArgumentException( "Пользователь с таким номером телефона уже существует, укажите другой");
        if( patientRepository.findPatientByIdDocument( id ).isPresent() )     throw new IllegalArgumentException( "Неверное значение ИД документа, попробуйте другой");
        if( patientRepository.findById( patient.getIdPatient()).isPresent())  throw new IllegalArgumentException( "Пользователь с таким ИД уже существует");
    }

    @ExecuteTimeLog(operation = "findByWord")
    public List<Patient> findByWord( String word ) throws Exception{
        List<Patient> response = patientRepository.findPatientByWord( word );
        if ( response.isEmpty() == true ) throw new NoSuchElementException( "По данному запросу ничего не найдено");
        log.info( "findByWord Patient");
        return response;
    }


    @SuppressWarnings("unchecked")
    @ExecuteTimeLog(operation = "getLazyPatients")
    public List<Patient> getLazyLoad( int page, int size){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Patient> response =  entityManager.createNativeQuery( "select * from Patient", Patient.class)
                                               .setFirstResult((page - 1) * size)
                                               .setMaxResults(size)
                                               .getResultList();
        stopWatch.stop();
        log.info( "Method execution time - getLazyLoadPatient >> " +  + stopWatch.getTotalTimeMillis() + " ms" );
        return response;                       
    }

}
