package com.klinik.controller;

import com.klinik.entity.Document;
import com.klinik.service.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Documents", description = "Документ клиента")
public class DocumentController {

    @Autowired
    private DocumentService service;

    @GetMapping(value = "/Documents")
    @Operation( description = "Список всех документов", summary = "Список всех документов")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Found the documents", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = Document.class))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( ))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( ))) })
    })
    public List<Document> getAllDocuments() throws Exception{
        return service.getAllDocuments();
    }

    @Operation( description = "Добавить документ", summary = "Добавить документ")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Add document", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = Document.class))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( ))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( ))) })
    })
    @RequestMapping( method = RequestMethod.PUT , value = "/addDocument")
    public Document addDocument(Document document ) throws Exception{
        return service.addDocument( document );
    }


}
