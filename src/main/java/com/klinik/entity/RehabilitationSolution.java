package com.klinik.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//Реабилитационное лечение

@Entity
@Table( name = "rehabilitation_solution")
@Setter
@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class RehabilitationSolution  implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column( name = "id_rehabilitation_solution")
    @Schema( name        = "idRehabilitationSolution",
             description = "ИД реабилитационного лечения",
             example     = "100",
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
