package com.klinik.redis.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.klinik.redis.model.Student;
import com.klinik.response.BaseResponseError;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Student", description = "Student")
    @ApiResponses(value = {
        @ApiResponse( responseCode = "200", description = "Успешно",        content = { @Content( array = @ArraySchema(schema = @Schema(implementation = Student.class ))) }),
        @ApiResponse( responseCode = "400", description = "Плохой запрос ", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
        @ApiResponse( responseCode = "500", description = "Ошибка сервера", content = { @Content( array = @ArraySchema(schema = @Schema( implementation =  BaseResponseError.class ))) })
    })
@RequestMapping("/students")
public interface RestStudent {

    @GetMapping()
    @Operation( description = "Список всех студентов", summary = "Список всех студентов")
    public ResponseEntity<Iterable<Student>> getAllStudents();

    @GetMapping("/{id}")
    @Operation( description = "Поиск по ИД", summary = "Поиск по ИД")
    public ResponseEntity<Student> getMethodName(@RequestParam String id);

    @PostMapping("/add/{student}")
    @Operation( description = "Добавить студента", summary = "Добавить студента")
    public ResponseEntity<Student> postMethodName(@RequestBody Student student);
    
}
