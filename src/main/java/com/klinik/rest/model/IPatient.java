package com.klinik.rest.model;

import java.util.List;
import javax.ws.rs.core.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.klinik.entity.Patient;
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

@RequestMapping( value = "patients")
@Tag(name = "2. Patient", description = "Пациенты:")
@ApiResponses(value = {
        @ApiResponse( responseCode = "200", description = "Успешно",        content = { @Content( mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema( implementation = Patient.class ))) }),
        @ApiResponse( responseCode = "400", description = "Плохой запрос",  content = { @Content( mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
        @ApiResponse( responseCode = "500", description = "Ошибка сервера", content = { @Content( mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
public interface IPatient {
    
    @GetMapping(value = "/all")
    @Operation( description = "Список всех пациентов", summary = "Список всех пациентов")
    public ResponseEntity<List<Patient>> getAllPatients() throws Exception, MyException;
    
    @PostMapping( value = "/add")
    @Operation( description = "Добавить пациента", summary = "Добавить пациента")
    public ResponseEntity<Patient> addPatient( @RequestBody Patient patient,  @Parameter( description = "Ид документа" , example = "1") Long idDocument) throws Exception, MyException;
    
    @RequestMapping( method = RequestMethod.GET, value = "/find/{word}")
    @Operation( description = "Поиск пациента по ФИО или номеру телефона", summary = "Поиск пациента по ФИО или номеру телефона")
    public ResponseEntity<List<Patient>> findByWord( @Parameter( description = "Параметр поиска")  String word ) throws Exception, MyException;

    @GetMapping(value = "/list/{page}{size}")
    @Operation( description = "ленивая загрузка пациентов", summary = "ленивая загрузка пациентов")
    public ResponseEntity<List<Patient>> getLazyLoad( int page, int size) ;

    @GetMapping(value = "/count")
    @Operation( description ="Количество пациентов", summary = "Количество пациентов")
    public ResponseEntity<Long> getCountPatient( ) ;
}
