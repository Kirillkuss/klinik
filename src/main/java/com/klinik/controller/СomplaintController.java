package com.klinik.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.klinik.entity.Сomplaint;
import com.klinik.response.BaseResponseError;
import com.klinik.service.ComplaintService;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Сomplaint", description = "Справочник: Жалобы")
public class СomplaintController {

    @Autowired
    private ComplaintService service;


    @GetMapping(value = "/Сomplaints")
    @Operation( description = "Получение справочника жалобы", summary = "Получение справочника жалобы")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Found the Treatment", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = Сomplaint.class))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
    public List<Сomplaint> findAll() throws Exception{
        return service.listComplaints();
    }

    
    
}
