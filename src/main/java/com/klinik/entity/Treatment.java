package com.klinik.entity;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

//Сущночть лечение

@Entity
@Table( name = "Treatment")
@Setter
@Getter
@EqualsAndHashCode
@ToString
@Data
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

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column( name = "time_start_treatment")
    @Schema( name        = "time_start_treatment",
            description = "Дата начала лечения",
            example     = "2023-05-22 18:58:47.745",
            required    = true )
    private LocalDateTime time_start_treatment;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column( name = "end_time_treatment")
    @Schema( name        = "end_time_treatment",
            description = "Дата окончания лечения",
            example     = "2023-07-22 18:58:47.745",
            required    = true )
    private LocalDateTime end_time_treatment;

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
