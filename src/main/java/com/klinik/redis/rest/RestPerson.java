package com.klinik.redis.rest;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.klinik.redis.model.Person;
import com.klinik.response.BaseResponseError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "persons", description = "persons")
    @ApiResponses(value = {
        @ApiResponse( responseCode = "200", description = "Успешно",        content = { @Content( array = @ArraySchema(schema = @Schema(implementation = Person.class ))) }),
        @ApiResponse( responseCode = "400", description = "Плохой запрос ", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
        @ApiResponse( responseCode = "500", description = "Ошибка сервера", content = { @Content( array = @ArraySchema(schema = @Schema( implementation =  BaseResponseError.class ))) })
    })
@RequestMapping("/persons")
public interface RestPerson {

    @GetMapping()
    @Operation( description = "Список всех чел", summary = "Список всех чел")
    public ResponseEntity<List<Person>> getAllPerson();

    @GetMapping("/{id}")
    @Operation( description = "Список всех чел", summary = "Список всех чел")
    public ResponseEntity<Person> findByIdPerson( String id );

    @PostMapping("/{person}")
    @Operation( description = "Список всех чел", summary = "Список всех чел")
    public ResponseEntity<Void> addPerson( Person person ); 

    
}
