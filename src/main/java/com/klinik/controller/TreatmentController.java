package com.klinik.controller;

import com.klinik.entity.Treatment;
import com.klinik.response.BaseResponseError;
import com.klinik.service.ComplaintService;
import com.klinik.service.RehabilitationSolutionService;
import com.klinik.service.TreatmentService;
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

import java.util.List;

@RestController
@Tag(name = "Treatment", description = "Лечение")
public class TreatmentController {

    @Autowired
    private TreatmentService service;

    @Autowired
    private RehabilitationSolutionService rehabilitationSolutionService; 

    @GetMapping(value = "/Treatment")
    @Operation( description = "Получение списка всех лечений", summary = "Получение списка всех лечений")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Found the Treatment", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = Treatment.class))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
    public List<Treatment> getAllTreatment() throws Exception{
        return service.allListTreatment();
    }

    @PostMapping( value = "/addTreatment")
    @Operation( description = "Добавить лечение для пациента", summary = "Добавить лечение для пациента")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Create the Treatment", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = Treatment.class))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation =  BaseResponseError.class))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
    public Treatment addTreatment( Treatment treatment,
                                  @Parameter( description = "Ид карты пациента") Long card_patient_id,
                                  @Parameter( description = "Ид реабилитационного лечения") Long rehabilitation_solution_id) throws Exception{
        treatment.setCard_patient_id( card_patient_id );
        treatment.setRehabilitation_solution( rehabilitationSolutionService.findById(rehabilitation_solution_id));
        return service.addTreatment( treatment );
    }

}
