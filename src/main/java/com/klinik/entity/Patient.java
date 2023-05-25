package com.klinik.entity;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.klinik.entity.Document;
import javax.persistence.*;

@Entity
@Table( name = "patient")
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Patient {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column( name = "id_patient")
    @Schema( name        = "id_patient",
            description = "ИД пациента",
            example     = "1",
            required    = true )
    
    private Long id_patient;

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
    @Schema( name        = "full_name",
            description = "Отчество",
            example     = "Петрович",
            required    = true )
    private String full_name;

    @Column( name = "gender")
    @Schema( name        = "gender",
            description = "true",
            example     = "1",
            required    = true )
    private Boolean gender;

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

    /**@Hidden
    @Column( name = "document_id")
    @Schema( name        = "document_id",
            description = "ИД документа",
            example     = "1",
            required    = true )
    private Long document_id;*/

    @Hidden
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "document_id", referencedColumnName = "id_document")
    private Document document;
    
    public Patient(){
    }

        
    public Patient(Long id_patient,  String surname, String name, String full_name, Boolean gender ,String phone, String address, Document document ){
        this.id_patient = id_patient;
        this.surname = surname;
        this.name = name;
        this.full_name = full_name;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.document = document;
    }

    public Patient(  String surname, String name, String full_name, Boolean gender ,String phone, String address ){
        this.surname = surname;
        this.name = name;
        this.full_name = full_name;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
    }


}
