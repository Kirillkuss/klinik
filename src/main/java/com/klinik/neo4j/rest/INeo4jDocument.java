package com.klinik.neo4j.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.klinik.neo4j.model.DocumentNeo4j;
import com.klinik.neo4j.model.PersonNeo4j;
import com.klinik.response.BaseResponseError;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.*;

@RequestMapping( value = "neo4j")
@Tag(name = "w document", description = "документы в Neo4j")
    @ApiResponses(value = {
        @ApiResponse( responseCode = "200", description = "Успешно",        content = { @Content( mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema( implementation = DocumentNeo4j.class))) }),
        @ApiResponse( responseCode = "400", description = "Плохой запрос ", content = { @Content( mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
        @ApiResponse( responseCode = "500", description = "Ошибка сервера", content = { @Content( mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
public interface INeo4jDocument {
    
    @GetMapping(value = "")
    @Operation( description = "Получение списка док", summary = "Получение списка док")
    public ResponseEntity<List<DocumentNeo4j>> getAll();

    @GetMapping(value = "/{id}")
    @Operation( description = "Поиск по ид", summary = "Поиск по ид")
    public ResponseEntity<DocumentNeo4j> findById( @PathVariable @Parameter( example = "3fa85f64-5717-4562-b3fc-2c963f66af32") String id );

    @PostMapping("")
    @Operation( description = "Добавить документ", summary = "Добавить документ")
    public ResponseEntity<DocumentNeo4j> addDocument(@RequestBody DocumentNeo4j document );

    @GetMapping(value = "/person")
    @Operation( description = "Получение списка пациентов", summary = "Получение списка пациентов")
    public ResponseEntity<List<PersonNeo4j>> getAllPerson();

    @PostMapping("/person")
    @Operation( description = "Добавить чел", summary = "Добавить чел")
    public ResponseEntity<PersonNeo4j> addPerson( @RequestBody PersonNeo4j person) ;

}
