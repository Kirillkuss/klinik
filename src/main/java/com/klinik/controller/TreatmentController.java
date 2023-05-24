package com.klinik.controller;

import com.klinik.entity.Card_patient;
import com.klinik.entity.Rehabilitation_solution;
import com.klinik.entity.Treatment;
import com.klinik.excep.MyException;
import com.klinik.response.BaseResponseError;
import com.klinik.response.ResponseTreatment;
import com.klinik.service.CardPatientService;
import com.klinik.service.ComplaintService;
import com.klinik.service.RehabilitationSolutionService;
import com.klinik.service.TreatmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping( value = "Treatment")
@RestController
@Tag(name = "Treatment", description = "Лечение")
public class TreatmentController {

    @Autowired
    private TreatmentService service;

    @Autowired
    private RehabilitationSolutionService rehabilitationSolutionService;
    
    @Autowired CardPatientService cardPatientService;

    @GetMapping(value = "/getAllTreatment")
    @Operation( description = "Получение списка всех лечений", summary = "Получение списка всех лечений")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Found the Treatment", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = ResponseTreatment.class))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
    public ResponseTreatment getAllTreatment() throws Exception{
        ResponseTreatment response = new ResponseTreatment( 200, "success");
        try{
            response.setResponse( service.allListTreatment());
            return response;
        }catch( Exception ex ){
            return new ResponseTreatment().error( 999, ex ); 
        }

    }

    @PostMapping( value = "/addTreatment")
    @Operation( description = "Добавить лечение для пациента", summary = "Добавить лечение для пациента")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Create the Treatment", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = ResponseTreatment.class))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation =  BaseResponseError.class))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
    public ResponseTreatment addTreatment( Treatment treatment,
                                  @Parameter( description = "Ид карты пациента") Long card_patient_id,
                                  @Parameter( description = "Ид реабилитационного лечения") Long rehabilitation_solution_id) throws Exception{
        ResponseTreatment response = new ResponseTreatment( 200, "success");
        try{
            if( service.findById( treatment.getId_treatment()) != null  ) throw new MyException( 470, "Лечение с таким ИД уже существует, используйте другой");
            Rehabilitation_solution solution = rehabilitationSolutionService.findById(rehabilitation_solution_id);
            if( solution == null  ) throw new MyException( 471, "Указано неверное значение реабилитационного лечения, укажите другой");
            Card_patient card_patient = cardPatientService.findByIdCard(card_patient_id );
            if( card_patient == null ) throw new MyException( 472, "Указано неверное значение карты пациента, укажите другой");
            treatment.setCard_patient_id( card_patient.getId_card_patient() );
            treatment.setRehabilitation_solution( solution );
            response.setTreatment(service.addTreatment( treatment ));
            return response;
        }catch( Exception ex ){
            return new ResponseTreatment().error( 999, ex );
        }                          
    }

    @GetMapping(value = "/findByParamIdCardAndDateStart")
    @Operation( description = "Получение списка лечений по параметрам", summary = "Получение списка лечений по параметрам")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Found the Treatments", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = ResponseTreatment.class))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
    public ResponseTreatment findByParamIdCardAndDateStart( @Parameter( description = "Ид карты", example = "1") Long id,
                                                            @Parameter( description = "Время начала лечения с:", example = "2023-01-20T12:47:07.605") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
                                                            @Parameter( description = "Время начала лечения по", example = "2023-09-20T12:47:07.605") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo) throws Exception{
        ResponseTreatment response = new ResponseTreatment( 200, "success");
        try{
            response.setResponse( service.findByParamIdCardAndDateStart(id, dateFrom, dateTo));
            return response;
        }catch( Exception ex ){
            return new ResponseTreatment().error( 999, ex ); 
        }
    }

    @GetMapping(value = "/findByParamIdCardAndIdRh")
    @Operation( description = "Получение списка лечений по параметрам", summary = "Получение списка лечений по параметрам")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Found the Treatments", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = ResponseTreatment.class))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
    public ResponseTreatment findByParamIdCardAndIdRh( @Parameter( description = "Ид карты пациента", example = "1") Long idCard, 
                                                       @Parameter( description = "Ид реабилитационного лечения", example = "1") Long idReSol ) throws Exception{
        ResponseTreatment response = new ResponseTreatment( 200, "success");
        try{
            response.setResponse( service.findByParamIdCardAndIdRh( idCard, idReSol ));
            return response;
        }catch( Exception ex ){
            return new ResponseTreatment().error( 999, ex ); 
        }
    }

}
