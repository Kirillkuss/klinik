package com.klinik.entity;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
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
