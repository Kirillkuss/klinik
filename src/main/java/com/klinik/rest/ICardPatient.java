package com.klinik.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.klinik.entity.Card_patient;
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

@RequestMapping( value = "CardPatient")
@Tag(name = "4. Card Patient", description = "Карта пациента")
public interface ICardPatient {
    @GetMapping(value = "/findCardPatientByDocument")
    @Operation( description = "Поиск карты пациента по документу пациента (СНИЛС, номер документа, ПОЛИС)", summary = "Поиск карты пациента по документу пациента")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Карта пациента найдена по документу пациента", content = { @Content( array = @ArraySchema(schema = @Schema(implementation = ResponseEntity.class ))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос ",                               content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",                               content = { @Content( array = @ArraySchema(schema = @Schema( implementation =  BaseResponseError.class ))) })
    })
    public ResponseEntity<Card_patient> findByDocumentPatient(@Parameter( description = "Параметр поиска:", example = "123243453") String word) throws Exception, MyException;
    @GetMapping(value = "/ByIdCard")
    @Operation( description = "Поиск карты пациента по ИД карты", summary = "Поиск карты пациента по ИД карты")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Карта пациента найдена по ИД карты пациента", content = { @Content( array = @ArraySchema(schema = @Schema(implementation = BaseResponse.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",                               content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",                              content = { @Content( array = @ArraySchema(schema = @Schema( implementation =  BaseResponseError.class ))) })
    })
    public ResponseEntity<Card_patient> getByIdCard( @Parameter( description = "ИД карты пациента", example ="1") Long id ) throws Exception, MyException;
    @GetMapping(value = "/ByIdPatient")
    @Operation( description = "Поиск карты пациента по ИД пациента", summary = "Поиск карты пациента по ИД пациента")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Карта пациента найдена по ИД пациента", content = { @Content( array = @ArraySchema(schema = @Schema(implementation = BaseResponse.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",                         content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",                        content = { @Content( array = @ArraySchema(schema = @Schema( implementation =  BaseResponseError.class ))) })
    })
    public ResponseEntity<Card_patient> getByIdPatient (@Parameter( description = "ИД Пациента", example = "1") Long id ) throws Exception, MyException;
    @PostMapping (value = "/saveCardPatient")
    @Operation( description = "Добавить карту пациента", summary = "Добавить карту пациента")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Карта пациента добавлена", content = { @Content( array = @ArraySchema(schema = @Schema(implementation = BaseResponse.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",            content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",           content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
    public ResponseEntity<Card_patient> saveCardPatient( Card_patient card_patient, @Parameter( description = "ИД пациента:") Long id_patient) throws Exception, MyException;
    @PostMapping (value = "/addComplaintToCardPatient")
    @Operation( description = "Добавление жалобы пациенту", summary = "Добавление жалобы пациенту")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Добавление жалобы в карту пациента", content = { @Content( array = @ArraySchema(schema = @Schema(implementation = BaseResponse.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",                      content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",                     content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
    public ResponseEntity<Card_patient> saveComplaintToCardPatient( @Parameter( description = "ИД карты пациента:", example = "1") Long idCard,
                                                                    @Parameter( description = "ИД Под жалобы:" , example =  "1") Long idComplaint ) throws Exception, MyException;

    
}
