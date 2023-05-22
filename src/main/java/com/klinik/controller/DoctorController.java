package com.klinik.controller;

import com.klinik.entity.Doctor;
import com.klinik.service.DoctorService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Doctors", description = "Доктора")
public class DoctorController {

    @Autowired
    private DoctorService service;

    @GetMapping(value = "/Doctors")
    @Operation( description = "Список всех докторов", summary = "Список всех докторов")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Found the Doctors", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = Doctor.class))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( ))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( ))) })
    })
    public List<Doctor> getAllDoc() throws Exception{
        return service.allDoctor();
    }

    @GetMapping(value = "/FindDoctorByFIO")
    @Operation( description = "Поиск врача по ФИО", summary = "Поиск врача по ФИО")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Found the Doctor", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = Doctor.class))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( ))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( ))) })
    })
    public List<Doctor> findByFIO(@Parameter( description = "ФИО врача") String word ) throws Exception{
        return service.findByFIO( word );
    }
}
