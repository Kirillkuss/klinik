package com.klinik.cassandra.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import com.klinik.cassandra.entity.Document;
import com.klinik.response.BaseResponseError;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequestMapping( value = "cassandra/documents")
@Tag(name = "w cassandra", description = "Документы в БД ( cassandra )")
    @ApiResponses(value = {
        @ApiResponse( responseCode = "200", description = "Успешно",        content = { @Content( array = @ArraySchema(schema = @Schema( implementation = Document.class ))) }),
        @ApiResponse( responseCode = "400", description = "Плохой запрос ", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
        @ApiResponse( responseCode = "500", description = "Ошибка сервера", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
public interface RestControllerDocumentCassandra {

    @GetMapping("")
    @Operation( description = "Список всех документов", summary = "Список всех документов")
    public ResponseEntity<List<Document>> allDocument();

    @PostMapping("/{document}")
    @Operation( description = "Сохранить документ", summary = "Сохранить документ")
    public ResponseEntity<Document> saveDocument( @RequestBody Document document );

    @GetMapping("/{id}")
    @Operation( description = "Поиск по ИД документа", summary = "Поиск по ИД документа")
    public ResponseEntity<Document> findById( @PathVariable @Parameter( example = "3fa85f64-5717-4562-b3fc-2c963f66af32") UUID id );

    @SuppressWarnings("rawtypes")
    @DeleteMapping( "/{id}")
    @Operation( description = "Удалить документ", summary = "Удалить документ")
    public ResponseEntity deleteDocument( @PathVariable @Parameter( example = "3fa85f64-5717-4562-b3fc-2c963f66af32") UUID id  );
    
}
