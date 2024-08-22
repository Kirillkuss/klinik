package com.klinik.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "doctor")
@Setter
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class Doctor  implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column( name = "id_doctor")
    @Schema( name        = "idDoctor",
            description = "ИД доктора",
            example     = "1",
            required    = true )
    @JsonInclude(Include.NON_NULL)
    private Long idDoctor;

    @Column( name = "surname")
    @Schema( name        = "surname",
            description = "Фамилия",
            example     = "Петров",
            required    = true )
    private String surname;

    @Column( name = "name")
    @Schema( name        = "name",
            description = "Имя",
            example     = "Петр",
            required    = true )
    private String name;

    @Column( name = "full_name")
    @Schema( name        = "fullName",
            description = "Отчество",
            example     = "Петрович",
            required    = true )
    private String fullName;

}
