package com.klinik.controller;

import com.fasterxml.jackson.databind.JsonSerializable.Base;
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
        @ApiResponse( responseCode = "200", description = "Получен отчет по виду ребилитационного лечения за период времени", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) }),
        @ApiResponse( responseCode = "400", description = "Плохой запрос",       content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) }),
        @ApiResponse( responseCode = "500", description = "Ошибка сервера",content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) })
    })
    public BaseResponse  getAllDoc() throws Exception{
        BaseResponse response = new BaseResponse<>( 200, "success" );
        try{
            response.setResponse(service.allDoctor());
            return response;
        }catch( Exception ex ){
            return BaseResponse.error( 999, ex );
        }
    }

    @GetMapping(value = "/FindDoctorByFIO")
    @Operation( description = "Поиск врача по ФИО", summary = "Поиск врача по ФИО")
    @ApiResponses(value = {
        @ApiResponse( responseCode = "200", description = "Получен отчет по виду ребилитационного лечения за период времени", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) }),
        @ApiResponse( responseCode = "400", description = "Плохой запрос",       content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) }),
        @ApiResponse( responseCode = "500", description = "Ошибка сервера",content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) })
    })
    public BaseResponse findByFIO(@Parameter( description = "ФИО врача") String word ) throws Exception{
        BaseResponse response = new BaseResponse( 200, "success");
        try{
            List<Doctor> doctors = service.findByFIO( word );
            if( doctors.isEmpty() == true ) throw new MyException( 999, "По данному запросу ничего не найдено");
            response.setResponse(doctors);
            return response; 
        }catch( MyException ex ){
            return BaseResponse.error( ex.getCode(), ex);
        }

    }

    @PostMapping( value = "/addDoctor")
    @Operation( description = "Добавить доктора", summary = "Добавить доктора")
    @ApiResponses(value = {
        @ApiResponse( responseCode = "200", description = "Получен отчет по виду ребилитационного лечения за период времени", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) }),
        @ApiResponse( responseCode = "400", description = "Плохой запрос",       content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) }),
        @ApiResponse( responseCode = "500", description = "Ошибка сервера",content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) })
    })
    public BaseResponse addDoctor( Doctor doctor ) throws Exception{
        BaseResponse response = new BaseResponse( 200, "success" ); 
        try{
            if (service.findById( doctor.getId_doctor() ) != null ) throw new MyException( 999, "Пользователь с таким ИД уще существует");
            response.setResponse(service.saveDoctor( doctor ));
            return response;
        }catch( MyException ex ){
            return  BaseResponse.error( ex.getCode(), ex );
        }
        
    }
}
