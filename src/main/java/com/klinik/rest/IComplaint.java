package com.klinik.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.klinik.request.RequestTypeComplaint;
import com.klinik.entity.Complaint;
import com.klinik.response.BaseResponseError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping( value = "complaints")
@Tag(name = "6. Сomplaint", description = "Справочник: Жалобы и под жалобы ")
@ApiResponses(value = {
        @ApiResponse( responseCode = "200", description = "Успешно",        content = { @Content( array = @ArraySchema(schema = @Schema( implementation = Complaint.class))) }),
        @ApiResponse( responseCode = "400", description = "Плохой запрос",  content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
        @ApiResponse( responseCode = "500", description = "Ошибка сервера", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
public interface IComplaint {
    @GetMapping(value = "/list")
    @Operation( description = "Получение справочника жалобы", summary = "Получение справочника жалобы")
    public ResponseEntity findAll() throws Exception;
    @Operation( description = "Добавление справочника жалобы", summary = "Добавление справочника жалобы")
    @PostMapping( value = "/complaint")
    public ResponseEntity saveСomplaint( @RequestBody Complaint complaint ) throws Exception;
    @Operation( description = "Добавление под жалобы", summary = "Добавление под жалобы")
    @PostMapping( value = "/typecomplain")
    public ResponseEntity saveTypeComplaint( @RequestBody RequestTypeComplaint requestTypeComplaint ) throws Exception;
    @Operation( description = "Получение жалобы с под жалобами", summary = "Получение жалобы с под жалобами")
    @GetMapping( "/type/{id}")
    public ResponseEntity listComplaintWithTypeComplaints( @Parameter( description = "Ид жалобы", example = "1" )Long id ) throws Exception;
    
}
