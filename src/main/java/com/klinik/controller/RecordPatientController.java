package com.klinik.controller;

import com.klinik.entity.Record_patient;
import com.klinik.excep.MyException;
import com.klinik.response.BaseResponseError;
import com.klinik.response.ResponseRecordPatient;
import com.klinik.service.CardPatientService;
import com.klinik.service.DoctorService;
import com.klinik.service.RecordPatientService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Tag(name = "Records Patients", description = "Записи пациентов")
public class RecordPatientController {

    @Autowired
    private RecordPatientService service;

    @Autowired
    private DoctorService serviceDoctor;

    @Autowired
    private CardPatientService servicePatientCard;

    @GetMapping(value = "/RecordsPatients")
    @Operation( description = "Список всех записей пациентов", summary = "Список всех записей пациентов")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Found the Records Patients", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = ResponseRecordPatient.class))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
    public ResponseRecordPatient allListRecordPatient() throws Exception, MyException{
        ResponseRecordPatient response = new ResponseRecordPatient( 200, "success");
        try{
            response.setListRecordPatient( service.allListRecordPatient() );
            return response;
        }catch( MyException ex ){
            return ResponseRecordPatient.error( ex.getCode(), ex );
        }
    }

    @PostMapping (value = "/addRecordPatient")
    @Operation( description = "Добавить запись к пациенту", summary = "Добавить запись к пациенту")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Found the Records Patients", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = ResponseRecordPatient.class))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
    public ResponseRecordPatient addRecordPatient(Record_patient record_patient,
                                           @Parameter( description = "Ид доктора") Long doctor_id,
                                           @Parameter( description = "Ид карты пациента") Long card_patient_id) throws Exception, MyException{
        ResponseRecordPatient response = new ResponseRecordPatient( 200, "success");                                   
        try{
            if ( serviceDoctor.findById( doctor_id ) == null ) throw new MyException( 440, "Нет доктора с таким идентификатором");
            if ( servicePatientCard.findByIdCard( card_patient_id ) == null ) throw new MyException( 441, "Нет карты пациента с таким идентификатором");
            record_patient.setDoctor_id( doctor_id );
            record_patient.setCard_patient_id( card_patient_id );
            List<Record_patient> list = new ArrayList<>();
            list.add( 0, service.saveRecordPatient( record_patient));
            response.setListRecordPatient( list );
            return response;
        }catch( MyException ex ){
            return ResponseRecordPatient.error( ex.getCode(), ex );
        }                                 

    }
}
