package com.klinik.controller;

import com.klinik.entity.Card_patient;
import com.klinik.excep.MyException;
import com.klinik.rest.ICardPatient;
import com.klinik.service.CardPatientService;
import com.klinik.service.PatientService;
import com.klinik.service.TypeComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardPatientController implements ICardPatient{

    @Autowired private CardPatientService   cardPatientService;
    @Autowired private PatientService       servicePatient;
    @Autowired private TypeComplaintService serviceTypeComplaint;

    public ResponseEntity<Card_patient> findByDocumentPatient( String word ) throws Exception, MyException {
        if ( cardPatientService.findByNumberPolisSnils( word ).getId_card_patient() == null ) throw new MyException( 404, "Карта паицента не найдена");
        return new ResponseEntity<>( cardPatientService.findByNumberPolisSnils( word ), HttpStatus.OK );
    } 

    public ResponseEntity<Card_patient> getByIdCard( Long id ) throws Exception, MyException {
        if( cardPatientService.findByIdCard( id ) == null ) throw new MyException( 404, "Карты с таким идентификатором карты не существует");
        return new ResponseEntity<>(cardPatientService.findByIdCard( id ), HttpStatus.OK);
    }
    
    public ResponseEntity<Card_patient> getByIdPatient ( Long id ) throws Exception, MyException {
        if( cardPatientService.findByPatientId( id ) == null ) throw new MyException( 404, "Карты с таким идентификатором пациента не существует");
        return new ResponseEntity<>( cardPatientService.findByPatientId( id ), HttpStatus.OK );
    }

    public ResponseEntity<Card_patient> saveCardPatient( Card_patient card_patient, Long id_patient) throws Exception, MyException{
        if( cardPatientService.findByPatientId( id_patient ) != null )                     throw new MyException( 409, "Карта пациента с таким ИД пациента уже существует");
        if( cardPatientService.findByIdCard( card_patient.getId_card_patient() ) != null ) throw new MyException ( 409, "Карта с таким ИД уже существует");
        if( servicePatient.findById( id_patient ) == null )                                throw new MyException ( 400, "Пациента с таким ИД не существует");
        card_patient.setPatient( servicePatient.findById( id_patient ));
        return new ResponseEntity<>( cardPatientService.saveCardPatient( card_patient ), HttpStatus.OK);
    }
    public ResponseEntity<Card_patient> saveComplaintToCardPatient( Long idCard, Long idComplaint ) throws Exception, MyException{
        if ( cardPatientService.findByIdCard( idCard ) == null )                                               throw new MyException ( 400, "Карта с таким ИД не существует");
        if ( serviceTypeComplaint.findById( idComplaint ) == null )                                            throw new MyException ( 400, "Под жалобы с таким ИД не существует");
        if ( cardPatientService.findByIdCardAndIdComplaint(idCard, idComplaint).getId_card_patient() != null ) throw new MyException ( 409, "Под жалоба с таким ИД уже добавлена в карту пацинета");
        cardPatientService.addCardPatientComplaint( idCard, idComplaint );
        return new ResponseEntity<>( HttpStatus.CREATED );
    }

}
