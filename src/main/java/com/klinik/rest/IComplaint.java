package com.klinik.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.klinik.entity.TypeComplaint;
import com.klinik.entity.Сomplaint;
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

@RequestMapping( value = "Сomplaint")
@Tag(name = "6. Сomplaint", description = "Справочник: Жалобы и под жалобы ")
public interface IComplaint {
    @GetMapping(value = "/getAll")
    @Operation( description = "Получение справочника жалобы", summary = "Получение справочника жалобы")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Получен список жалобы", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = Сomplaint.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",         content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",        content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
    public ResponseEntity findAll() throws Exception;
    @Operation( description = "Добавление справочника жалобы", summary = "Добавление справочника жалобы")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Добавлена жалоба", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = Сomplaint.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",    content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",   content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
    @PostMapping( value = "/saveСomplaint")
    public ResponseEntity saveСomplaint( com.klinik.entity.Сomplaint сomplaint ) throws Exception;
    @Operation( description = "Добавление под жалобы", summary = "Добавление под жалобы")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Добавлена под жалоба", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = Сomplaint.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",       content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",      content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
    @PostMapping( value = "/TypeComplaint")
    public ResponseEntity saveTypeComplaint(TypeComplaint request, @Parameter( description = "ИД жалобы", example = "1") Long idComplaint ) throws Exception;
    @Operation( description = "Получение жалобы с под жалобами", summary = "Получение жалобы с под жалобами")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Получение жалобы с под жалобами", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = TypeComplaint.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",                   content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",                  content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
    @GetMapping( "/findComplaintWithTypeComplaints")
    public ResponseEntity listComplaintWithTypeComplaints( @Parameter( description = "Ид жалобы", example = "1" )Long Id ) throws Exception;
    
}
