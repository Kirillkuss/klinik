package com.klinik.liquibase.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table( name = "department_liquibase", schema = "klinik_schema")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class DepartmentLiquibase {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column( name = "id_department")
    @Schema( name        = "id",
             description = "ИД департмента",
             example     = "100",
             required    = true )
    @JsonInclude(Include.NON_NULL)
    private Long id;

    @Column( name = "name")
    @Schema( name        = "name",
             description = "Наименование",
             example     = "First department",
             required    = true )
    private String name;
    
}
