package com.klinik.controller;

import com.klinik.entity.Treatment;
import com.klinik.service.TreatmentService;
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
@Tag(name = "Treatment", description = "Лечение")
public class TreatmentController {

    @Autowired
    private TreatmentService service;

    @GetMapping(value = "/Treatment")
    @Operation( description = "Получение всего справочника лечение", summary = "Получение всего справочника лечение")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Found the Treatment", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = Treatment.class))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( ))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( ))) })
    })
    public List<Treatment> getAllTreatent() throws Exception{
        return service.allListTreatment();
    }
}
