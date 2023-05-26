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


@RequestMapping( value = "Documents")
@RestController
@Tag(name = "Documents", description = "Документ пациента")
public class DocumentController {

    @Autowired
    private DocumentService service;

    @GetMapping(value = "/getAllDocunets")
    @Operation( description = "Список всех документов", summary = "Список всех документов")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Found the documents", content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = ResponseDocument.class))) }),
            @ApiResponse( responseCode = "400", description = "Bad request",       content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
            @ApiResponse( responseCode = "500", description = "System malfunction",content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BaseResponseError.class ))) })
    })
    public ResponseDocument getAllDocuments() throws Exception, MyException{
        ResponseDocument response = new ResponseDocument(200, "success");
        try{
            response.setDocuments( service.getAllDocuments() );
            return response;
        }catch( Exception ex ){
            return ResponseDocument.error( 999, ex);
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
            if ( service.findById( document.getId_document()) != null ) throw new MyException( 410, "Документ с таким ИД документа уже существует, используйте другой ИД");
            if ( service.findByNumar( document.getNumar()) != null ) throw new MyException( 411, "Документ с таким номером документа уже существует");
            if ( service.findByPolis( document.getPolis()) != null ) throw new MyException( 412, "Документ с таким полисом уже существует");
            if ( service.findBySnils( document.getSeria()) != null ) throw new MyException( 413, "Документ с таким СНИЛСом уже существует");
            response.setDocument(service.addDocument( document ));
            return response;
        }catch( Exception ex ){
            return ResponseDocument.error( 999, ex);
        }  
    }


}
