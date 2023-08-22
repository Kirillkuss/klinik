package com.klinik.service;

import com.klinik.entity.Document;
import com.klinik.entity.Patient;
import com.klinik.excep.MyException;
import com.klinik.repositories.DocumentRepository;
import com.klinik.repositories.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final DocumentRepository documentRepository; 

    public List<Patient> getAllPatients(){
        log.info( "Alls Patient");
        return patientRepository.findAll();
    }

    public Patient addPatient( Patient patient, Long id ) throws Exception{
        if( patientRepository.findByPhone( patient.getPhone() ) != null )  throw new MyException( 409, "Пользователь с таким номером телефона уже существует, укажите другой");
        if( patientRepository.findPatientByIdDocument( id ) != null )      throw new MyException( 400, "Неверное значение ИД документа, попробуйте другой");
        if( patientRepository.findById( patient.getIdPatient()).isPresent()) throw new MyException( 409, "Пользователь с таким ИД уже существует");
        Optional<Document> document = documentRepository.findById( id );
        if( document.isEmpty()) throw new MyException( 400, "Документ с таким ИД не существует");
        patient.setDocument( document.get() );
        log.info( "add Patient");
        return patientRepository.save( patient );
    }

    public List<Patient> findByWord( String word ) throws Exception{
        List<Patient> response = patientRepository.findPatientByWord( word );
        if ( response.isEmpty() == true ) throw new MyException( 404, "По данному запросу ничего не найдено");
        log.info( "findByWord Patient");
        return response;
    }

}
