package com.klinik.controller;

import com.klinik.entity.Document;
import com.klinik.entity.Patient;
import com.klinik.excep.MyException;
import com.klinik.response.BaseResponseError;
import com.klinik.response.ResponsePatient;
import com.klinik.service.DocumentService;
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
import org.springframework.web.bind.annotation.*;

@RequestMapping( value = "Patients")
@RestController
@Tag(name = "2. Patient", description = "Пациенты:")
public class PatientController {

    @Autowired
    private PatientService service;

    @Autowired DocumentService docService;

    @GetMapping(value = "/getAllPatients")
    @Operation( description = "Список всех пациентов", summary = "Список всех пациентов")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Found the patients", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = ResponsePatient.class ))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class  ))) })
    })
    public ResponsePatient getAllPatients() throws Exception, MyException{
        ResponsePatient response = new ResponsePatient( 200, "sucess");
        try{
            response.setPatients( service.getAllPatients() );
            return response;
        }catch( Exception ex ){
            return ResponsePatient.error( 999, ex );
        }
    }

    @PostMapping( value = "/addPatient")
    @Operation( description = "Добавить пациента", summary = "Добавить пациента")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Add patient",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = ResponsePatient.class ))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
    public ResponsePatient addPatient(Patient patient,  @Parameter Long id) throws Exception, MyException{
        ResponsePatient response = new ResponsePatient( 200, "success");
        try{
            Document document = docService.findById( id );
            if( service.findByPhone( patient.getPhone() ) != null ) throw new MyException( 423, "Пользователь с таким номером телефона уже существует, укажите другой");
            if( service.findByIdDocument( id ) != null ) throw new MyException( 420, "Не верное значение ИД документа, попробуйте другой");
            if( service.findById( patient.getId_patient()) != null )  throw new MyException( 421, "Пользователь с таким ИД уже существует");
            if( docService.findById( id ) == null) throw new MyException( 422, "Документ с таким ИД не существует");
            patient.setDocument( document );
            response.setPatient( service.addPatient(patient) );
            return response;
        }catch( MyException ex ){
            return ResponsePatient.error( ex.getCode(), ex );
        }
    }

    @RequestMapping( method = RequestMethod.GET, value = "/findByWord")
    @Operation( description = "Поиск пациента по ФИО или номеру телефона", summary = "Поиск пациента по ФИО или номеру телефона")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Patient find by word", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = ResponsePatient.class ))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
    public ResponsePatient findByWord( @Parameter( description = "Параметр поиска")  String word ) throws Exception, MyException{
        ResponsePatient response = new ResponsePatient( 200, "success");
        try{
            response.setPatients( service.findByWord( word ));
            return response;
        }catch( Exception ex ){
           return  ResponsePatient.error( 999 , ex);
        }
    }

 }
