package com.klinik.controller;

import com.klinik.entity.Card_patient;
import com.klinik.entity.Patient;
import com.klinik.entity.Сomplaint;
import com.klinik.excep.MyException;
import com.klinik.response.BaseResponseError;
import com.klinik.response.ResponseCardPatient;
import com.klinik.service.CardPatientService;
import com.klinik.service.ComplaintService;
import com.klinik.service.PatientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping( value = "CardPatient")
@RestController
@Tag(name = "CardPatient", description = "Карта пациента")
public class CardPatientController {

    @Autowired
    private CardPatientService service;

    @Autowired
    private PatientService servicePatient;

    @Autowired
    private ComplaintService serviceComplaint;

    @GetMapping(value = "/getAll")
    @Operation( description = "Список всех карт пациентов", summary = "Список всех карт пациентов")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Found the cards patients", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ResponseCardPatient.class))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation =  BaseResponseError.class ))) })
    })
    public ResponseCardPatient getAllCards() throws Exception, MyException {
        ResponseCardPatient response = new ResponseCardPatient( 200, "success");
        try{
            response.setListCardPatient( service.allListCardPatient() );
            return response;
        }catch ( Exception ex){
            return ResponseCardPatient.error( 999, ex );
        }
    }

    @GetMapping(value = "/ByIdCard")
    @Operation( description = "Поиск карты пациента по ИД карты", summary = "Поиск карты пациента по ИД карты")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Found the card by ID_card", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ResponseCardPatient.class))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation =  BaseResponseError.class ))) })
    })
    public ResponseCardPatient getByIdCard( @Parameter( description = "ID Card Patint") Long id ) throws Exception, MyException {
        ResponseCardPatient response = new ResponseCardPatient( 200, "success");
        try{
            Card_patient result = service.findByIdCard( id );
            if( result == null ) throw new MyException( 433, "Карты с таким идентификатором карты не существует");
            response.setCardPatient(result);
            return response;
        }catch ( Exception ex){
            return ResponseCardPatient.error( 999, ex );
        }
    }
    
    @GetMapping(value = "/ByIdPatient")
    @Operation( description = "Поиск карты пациента по ИД пациента", summary = "Поиск карты пациента по ИД пациента")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Found the card by ID_patient", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ResponseCardPatient.class))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation =  BaseResponseError.class ))) })
    })
    public ResponseCardPatient getByIdPatient (@Parameter( description = "ID Patint") Long id ) throws Exception, MyException {
        ResponseCardPatient response = new ResponseCardPatient( 200, "success");
        try{
            Card_patient result = service.findByPatientId( id );
            if( result == null ) throw new MyException( 434, "Карты с таким идентификатором пациента не существует");
            response.setCardPatient( result );
            return response;
        }catch ( Exception ex){
            return ResponseCardPatient.error( 999, ex );
        }
    }

    @PostMapping (value = "/saveCardPatient")
    @Operation( description = "Список всех карт пациентов", summary = "Список всех карт пациентов")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Add the card patient", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ResponseCardPatient.class))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
    public ResponseCardPatient saveCardPatient( Card_patient card_patient,
         @Parameter(description = "Ид жалобы:") Long id_complaint,
         @Parameter( description = "ИД пациента:") Long id_patient) throws Exception, MyException{
        ResponseCardPatient response = new ResponseCardPatient( 200, "success");
        try{
            Сomplaint сomplaint = serviceComplaint.findById( id_complaint );
            Patient patient     = servicePatient.findById( id_patient );
            if( service.findByPatientId( id_patient ) != null ) throw new MyException( 430, "Карта пациента с таким ИД пациента уже существует");
            if(  сomplaint == null ) throw new MyException( 431, "Неверный Ид жалобы");
            if( service.findByIdCard( card_patient.getId_card_patient() ) != null ) throw new MyException ( 432, "Карта с таким ИД уже существует");
            card_patient.setPatient( patient );
            card_patient.setComplaint( сomplaint );
            response.setCardPatient( service.saveCardPatient( card_patient ) );
            return response;
        }catch ( Exception ex ){
            return ResponseCardPatient.error( 999, ex );
        }
    }
}
