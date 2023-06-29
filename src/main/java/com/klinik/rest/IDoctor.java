package com.klinik.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.klinik.entity.Doctor;
import com.klinik.response.BaseResponse;
import com.klinik.response.BaseResponseError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping( value = "Doctors")
@Tag(name = "1. Doctors", description = "Доктора:")
public interface IDoctor {
    @GetMapping(value = "/getAll")
    @Operation( description = "Список всех докторов", summary = "Список всех докторов")
    @ApiResponses(value = {
        @ApiResponse( responseCode = "200", description = "Список всех докторов", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = Doctor.class))) }),
        @ApiResponse( responseCode = "400", description = "Плохой запрос",        content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
        @ApiResponse( responseCode = "500", description = "Ошибка сервера",       content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
    public ResponseEntity  getAllDoc() throws Exception;
    @GetMapping(value = "/FindDoctorByFIO")
    @Operation( description = "Поиск врача по ФИО", summary = "Поиск врача по ФИО")
    @ApiResponses(value = {
        @ApiResponse( responseCode = "200", description = "Поиск врача по ФИО", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = Doctor.class))) }),
        @ApiResponse( responseCode = "400", description = "Плохой запрос",      content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
        @ApiResponse( responseCode = "500", description = "Ошибка сервера",     content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
    public ResponseEntity findByFIO(@Parameter( description = "ФИО врача") String word ) throws Exception;
    @PostMapping( value = "/addDoctor")
    @Operation( description = "Добавить доктора", summary = "Добавить доктора")
    @ApiResponses(value = {
        @ApiResponse( responseCode = "200", description = "Добавить доктора", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = Doctor.class))) }),
        @ApiResponse( responseCode = "400", description = "Плохой запрос",    content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
        @ApiResponse( responseCode = "500", description = "Ошибка сервера",   content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
    public ResponseEntity addDoctor( Doctor doctor ) throws Exception;
    
}
