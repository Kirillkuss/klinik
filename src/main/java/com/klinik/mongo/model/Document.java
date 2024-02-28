package com.klinik.mongo.model;

import org.springframework.data.annotation.Id;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@org.springframework.data.mongodb.core.mapping.Document( collection = "document")
public class Document {

    @Id
    @Hidden
    private String id;
    private String typeDocument;
    private String seria;
    private String numar;
    private String polis;
    private String snils;
    
    
}
