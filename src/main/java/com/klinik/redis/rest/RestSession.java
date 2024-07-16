package com.klinik.redis.rest;

import java.util.Iterator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.klinik.redis.model.Doctor;
import com.klinik.redis.model.Session;
import com.klinik.response.BaseResponseError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Session", description = "Сессии ( Redis )")
    @ApiResponses(value = {
        @ApiResponse( responseCode = "200", description = "Успешно",        content = { @Content( array = @ArraySchema(schema = @Schema(implementation = Session.class ))) }),
        @ApiResponse( responseCode = "400", description = "Плохой запрос ", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
        @ApiResponse( responseCode = "500", description = "Ошибка сервера", content = { @Content( array = @ArraySchema(schema = @Schema( implementation =  BaseResponseError.class ))) })
    })
@RequestMapping("/sessions")
public interface RestSession {

    @GetMapping()
    @Operation( description = "Список всех сессий", summary = "Список всех сессий")
    public ResponseEntity<Iterator<Session>> getAllStudents();

    @GetMapping("/{id}")
    @Operation( description = "Поиск по ИД", summary = "Поиск по ИД")
    public ResponseEntity<Session> getFindById(@RequestParam String id);

    @PostMapping("/add/{session}")
    @Operation( description = "Добавить сессию", summary = "Добавить сессию")
    public ResponseEntity<Session> addSession(@RequestBody Session session);

    @GetMapping("/doctors")
    public ResponseEntity<Iterable<Doctor>> getAllDoctors();
    
}
