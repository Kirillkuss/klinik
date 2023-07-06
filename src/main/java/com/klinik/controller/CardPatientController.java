package com.klinik.controller;

import com.klinik.entity.CardPatient;
import com.klinik.excep.MyException;
import com.klinik.rest.ICardPatient;
import com.klinik.service.CardPatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CardPatientController implements ICardPatient{

    private final CardPatientService   cardPatientService;
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
    public ResponseEntity saveComplaintToCardPatient( Long idCard, Long idComplaint ) throws Exception, MyException{
        cardPatientService.addCardPatientComplaint( idCard, idComplaint );
        return new ResponseEntity<>( HttpStatus.CREATED );
    }
}
