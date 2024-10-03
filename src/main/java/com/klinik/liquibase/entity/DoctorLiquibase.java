package com.klinik.liquibase.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table( name = "doctor_liquibase", schema = "klinik_schema")
@ToString
@Getter
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor
public class DoctorLiquibase {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column( name = "id_doctor")
    @Schema( name        = "id",
            description = "ИД доктора",
            example     = "1",
            required    = true )
    @JsonInclude(Include.NON_NULL)
    private Long id;

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

    @OneToOne( cascade = CascadeType.ALL )
    @JoinColumn( name = "department_id", referencedColumnName = "id_department" ) 
    private DepartmentLiquibase departmentLiquibase;
    
}
