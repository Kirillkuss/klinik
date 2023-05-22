package com.klinik.controller;

import com.klinik.entity.Patient;
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

import java.util.List;

@RestController
@Tag(name = "Patient", description = "Пациент")
public class PatientController {

    @Autowired
    private PatientService service;

    @GetMapping(value = "/Patients")
    @Operation( description = "Список всех пациентов", summary = "Список всех пациентов")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Found the patients", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = Patient.class))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( ))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( ))) })
    })
    public List<Patient> getAllPatients() throws Exception{
        return service.getAllPatients();
    }

    @PutMapping( value = "/addPatient")
    @Operation( description = "Добавить пациента", summary = "Добавить пациента")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Add patient", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = Patient.class))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( ))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( ))) })
    })
    public Patient addPatient(Patient patient, @Parameter( description = "ИД документа") Long id ) throws Exception{
        patient.setDocument_id( id );
        return service.addPatient( patient );
    }

    @RequestMapping( method = RequestMethod.GET, value = "/findByWord")
    @Operation( description = "Поиск пациента по ФИО или номеру телефона", summary = "Поиск пациента по ФИО или номеру телефона")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Patient find by word", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = Patient.class))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( ))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( ))) })
    })
    public List<Patient> findByWord( @Parameter( description = "Параметр поиска")  String word ) throws Exception{
        return service.findByWord( word );
    }

}
