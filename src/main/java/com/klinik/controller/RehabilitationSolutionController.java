package com.klinik.controller;

import com.klinik.entity.Rehabilitation_solution;
import com.klinik.service.RehabilitationSolutionService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "RehabilitationSolution", description = "Реабилитационное лечение")
public class RehabilitationSolutionController {

    @Autowired
    private RehabilitationSolutionService service;

    @GetMapping(value = "/RehabilitationSolution")
    @Operation( description = "Список всех реабилитационных лечений", summary = "Список всех Реабилитационных лечений")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Found the Rehabilitation Solution", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = Rehabilitation_solution.class))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( ))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( ))) })
    })
    public List<Rehabilitation_solution> getAllRehabilitationSolution() throws Exception{
        return service.getAllReha();
    }

    @RequestMapping( method = RequestMethod.GET, value = "/findByName")
    @Operation( description = "Поиск по названию лечения", summary = "Поиск по названию лечения")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Found the Rehabilitation Solution", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = Rehabilitation_solution.class))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( ))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( ))) })
    })
    public Rehabilitation_solution findByName( @Parameter( description = "Наименование лечения") String name ) throws Exception{
        return service.findByName( name );
    }
}
