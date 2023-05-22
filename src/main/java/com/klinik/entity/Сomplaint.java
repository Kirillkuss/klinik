package com.klinik.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;

//Сущность жалоба
@Entity
@Table( name = "complaint")
@Setter
@Getter
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
public class Сomplaint {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column( name = "id_complaint")
    @Schema( name        = "id_complaint",
            description = "ИД жалобы",
            example     = "1",
            required    = true )
    private Long id_complaint;

    @Column( name = "functional_impairment")
    @Schema( name        = "functional_impairment",
            description = "Функциональные нарушения",
            example     = "симптомы поражения пирамидного тракта",
            required    = true )
    private String functional_impairment;
}
