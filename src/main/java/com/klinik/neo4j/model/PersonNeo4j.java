package com.klinik.neo4j.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import com.klinik.entity.Gender;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Node("Person")
public class PersonNeo4j {

    @Hidden
    @Id
    private UUID id;
 
    @Schema( name  = "surname", description = "Фамилия", example = "Пупкин", required = true )
    private String surname;

    @Schema( name = "name",description = "Имя", example = "Михаил", required = true )
    private String name;

    @Schema( name = "fullName", description = "Отчество", example = "Петрович", required = true )
    private String fullName;

    @Schema( name = "gender", description = "Пол пациента" )
    private Gender gender;

    @Column( name = "phone")
    @Schema( name  = "phone", description = "Номер телефона", example = "+78998956184", required = true )
    private String phone;

    @Schema( name = "address", description = "Адрес", example = "Спб, Проспект Тихорецкого д.5", required = true )
    private String address;

    @Relationship(type = "WRITTEN_BY", direction = Relationship.Direction.OUTGOING)
    private DocumentNeo4j documentNeo4j;
    
}
