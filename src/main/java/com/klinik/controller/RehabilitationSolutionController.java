package com.klinik.controller;

import com.klinik.entity.Rehabilitation_solution;
import com.klinik.excep.MyException;
import com.klinik.response.BaseResponse;
import com.klinik.response.BaseResponseError;
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

@RequestMapping( value = "RehabilitationTreatment")
@RestController
@Tag(name = "RehabilitationSolution", description = "Реабилитационное лечение")
public class RehabilitationSolutionController {

    @Autowired
    private RehabilitationSolutionService service;

    @GetMapping(value = "/getAllRS")
    @Operation( description = "Список всех реабилитационных лечений", summary = "Список всех Реабилитационных лечений")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Found the Rehabilitation Treatment", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BaseResponseError.class ))) })
    })
    public BaseResponse<List<Rehabilitation_solution>> getAllRehabilitationSolution() throws Exception{
        BaseResponse response = new BaseResponse( 200, "success");
        try{
            response.setResponse( service.getAllReha());
            return response;
        }catch( Exception ex ){
            return new BaseResponse().error( 999, ex);
        }
    }

    @RequestMapping( method = RequestMethod.GET, value = "/findByNameRS")
    @Operation( description = "Поиск по названию лечения", summary = "Поиск по названию лечения")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Found the Rehabilitation Solution", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class ))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
    public BaseResponse<Rehabilitation_solution> findByName( @Parameter( description = "Наименование лечения") String name ) throws Exception{
        BaseResponse response = new BaseResponse( 200, "success");
        try{
            response.setResponse(  service.findByName( name ));
            return response;
        }catch( Exception ex ){
            return new BaseResponse<>().error( 999, ex);
        }
    }

    @Operation( description = "Добавить способ лечения", summary = "Добавить способ лечения")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Save the Rehabilitation Treatment", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(  implementation = BaseResponseError.class))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(  implementation = BaseResponseError.class))) })
    })
    @RequestMapping( method = RequestMethod.POST, value = "/saveRS")
    public BaseResponse<Rehabilitation_solution> save( Rehabilitation_solution solution ) throws Exception{
        BaseResponse response = new BaseResponse( 200, "success");
        try{

            if( service.findByName( solution.getName() ) != null ) throw new MyException( 461, "Ребилитационное лечение с таким наименованием уже существует");
            if( service.findById( solution.getId_rehabilitation_solution() ) != null ) throw new MyException( 460, "Ребилитационное лечение с таким ИД уже существует");
            response.setResponse(service.saveRS( solution ));
            return response;
        }catch( MyException ex ){
            return new BaseResponse().error( ex.getCode(), ex );
        }

    }
}
