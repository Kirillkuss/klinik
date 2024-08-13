package com.klinik.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.klinik.entity.Doctor;
import com.klinik.response.BaseResponseError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping( value = "doctors")
@Tag(name = "1. Doctors", description = "Доктора:")
@ApiResponses(value = {
    @ApiResponse( responseCode = "200", description = "Успешно", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = Doctor.class))) }),
    @ApiResponse( responseCode = "400", description = "Плохой запрос",    content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
    @ApiResponse( responseCode = "500", description = "Ошибка сервера",   content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
@SecurityRequirement(name = "Bearer Authentication")
public interface IDoctor {

    @GetMapping(value = "/fio/{word}{page}{size}")
    @Operation( description = "Поиск врача по ФИО", summary = "Поиск врача по ФИО")
    public ResponseEntity<List<Doctor>> findByFIO( @Parameter( description = "ФИО врача") String word,
                                                   @Parameter( description = "страница") int page,
                                                   @Parameter( description = "размер") int size  ) throws Exception;

    @PostMapping( value = "/add")
    @Operation( description = "Добавить доктора", summary = "Добавить доктора")
    public ResponseEntity<Doctor> addDoctor(@RequestBody Doctor doc ) throws Exception;

    @PostMapping("/lazy")
    @Operation( description ="", summary = "")
    public ResponseEntity<List<Doctor>> getLazyDoctors( int page, int size );

    @GetMapping("/counts")
    public ResponseEntity<Long> getCountDoctors();
    
}
