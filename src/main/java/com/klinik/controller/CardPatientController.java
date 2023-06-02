package com.klinik.controller;

import com.klinik.entity.Card_patient;
import com.klinik.entity.Patient;
import com.klinik.excep.MyException;
import com.klinik.response.BaseResponse;
import com.klinik.response.BaseResponseError;
import com.klinik.response.ResponseCardPatientByDocument;
import com.klinik.service.CardPatientService;
import com.klinik.service.ComplaintService;
import com.klinik.service.PatientService;
import com.klinik.service.TypeComplaintService;

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

@RequestMapping( value = "CardPatient")
@RestController
@Tag(name = "4. Card Patient", description = "Карта пациента")
public class CardPatientController {

    @Autowired
    private CardPatientService service;

    @Autowired
    private PatientService servicePatient;

    @Autowired
    private TypeComplaintService serviceTypeComplaint;

    @GetMapping(value = "/findCardPatientByDocument")
    @Operation( description = "Поиск карты пациента по документу пациента (СНИЛС, номер документа, ПОЛИС)", summary = "Поиск карты пациента по документу пациента")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Карта пациента найдена по документу пациента", content = { @Content( array = @ArraySchema(schema = @Schema(implementation = ResponseCardPatientByDocument.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос ",                      content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",                      content = { @Content( array = @ArraySchema(schema = @Schema( implementation =  BaseResponseError.class ))) })
    })
    public String findByDocumentPatient(@Parameter( description = "Параметр поиска:", example = "123243453") String word ) throws Exception, MyException {
        BaseResponse response = new BaseResponse( 200, "успешно");
        try{
            Card_patient card_patient = service.findByNumberPolisSnils( word );
            if ( card_patient.getId_card_patient() == null ) throw new MyException( 30, "Карта паицента не найдена");
            response.setResponse( card_patient );
            return response.toString();
        }catch ( MyException ex){
            return BaseResponse.error( ex.getCode(), ex ).toString();
        }
    }

    @GetMapping(value = "/ByIdCard")
    @Operation( description = "Поиск карты пациента по ИД карты", summary = "Поиск карты пациента по ИД карты")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Карта пациента найдена по ИД карты пациента", content = { @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseCardPatientByDocument.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",       content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",content = { @Content( array = @ArraySchema(schema = @Schema( implementation =  BaseResponseError.class ))) })
    })
    public String getByIdCard( @Parameter( description = "ИД карты пациента", example ="1") Long id ) throws Exception, MyException {
        BaseResponse response = new BaseResponse( 200, "успешно");
        try{
            Card_patient result = service.findByIdCard( id );
            if( result == null ) throw new MyException( 433, "Карты с таким идентификатором карты не существует");
            response.setResponse(result);
            return response.toString();
        }catch ( MyException ex){
            return BaseResponse.error( ex.getCode(), ex ).toString();
        }
    }
    
    @GetMapping(value = "/ByIdPatient")
    @Operation( description = "Поиск карты пациента по ИД пациента", summary = "Поиск карты пациента по ИД пациента")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Карта пациента найдена по ИД пациента", content = { @Content( array = @ArraySchema(schema = @Schema(implementation = ResponseCardPatientByDocument.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",       content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",content = { @Content( array = @ArraySchema(schema = @Schema( implementation =  BaseResponseError.class ))) })
    })
    public String getByIdPatient (@Parameter( description = "ИД Пациента", example = "1") Long id ) throws Exception, MyException {
        BaseResponse response = new BaseResponse( 200, "успешно");
        try{
            Card_patient result = service.findByPatientId( id );
            if( result == null ) throw new MyException( 434, "Карты с таким идентификатором пациента не существует");
            response.setResponse( result );
            return response.toString();
        }catch ( MyException ex){
            return BaseResponse.error( ex.getCode(), ex ).toString();
        }
    }

    @PostMapping (value = "/saveCardPatient")
    @Operation( description = "Добавить карту пациента", summary = "Добавить карту пациента")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Карта пациента добавлена", content = { @Content( array = @ArraySchema(schema = @Schema(implementation = ResponseCardPatientByDocument.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",       content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
    public String saveCardPatient( Card_patient card_patient,
         @Parameter( description = "ИД пациента:") Long id_patient) throws Exception, MyException{
            BaseResponse response = new BaseResponse( 200, "успешно");
        try{
            Patient patient     = servicePatient.findById( id_patient );
            if( service.findByPatientId( id_patient ) != null ) throw new MyException( 430, "Карта пациента с таким ИД пациента уже существует");
            if( service.findByIdCard( card_patient.getId_card_patient() ) != null ) throw new MyException ( 432, "Карта с таким ИД уже существует");
            card_patient.setPatient( patient );
            response.setResponse(  service.saveCardPatient( card_patient ));
            return response.toString();
        }catch ( MyException ex ){
            return BaseResponse.error( ex.getCode(), ex ).toString();
        }
    }

    @PostMapping (value = "/addComplainttoCardPatient")
    @Operation( description = "Добавление жалобы пациенту", summary = "Добавление жалобы пациенту")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Добавление жалобы в карту пациента", content = { @Content( array = @ArraySchema(schema = @Schema(implementation = ResponseCardPatientByDocument.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",       content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
    public String saveCardPatient( @Parameter( description = "ИД карты пациента:", example = "1") Long idCard,
                                         @Parameter( description = "ИД Поджалобы:" , example =  "1")  Long idComplaint ) throws Exception, MyException{
         BaseResponse response = new BaseResponse( 200, "успешно");
        try{
            if( service.findByIdCard( idCard ) == null ) throw new MyException ( 433, "Карта с таким ИД не существует");
            if ( serviceTypeComplaint.findById( idComplaint ) == null ) throw  new MyException( 434, "Жалобы с таким ИД не существует");
            if ( service.findByIdCardAndIdComplaint(idCard, idComplaint).getId_card_patient() != null ) throw new MyException ( 435, "Жалоба с таким ИД уже добавлена в карту пацинета");
            service.addCardPatientComplaint( idCard, idComplaint );
            return response.toString();
        }catch ( MyException ex ){
            return BaseResponse.error( ex.getCode(), ex ).toString();
        }
    }

}
