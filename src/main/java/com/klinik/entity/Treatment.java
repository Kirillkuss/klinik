package com.klinik.entity;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

//Сущночть лечение

@Entity
@Table( name = "Treatment")
@Setter
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class Treatment implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column( name = "id_treatment")
    @Schema( name        = "id_treatment",
             description = "ИД лечения",
             example     = "100",
             required    = true )
    private Long id_treatment;


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column( name = "time_start_treatment")
    @Schema( name        = "time_start_treatment",
             description = "Дата начала лечения",
             example     = "2023-01-22 18:00:00.745",
             required    = true )
    private LocalDateTime time_start_treatment;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column( name = "end_time_treatment")
    @Schema( name        = "end_time_treatment",
             description = "Дата окончания лечения",
             example     = "2023-07-22 18:00:00.745",
             required    = true )
    private LocalDateTime end_time_treatment;

    @Hidden
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "drug_id", referencedColumnName = "id_dr")
    private  Drug drug;

    @Hidden
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rehabilitation_solution_id", referencedColumnName = "id_rehabilitation_solution")
    private RehabilitationSolution rehabilitation_solution;

    @Hidden
    @Column( name = "card_patient_id")
    @Schema( name        = "card_patient_id",
             description = "ИД карты пациента",
             example     = "1",
             required    = true )
    private Long card_patient_id;

    @Hidden
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_id", referencedColumnName = "id_doctor")
    private Doctor doctor;   
}
