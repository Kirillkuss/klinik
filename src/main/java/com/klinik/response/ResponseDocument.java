package com.klinik.response;

import java.util.List;

import com.klinik.entity.Document;

import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonInclude;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDocument<T> {

    private List<Document> documents;
    private Document document;
    public ResponseDocument(){
    }
    public ResponseDocument(  List<Document> documents ){
        this.documents = documents;
    }
    public ResponseDocument(  Document document ){
        this.document = document;
    }
}
