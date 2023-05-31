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
            example     = "100",
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
            description = "Пол пациента",
            example     = "true",
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

    @Override
    public String toString() {
        return new StringBuilder(" Пациент { \n")
                      .append("   Ид пациента: ").append(id_patient).append(",\n")  
                      .append("   Фамилия: ").append(surname).append(",\n")
                      .append("   Имя: ").append(name).append(",\n")
                      .append("   Отчество: ").append(full_name).append(",\n")
                      .append("   Пол: ").append(gender == true ? "Муж" : "Жен").append(",\n")
                      .append("   Номер телефона: ").append(phone).append(",\n")
                      .append("   Адрес: ").append(address).append(",")
                      .append("  \t  ").append(document).append("}\n")
                      .toString();
    }


}
