package com.klinik.controller;

import com.klinik.entity.Card_patient;
import com.klinik.service.CardPatientService;
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
@Tag(name = "CardPatient", description = "Карта пациента")
public class CardPatientController {

    @Autowired
    private CardPatientService service;

    @GetMapping(value = "/CardsPatients")
    @Operation( description = "Список всех карт пациентов", summary = "Список всех карт пациентов")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Found the cards patients", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Card_patient.class))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( ))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( ))) })
    })
    public List<Card_patient> getAllCards() throws Exception{
        return service.allListCardPatient();
    }
}
