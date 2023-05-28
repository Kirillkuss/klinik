package com.klinik.controller;

import com.klinik.entity.Doctor;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;

@RequestMapping( value = "RecordsPatients")
@RestController
@Tag(name = "Records Patients", description = "Записи пациентов")
public class RecordPatientController {

    
    @Autowired
    private RecordPatientService service;

    @Autowired
    private DoctorService serviceDoctor;

    @Autowired
    private CardPatientService servicePatientCard;

    //@GetMapping(value = "/getAllRecordPatient")
    @Operation( description = "Список всех записей пациентов к врачу", summary = "Список всех записей пациентов к врачу")
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
        }catch( Exception ex ){
            return ResponseRecordPatient.error( 999, ex );
        }
    }

    @PostMapping (value = "/addRecordPatient")
    @Operation( description = "Добавить запись пациента к врачу", summary = "Добавить запись пациента к врачу")
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
            Doctor doctor = serviceDoctor.findById( doctor_id );
            if ( serviceDoctor.findById( doctor_id ) == null ) throw new MyException( 440, "Нет доктора с таким идентификатором");
            if ( servicePatientCard.findByIdCard( card_patient_id ) == null ) throw new MyException( 441, "Нет карты пациента с таким идентификатором");
            if ( service.findById( record_patient.getId_record()) != null) throw new MyException( 442, "Запись к врачу с таким ИД уже существует, установите другой ИД записи к врачу");
            record_patient.setDoctor(doctor);;
            record_patient.setCard_patient_id( card_patient_id );
            response.setRecordPatient( service.saveRecordPatient( record_patient) );
            return response;
        }catch( Exception ex ){
            return ResponseRecordPatient.error( 999, ex );
        }                                 
    }


    @GetMapping(value = "/findByParams")
    @Operation( description = "Список всех записей пациентов к врачу по параметрам", summary = "Список всех записей пациентов к врачу по параметрам ")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Found the Records Patients", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = ResponseRecordPatient.class))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
    public ResponseRecordPatient findByParams( @Parameter(description = "ИД карты пациента", example = "1") Long id,
                                               @Parameter(description = "Дата записи с:", example = "2023-02-19T12:47:07.605")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
                                               @Parameter(description = "Дата записи по:", example = "2023-05-19T12:47:07.605") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo ) throws Exception, MyException{
        ResponseRecordPatient response = new ResponseRecordPatient( 200, "success");
        try{
            response.setListRecordPatient( service.findByParam(id, dateFrom, dateTo));;
            return response;
        }catch( Exception ex ){
            return ResponseRecordPatient.error( 999, ex );
        }
    }

    
}
