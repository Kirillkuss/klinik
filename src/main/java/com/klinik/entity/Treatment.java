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
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

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
    @Schema( name        = "idTreatment",
             description = "ИД лечения",
             example     = "100",
             required    = true )
    private Long idTreatment;


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column( name = "time_start_treatment")
    @Schema( name        = "timeStartTreatment",
             description = "Дата начала лечения",
             example     = "2023-01-22 18:00:00.745",
             required    = true )
    private LocalDateTime timeStartTreatment;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column( name = "end_time_treatment")
    @Schema( name        = "endTimeTreatment",
             description = "Дата окончания лечения",
             example     = "2023-07-22 18:00:00.745",
             required    = true )
    private LocalDateTime endTimeTreatment;

    @Hidden
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "drug_id", referencedColumnName = "id_dr")
    private  Drug drug;

    @Hidden
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rehabilitation_solution_id", referencedColumnName = "id_rehabilitation_solution")
    private RehabilitationSolution rehabilitationSolution;

    @Hidden
    @Column( name = "card_patient_id")
    @Schema( name        = "cardPatientId",
             description = "ИД карты пациента",
             example     = "1",
             required    = true )
    private Long cardPatientId;

    @Hidden
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_id", referencedColumnName = "id_doctor")
    private Doctor doctor;   
}
