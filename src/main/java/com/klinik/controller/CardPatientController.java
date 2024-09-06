package com.klinik.controller;

import com.klinik.entity.CardPatient;
import com.klinik.excep.MyException;
import com.klinik.repositories.CardPatientRepository;
import com.klinik.request.CoplaintRequest;
import com.klinik.rest.ICardPatient;
import com.klinik.service.CardPatientService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CardPatientController implements ICardPatient{

    private final CardPatientService  cardPatientService;
    private final CardPatientRepository cardPatientRepository;
    
    public ResponseEntity<CardPatient> findByDocumentPatient( String word ) throws Exception, MyException {
        return new ResponseEntity<>( cardPatientService.findByNPS( word ), HttpStatus.OK );
    } 
    public ResponseEntity<CardPatient> getByIdCard( Long id ) throws Exception, MyException {
        return new ResponseEntity<>(cardPatientService.findByIdCard( id ), HttpStatus.OK);
    }
    public ResponseEntity<CardPatient> getByIdPatient ( Long id ) throws Exception, MyException {
        return new ResponseEntity<>( cardPatientService.findByPatientId( id ), HttpStatus.OK );
    }
    public ResponseEntity<CardPatient> saveCardPatient( CardPatient cardPatient, Long idPatient) throws Exception, MyException{
        return new ResponseEntity<>( cardPatientService.saveCardPatient( cardPatient, idPatient ), HttpStatus.OK);
    }
    public ResponseEntity saveComplaintToCardPatient(  CoplaintRequest coplaintRequest ) throws Exception, MyException{
        cardPatientService.addCardPatientComplaint( coplaintRequest.getIdCard(), coplaintRequest.getIdComplaint() );
        return new ResponseEntity<>( HttpStatus.CREATED );
    }
    @Override
    public ResponseEntity<List<CardPatient>> getLazyCardPatient(int page, int size) {
        return new ResponseEntity<>( cardPatientService.getLazyCardPatient( page, size ), HttpStatus.OK);
    }
    @Override
    public ResponseEntity<Long> getCountCardPatient() {
        return new ResponseEntity<>( cardPatientRepository.count(), HttpStatus.OK);
    }
}
