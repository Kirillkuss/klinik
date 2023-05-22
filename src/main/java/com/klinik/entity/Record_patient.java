package com.klinik.entity;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Setter
@Getter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class Record_patient {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column( name = "id_record")
    @Schema( name        = "id_record",
            description = "ИД записи пациента",
            example     = "1",
            required    = true )
    private Long id_record;

    @Column( name = "date_record")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Schema( name        = "date_record",
            description = "Дата и время записи",
            example     = "2023-05-19T12:47:07.605Z",
            required    = true )
    private LocalDateTime date_record;

    @Column( name = "date_appointment")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Schema( name        = "date_appointment",
            description = "Дата и время приема",
            example     = "2023-05-19T12:47:07.605Z",
            required    = true )
    private LocalDateTime date_appointment;

    @Column( name = "number_room")
    @Schema( name        = "number_room",
            description = "Номер кабинета",
            example     = "1102",
            required    = true )
    private Long number_room;

    @Hidden
    @Column( name = "doctor_id")
    @Schema( name        = "doctor_id",
            description = "ИД доктора",
            example     = "1",
            required    = true )
    private Long doctor_id;

    @Hidden
    @Column( name = "card_patient_id")
    @Schema( name        = "card_patient_id",
            description = "ИД карты",
            example     = "1",
            required    = true )
    private Long card_patient_id;

}
