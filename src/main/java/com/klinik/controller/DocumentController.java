package com.klinik.controller;

import com.klinik.entity.Document;
import com.klinik.excep.MyException;
import com.klinik.response.BaseResponseError;
import com.klinik.response.ResponseDocument;
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
import java.util.ArrayList;
import java.util.List;

@RestController
@Tag(name = "Documents", description = "Документ пациента")
public class DocumentController {

    @Autowired
    private DocumentService service;

    @GetMapping(value = "/Documents")
    @Operation( description = "Список всех документов", summary = "Список всех документов")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Found the documents", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = ResponseDocument.class))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BaseResponseError.class ))) })
    })
    public ResponseDocument getAllDocuments() throws Exception, MyException{
        ResponseDocument response = new ResponseDocument(200, "success");
        try{
            response.setDocument( service.getAllDocuments() );
            return response;
        }catch( MyException ex ){
            return ResponseDocument.error( ex.getCode(), ex);
        }
    }

    @Operation( description = "Добавить документ", summary = "Добавить документ")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Add document", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = ResponseDocument.class))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
    @RequestMapping( method = RequestMethod.POST , value = "/addDocument")
    public ResponseDocument addDocument( Document document ) throws Exception, MyException{
        ResponseDocument response = new ResponseDocument(200, "success");
        try{
            if ( service.findById( document.getId_document()) != null ) throw new MyException( 410, "Карта с таким ИД документа уже существует, используйте другой ИД");
            List<Document> list = new ArrayList<>();
            list.add(0, service.addDocument( document ));
            response.setDocument(list);
            return response;
        }catch( MyException ex ){
            return ResponseDocument.error( ex.getCode(), ex);
        }  
    }


}
