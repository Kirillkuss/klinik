package com.klinik.neo4j.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Node("Document")
public class DocumentNeo4j {
    
    @Hidden
    @Id
    private String id;

    @Schema( name = "typeDocument", description = "Тип документа", example = "passport", required  = true )
    @Property("typeDocument")
    private String typeDocument;

    @Schema( name = "seria", description = "серия", example = "RY", required  = true )
    @Property("seria")
    private String seria;

    @Schema( name = "numar", description = "номер", example = "123243455", required  = true )
    @Property("numar")
    private String numar;

    @Schema( name = "snils", description = "снилс", example = "123-456-789-33", required  = true )
    @Property("snils")
    private String snils;

    @Schema( name = "polis", description = "полис", example = "1111 4567 3333 1264", required  = true )
    @Property("polis")
    private String polis;

}
