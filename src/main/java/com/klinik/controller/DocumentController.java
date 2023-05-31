package com.klinik.controller;

import com.klinik.entity.Document;
import com.klinik.excep.MyException;
import com.klinik.response.BaseResponse;
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

@RequestMapping( value = "Documents")
@RestController
@Tag(name = "3. Documents", description = "Документ пациента")
public class DocumentController {

    @Autowired
    private DocumentService service;

    @GetMapping(value = "/getAllDocunets")
    @Operation( description = "Список всех документов", summary = "Список всех документов")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Получение списка документов", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) })
    })
    public String getAllDocuments() throws Exception, MyException{
        BaseResponse response = new BaseResponse(200, "успешно");
        try{
            response.setResponse( service.getAllDocuments() );
            return response.toString();
        }catch( Exception ex ){
            return BaseResponse.error( 999, ex ).toString();
        }
    }

    @Operation( description = "Добавить документ", summary = "Добавить документ")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Документ добавлен", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = ResponseDocument.class))) })
    })
    @RequestMapping( method = RequestMethod.POST , value = "/addDocument")
    public String addDocument( Document document ) throws Exception, MyException{
        BaseResponse response = new BaseResponse(200, "успешно");
        try{
            if ( service.findById( document.getId_document()) != null ) throw new MyException( 410, "Документ с таким ИД документа уже существует, используйте другой ИД");
            if ( service.findByNumar( document.getNumar()) != null ) throw new MyException( 411, "Документ с таким номером документа уже существует");
            if ( service.findByPolis( document.getPolis()) != null ) throw new MyException( 412, "Документ с таким полисом уже существует");
            if ( service.findBySnils( document.getSnils()) != null ) throw new MyException( 413, "Документ с таким СНИЛСом уже существует");
            response.setResponse(service.addDocument( document ));
            return response.toString();
        }catch( MyException ex ){
            return BaseResponse.error( ex.getCode(), ex ).toString(); 
        }  
    }


}
