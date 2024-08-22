package com.klinik.entity;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table
@Setter
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
@JsonInclude(Include.NON_NULL)
public class RecordPatient implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column( name = "id_record")
    @Schema( name        = "idRecord",
             description = "ИД записи пациента",
             example     = "100",
             required    = true )
    private Long idRecord;

    @Column( name = "date_record")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Schema( name        = "dateRecord",
             description = "Дата и время записи",
             example     = "2023-01-19T12:00:00.000Z",
             required    = true )
    private LocalDateTime dateRecord;

    @Column( name = "date_appointment")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Schema( name        = "dateAppointment",
             description = "Дата и время приема",
             example     = "2023-02-01T14:00:00.605Z",
             required    = true )
    private LocalDateTime dateAppointment;

    @Column( name = "number_room")
    @Schema( name        = "numberRoom",
             description = "Номер кабинета",
             example     = "203",
             required    = true )
    private Long numberRoom;

    @Hidden
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_id", referencedColumnName = "id_doctor")
    private Doctor doctor;

    @Hidden
    @Column( name = "card_patient_id")
    @Schema( name        = "cardPatientId",
             description = "ИД карты",
             example     = "1",
             required    = true )
    private Long cardPatientId;

}
