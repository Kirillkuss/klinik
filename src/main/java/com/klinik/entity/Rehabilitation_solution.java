package com.klinik.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.persistence.*;

//Реабилитационное лечение

@Entity
@Table( name = "rehabilitation_solution")
@Setter
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class Rehabilitation_solution {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column( name = "id_rehabilitation_solution")
    @Schema( name        = "id_rehabilitation_solution",
            description = "ИД жалобы",
            example     = "1",
            required    = true )
    private Long id_rehabilitation_solution;

    @Column( name = "name")
    @Schema( name        = "name",
            description = "Наименование",
            example     = "Интерфероны",
            required    = true )
    private String name;

    @Column( name = "survey_plan")
    @Schema( name        = "survey_plan",
            description = "План обследования",
            example     = "интерферон бета1b (бета-ферон) ",
            required    = true )
    private String survey_plan;
}
