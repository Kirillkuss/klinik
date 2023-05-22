package com.klinik.response;

import java.util.List;

import com.klinik.entity.Document;

import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonInclude;

@Setter
@Getter
public class ResponseDocument<T> extends BaseResponse{

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Document> document;


    public ResponseDocument(){
    }

    public ResponseDocument( Integer code, String message){
        super( code, message);
    }

    public ResponseDocument( Integer code, String message, List<Document> document ){
        super( code, message);
        this.document = document;
    }

    public static ResponseDocument error( int code, Throwable e ){
        return new ResponseDocument( code , null == e.getMessage() ? "System malfunction" : e.getMessage());
    }

}
