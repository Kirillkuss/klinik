package com.klinik.entity;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//Реабилитационное лечение

@Entity
@Table( name = "rehabilitation_solution")
@Setter
@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class RehabilitationSolution  implements Serializable {

    @Id
    @Hidden
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column( name = "id_rehabilitation_solution")
    @Schema( name        = "idRehabilitationSolution",
             description = "ИД реабилитационного лечения",
             example     = "-1",
             required    = true )
    private Long idRehabilitationSolution;

    @Column( name = "name")
    @Schema( name        = "name",
             description = "Наименование",
             example     = "Кинезитерапия1",
             required    = true )
    private String name;

    @Column( name = "survey_plan")
    @Schema( name        = "surveyPlan",
             description = "План обследования",
             example     = "План реабилитационного лечения",
             required    = true )
    private String surveyPlan;

}
