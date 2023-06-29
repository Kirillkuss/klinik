package com.klinik.controller;

import com.klinik.entity.Document;
import com.klinik.excep.MyException;
import com.klinik.response.BaseResponse;
import com.klinik.rest.IDocument;
import com.klinik.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DocumentController implements IDocument{

    @ExceptionHandler(Throwable.class)
    public BaseResponse errBaseResponse( Throwable ex ){
        return BaseResponse.error( 999, ex );
    }

    @ExceptionHandler(MyException.class)
    public BaseResponse errBaseResponse( MyException ex ){
        return BaseResponse.error( ex.getCode(), ex );
    }

    @Autowired
    DocumentService documentService;
    public BaseResponse getAllDocuments() throws Exception, MyException{
        return new BaseResponse<>( 200, "success", documentService.getAllDocuments() );
    }

    public BaseResponse addDocument( Document document ) throws Exception, MyException{
        if ( documentService.findById( document.getId_document()) != null ) throw new MyException( 410, "Документ с таким ИД документа уже существует, используйте другой ИД");
        if ( documentService.findByNumar( document.getNumar()) != null )    throw new MyException( 411, "Документ с таким номером документа уже существует");
        if ( documentService.findByPolis( document.getPolis()) != null )    throw new MyException( 412, "Документ с таким полисом уже существует");
        if ( documentService.findBySnils( document.getSnils()) != null )    throw new MyException( 413, "Документ с таким СНИЛСом уже существует");
        return new BaseResponse<>( 200, "success", documentService.addDocument( document ));
    }

}
