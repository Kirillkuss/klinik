package com.klinik.response;

import java.util.List;

import com.klinik.entity.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonInclude;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDocument<T> {

    private List<Document> documents;
    private Document document;
}
