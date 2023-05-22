package com.klinik.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "doctor")
@Setter
@Getter
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column( name = "id_doctor")
    @Schema( name        = "id_doctor",
            description = "ИД пациента",
            example     = "1",
            required    = true )
    private Long id_doctor;

    @Column( name = "fio")
    @Schema( name        = "fio",
            description = "ФИО",
            example     = "Петров Петр Петрович",
            required    = true )
    private String fio;
}
