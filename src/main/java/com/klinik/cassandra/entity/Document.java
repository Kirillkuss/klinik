package com.klinik.cassandra.entity;

import java.util.UUID;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Document {

    @Hidden
    @PrimaryKey
    @Schema( name = "id",description = "ИД", example = "3fa85f64-5717-4562-b3fc-2c963f66af32")
    private UUID id;
    @Schema( name = "typeDocument", description = "Тип документа", example = "passport", required  = true )
    private String typeDocument;
    @Schema( name = "seria", description = "серия", example = "RY", required  = true )
    private String seria;
    @Schema( name = "numar", description = "номер", example = "123243455", required  = true )
    private String numar;
    @Schema( name = "snils", description = "снилс", example = "123-456-789-33", required  = true )
    private String snils;
    @Schema( name = "polis", description = "полис", example = "1111 4567 3333 1264", required  = true )
    private String polis;

}