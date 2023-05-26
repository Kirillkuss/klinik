package com.klinik.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.klinik.entity.Сomplaint;
import com.klinik.excep.MyException;
import com.klinik.response.BaseResponse;
import com.klinik.response.BaseResponseError;
import com.klinik.service.ComplaintService;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping( value = "Сomplaint")
@RestController
@Tag(name = "Сomplaint", description = "Справочник: Жалобы")
public class СomplaintController {

    @Autowired
    private ComplaintService service;


    @GetMapping(value = "/getAll")
    @Operation( description = "Получение справочника жалобы", summary = "Получение справочника жалобы")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Found the All Treatments", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
    public BaseResponse<List<Сomplaint>> findAll() throws Exception{
        BaseResponse response = new BaseResponse( 200, "success");
        try{
            response.setResponse(service.listComplaints());
            return response;
        }catch( Exception ex ){
            return new BaseResponse().error( 999, ex );
        }
        
    }

    @Operation( description = "Добавление справочника жалобы", summary = "Добавление справочника жалобы")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Save the Treatment", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
    @PostMapping( value = "/saveСomplaint")
    public BaseResponse<Сomplaint> saveСomplaint( Сomplaint сomplaint ) throws Exception{
        BaseResponse response = new BaseResponse( 200, "success");
        try{
            if( service.findById( сomplaint.getId_complaint() ) !=null ) throw new MyException( 460, "Справочник жалоба с таким ИЖ уже существует");
            if( service.findByName( сomplaint.getFunctional_impairment() ) !=null ) throw new MyException( 461, "Справочник жалоба с таким наименованием уже существует");
            response.setResponse( service.saveСomplaint( сomplaint ));
            return response;
        }catch( Exception ex ){
            return new BaseResponse().error( 999, ex );
        }
    }

    
    
}
