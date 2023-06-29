package com.klinik.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.klinik.entity.Document;
import com.klinik.excep.MyException;
import com.klinik.response.BaseResponse;
import com.klinik.response.BaseResponseError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping( value = "Documents")
@Tag(name = "3. Documents", description = "Документ пациента")
public interface IDocument {
    @GetMapping(value = "/getAllDocunets")
    @Operation( description = "Список всех документов", summary = "Список всех документов")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Получение списка документов", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",               content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",              content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
    public BaseResponse getAllDocuments() throws Exception, MyException;
    @Operation( description = "Добавить документ", summary = "Добавить документ")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Документ добавлен", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",     content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",    content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
    @RequestMapping( method = RequestMethod.POST , value = "/addDocument")
    public BaseResponse addDocument( Document document ) throws Exception, MyException;
    
}
