package com.klinik.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "document")
@Setter
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class Document  implements Serializable{

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column( name = "id_document")
    @Schema( name        = "idDocument",
             description = "ИД документа",
             example     = "100",
             required    = true )
    private Long idDocument;

    @Column( name = "type_document")
    @Schema( name        = "typeDocument",
             description = "Тип документа",
             example     = "Паспорт",
             required    = true )
    private String typeDocument;

    @Column( name = "seria")
    @Schema( name        = "seria",
             description = "Серия документа",
             example     = "ВМ",
             required    = true )
    private String seria;

    @Column( name = "numar")
    @Schema( name        = "numar",
             description = "Номер документа",
             example     = "123243455",
             required    = true )
    private String numar;

    @Column( name = "snils")
    @Schema( name        = "snils",
             description = "СНИЛС",
             example     = "123-456-789-01",
             required    = true )
    private String snils;

    @Column( name = "polis")
    @Schema( name        = "polis",
             description = "Полис",
             example     = "0000 0000 0000 0000",
             required    = true )
    private String polis;

}
