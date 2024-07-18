package com.klinik.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.klinik.entity.Document;
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
import org.springframework.web.bind.annotation.RequestBody;

@RequestMapping( value = "documents")
@Tag(name = "3. Documents", description = "Документ пациента")
@ApiResponses(value = {
        @ApiResponse( responseCode = "200", description = "Успешно",        content = { @Content( array = @ArraySchema(schema = @Schema( implementation = Document.class))) }),
        @ApiResponse( responseCode = "400", description = "Плохой запрос",  content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
        @ApiResponse( responseCode = "500", description = "Ошибка сервера", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
public interface IDocument {

    @GetMapping(value = "/list")
    @Operation( description = "Список всех документов", summary = "Список всех документов")
    public ResponseEntity<List<Document>> getAllDocuments() throws Exception, MyException;

    @Operation( description = "Добавить документ", summary = "Добавить документ")
    @RequestMapping( method = RequestMethod.POST , value = "/add/{docum}")
    public ResponseEntity<Document> addDocument(@RequestBody Document document ) throws Exception, MyException;

    @Operation( description = "Найти документ", summary = "Найти документ")
    @RequestMapping( method = RequestMethod.GET , value = "/find/{word}")
    public ResponseEntity<List<Document>> findByWord( @Parameter( description = "Параметр поиска") String word ) ;

    @GetMapping(value = "/list/{page}{size}")
    @Operation( description = "Список документов", summary = "Список документов")
    public ResponseEntity<List<Document>> getLazyDocument( int page, int size);
    
}
