package com.klinik.entity;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table( name = "patient")
@Setter
@Getter
@ToString
@EqualsAndHashCode
@JsonInclude( Include.NON_NULL )
public class Patient implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column( name = "id_patient")
    @Schema( name        = "idPatient",
            description = "ИД пациента",
            example     = "100",
            required    = true )
    private Long idPatient;

    @Column( name = "surname")
    @Schema( name        = "surname",
            description = "Фамилия",
            example     = "Пупкин",
            required    = true )
    private String surname;

    @Column( name = "name")
    @Schema( name        = "name",
            description = "Имя",
            example     = "Михаил",
            required    = true )
    private String name;

    @Column( name = "full_name")
    @Schema( name        = "fullName",
            description = "Отчество",
            example     = "Петрович",
            required    = true )
    private String fullName;

    @Column( name = "gender")
    @Schema( name        = "gender",
            description = "Пол пациента" )
    private Gender gender;

    @Column( name = "phone")
    @Schema( name        = "phone",
            description = "Номер телефона",
            example     = "+78998956184",
            required    = true )
    private String phone;

    @Column( name = "address")
    @Schema( name        = "address",
            description = "Адрес",
            example     = "Спб, Проспект Тихорецкого д.5",
            required    = true )
    private String address;

    @Hidden
    @OneToOne( cascade = CascadeType.ALL )
    @JoinColumn( name = "document_id", referencedColumnName = "id_document" ) 
    private Document document;
}
