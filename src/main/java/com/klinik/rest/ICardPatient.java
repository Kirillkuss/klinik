package com.klinik.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.klinik.entity.CardPatient;
import com.klinik.excep.MyException;
import com.klinik.response.BaseResponseError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping( value = "card-patinets")
@Tag(name = "4. Card Patient", description = "Карта пациента")
    @ApiResponses(value = {
        @ApiResponse( responseCode = "200", description = "Успешно",        content = { @Content( array = @ArraySchema(schema = @Schema(implementation = CardPatient.class ))) }),
        @ApiResponse( responseCode = "400", description = "Плохой запрос ", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
        @ApiResponse( responseCode = "500", description = "Ошибка сервера", content = { @Content( array = @ArraySchema(schema = @Schema( implementation =  BaseResponseError.class ))) })
    })
public interface ICardPatient {
    @GetMapping(value = "/document")
    @Operation( description = "Поиск карты пациента по документу пациента (СНИЛС, номер документа, ПОЛИС)", summary = "Поиск карты пациента по документу пациента")
    public ResponseEntity<CardPatient> findByDocumentPatient( @Parameter( description = "Параметр поиска:", example = "123243453") String word) throws Exception, MyException;
    @GetMapping(value = "/card/{id}")
    @Operation( description = "Поиск карты пациента по ИД карты", summary = "Поиск карты пациента по ИД карты")
    public ResponseEntity<CardPatient> getByIdCard( @Parameter( description = "ИД карты пациента", example ="1") Long id ) throws Exception, MyException;
    @GetMapping(value = "/patient/{id}")
    @Operation( description = "Поиск карты пациента по ИД пациента", summary = "Поиск карты пациента по ИД пациента")
    public ResponseEntity<CardPatient> getByIdPatient (@Parameter( description = "ИД Пациента", example = "1") Long id ) throws Exception, MyException;
    @PostMapping (value = "/add")
    @Operation( description = "Добавить карту пациента", summary = "Добавить карту пациента")
    public ResponseEntity<CardPatient> saveCardPatient( @RequestBody CardPatient card, @Parameter( description = "ИД пациента:", example = "1") Long idpatient) throws Exception, MyException;
    @PostMapping (value = "/complaint/{id-card}{id-complaint}")
    @Operation( description = "Добавление жалобы пациенту", summary = "Добавление жалобы пациенту")
    public ResponseEntity saveComplaintToCardPatient( @Parameter( description = "ИД карты пациента:", example = "1") Long idcard,
                                                      @Parameter( description = "ИД Под жалобы:" , example =  "1") Long idcomplaint ) throws Exception, MyException;

    
}
