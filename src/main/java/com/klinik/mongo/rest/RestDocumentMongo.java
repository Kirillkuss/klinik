package com.klinik.mongo.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.klinik.mongo.model.Document;
import com.klinik.response.BaseResponseError;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping( value = "mongo/documents")
@Tag(name = "w mongo", description = "Документы в БД ( mongo )")
    @ApiResponses(value = {
        @ApiResponse( responseCode = "200", description = "Успешно",        content = { @Content( array = @ArraySchema(schema = @Schema( implementation = Document.class ))) }),
        @ApiResponse( responseCode = "400", description = "Плохой запрос ", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
        @ApiResponse( responseCode = "500", description = "Ошибка сервера", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
public interface RestDocumentMongo {

    @GetMapping
    @Operation( description = "Список всех документов", summary = "Список всех документов")
    public ResponseEntity<List<Document>> getDocuments();

    @GetMapping("/{id}")
    @Operation( description = "Поиск по ид", summary = "Поиск по ид")
    public ResponseEntity<Document> getDocument( @PathVariable @Parameter( example = "3fa85f64-5717-4562-b3fc-2c963f66af32") String id );
    
    @PostMapping("/{document}")
    @Operation( description = "Добавить документ", summary = "Добавить документ")
    public ResponseEntity<Document> saveDocument( @RequestBody Document document );
}
