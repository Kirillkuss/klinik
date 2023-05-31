package com.klinik.controller;

import com.klinik.entity.Doctor;
import com.klinik.excep.MyException;
import com.klinik.response.BaseResponse;
import com.klinik.response.BaseResponseError;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping( value = "Doctors")
@RestController
@Tag(name = "1. Doctors", description = "Доктора:")
public class DoctorController {

    @Autowired
    private DoctorService service;

    @GetMapping(value = "/getAll")
    @Operation( description = "Список всех докторов", summary = "Список всех докторов")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Found the Doctors", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
    public BaseResponse<List<Doctor>>  getAllDoc() throws Exception{
        BaseResponse response = new BaseResponse( 200, "success");
        try{
            response.setResponse(service.allDoctor());
            return response;
        }catch ( Exception ex ){
            return new BaseResponse<>().error( 999, ex);
        }
        
    }

    @GetMapping(value = "/FindDoctorByFIO")
    @Operation( description = "Поиск врача по ФИО", summary = "Поиск врача по ФИО")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Found the Doctor", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
    public BaseResponse<List<Doctor>> findByFIO(@Parameter( description = "ФИО врача") String word ) throws Exception{
        BaseResponse response = new BaseResponse<>( 200, "success");
        try{
            response.setResponse( service.findByFIO( word ));
            return response;
        }catch( Exception ex ){
            return new BaseResponse<>().error( 999, ex);
        }
    }

    @PostMapping( value = "/addDoctor")
    @Operation( description = "Добавить доктора", summary = "Добавить доктора")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Save the Doctor", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
    public BaseResponse<Doctor> addDoctor( Doctor doctor ) throws Exception{
        BaseResponse response = new BaseResponse( 200, "success");
        try{
            if( service.findById( doctor.getId_doctor() ) != null ) throw new MyException( 450, "Доктор с таким ИД уже существует");
            response.setResponse(service.saveDoctor( doctor ));
            return response;
        }catch( MyException ex ){
            return new BaseResponse().error( ex.getCode(), ex);
        }
        
    }
}
