package com.klinik.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.persistence.*;

//Реабилитационное лечение

@Entity
@Table( name = "rehabilitation_solution")
@Setter
@Getter

@EqualsAndHashCode
@RequiredArgsConstructor
public class Rehabilitation_solution {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column( name = "id_rehabilitation_solution")
    @Schema( name        = "id_rehabilitation_solution",
            description = "ИД реабилитационного лечения",
            example     = "100",
            required    = true )
    private Long id_rehabilitation_solution;

    @Column( name = "name")
    @Schema( name        = "name",
            description = "Наименование",
            example     = "Кинезитерапия1",
            required    = true )
    private String name;

    @Column( name = "survey_plan")
    @Schema( name        = "survey_plan",
            description = "План обследования",
            example     = "План реабилитационного лечения",
            required    = true )
    private String survey_plan;

    @Override
    public String toString() {
        return new StringBuilder(" { \n")
                      .append("         ИД реабилитационного лечения: ").append(id_rehabilitation_solution).append(",\n")
                      .append("         Наименование: ").append(name).append(",\n")   
                      .append("         План обследования: ").append(survey_plan).append("\n     }\n")
                      .toString();
    }
}
