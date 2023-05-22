package com.klinik.entity;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;

//Сущночть лечение

@Entity
@Table
@Setter
@Getter
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
public class Treatment {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column( name = "id_treatment")
    @Schema( name        = "id_treatment",
            description = "ИД лечения",
            example     = "1",
            required    = true )
    private Long id_treatment;

    @Column( name = "medical_treatment")
    @Schema( name        = "medical_treatment",
            description = "Медикационное лечение",
            example     = "Таблетки",
            required    = true )
    private String medical_treatment;

    @Column( name = "method_of_treatment")
    @Schema( name        = "method_of_treatment",
            description = "Способ лечения",
            example     = "Способ лечения",
            required    = true )
    private String method_of_treatment;

    @Hidden
    @Column( name = "card_patient_id")
    @Schema( name        = "card_patient_id",
            description = "ИД карты пациента",
            example     = "1",
            required    = true )
    private Long card_patient_id;

    @Hidden
    @Column( name = "rehabilitation_solution_id")
    @Schema( name        = "rehabilitation_solution_id",
            description = "ИД реабилитационного лечения",
            example     = "1",
            required    = true )
    private Long rehabilitation_solution_id;

}
