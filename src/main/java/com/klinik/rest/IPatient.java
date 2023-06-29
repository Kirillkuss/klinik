package com.klinik.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.klinik.entity.Patient;
import com.klinik.excep.MyException;
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

@RequestMapping( value = "Patients")
@Tag(name = "2. Patient", description = "Пациенты:")
public interface IPatient {
    @GetMapping(value = "/getAllPatients")
    @Operation( description = "Список всех пациентов", summary = "Список всех пациентов")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Получен список всех пациентов", content = { @Content(array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class ))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",                 content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",                content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
    public BaseResponse getAllPatients() throws Exception, MyException;
    @PostMapping( value = "/addPatient")
    @Operation( description = "Добавить пациента", summary = "Добавить пациента")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Пациент добавлен", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class ))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",    content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",   content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
    public BaseResponse addPatient(Patient patient,  @Parameter( description = "Ид документа" , example = "1") Long id) throws Exception, MyException;
    @RequestMapping( method = RequestMethod.GET, value = "/findByWord")
    @Operation( description = "Поиск пациента по ФИО или номеру телефона", summary = "Поиск пациента по ФИО или номеру телефона")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Пациент найден", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class ))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",  content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
    public BaseResponse findByWord( @Parameter( description = "Параметр поиска")  String word ) throws Exception, MyException;
    
}
