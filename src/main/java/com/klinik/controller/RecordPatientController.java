package com.klinik.controller;

import com.klinik.entity.Record_patient;
import com.klinik.service.RecordPatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Records Patients", description = "Записи пациентов")
public class RecordPatientController {

    @Autowired
    private RecordPatientService service;

    @GetMapping(value = "/RecordsPatients")
    @Operation( description = "Список всех записей пациентов", summary = "Список всех записей пациентов")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Found the Records Patients", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = Record_patient.class))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( ))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( ))) })
    })
    public List<Record_patient> allListRecordPatient() throws Exception{
        return service.allListRecordPatient();
    }
}
