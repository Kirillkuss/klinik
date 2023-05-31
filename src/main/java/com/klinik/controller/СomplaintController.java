package com.klinik.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
@Tag(name = "6. Сomplaint", description = "Справочник: Жалобы")
public class СomplaintController {

    @Autowired
    private ComplaintService service;


    @GetMapping(value = "/getAll")
    @Operation( description = "Получение справочника жалобы", summary = "Получение справочника жалобы")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Получен список жалобы", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",       content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
    public String findAll() throws Exception{
        BaseResponse response = new BaseResponse( 200, "успешно");
        try{
            response.setResponse(service.listComplaints());
            return response.toString();
        }catch( Exception ex ){
            return new BaseResponse().error( 999, ex ).toString();
        }
        
    }

    @Operation( description = "Добавление справочника жалобы", summary = "Добавление справочника жалобы")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Добавлена жалоба", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",       content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
    @PostMapping( value = "/saveСomplaint")
    public String saveСomplaint( com.klinik.entity.Сomplaint сomplaint ) throws Exception{
        BaseResponse response = new BaseResponse( 200, "успешно");
        try{
            if( service.findById( сomplaint.getId_complaint() ) !=null ) throw new MyException( 460, "Справочник жалоба с таким ИЖ уже существует");
            if( service.findByName( сomplaint.getFunctional_impairment() ) !=null ) throw new MyException( 461, "Справочник жалоба с таким наименованием уже существует");
            response.setResponse( service.saveСomplaint( сomplaint ));
            return response.toString();
        }catch( MyException ex ){
            return new BaseResponse().error( ex.getCode(), ex ).toString();
        }
    }

    
    
}
