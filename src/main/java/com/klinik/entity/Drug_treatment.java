package com.klinik.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table( name = "Drug_treatment")
@Getter
@Setter
public class Drug_treatment {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column( name = "id_drug")
    @Schema( name        = "id_drug",
            description = "ИД медикаментозного лечения",
            example     = "100",
            required    = true )

    private Long id_drug;

    @Column( name = "name")
    @Schema( name        = "name",
            description = "Наименование",
            example     = "Кортикостероиды",
            required    = true )
    private String name;

    
    @Override
    public String toString() {
        return new StringBuilder("      { \n")
                      .append("         Ид медикаментозного лечения: ").append(id_drug).append(",\n")  
                      .append("         Наименование: ").append(name).append("\n        }\n")
                      .toString();
    }
}
