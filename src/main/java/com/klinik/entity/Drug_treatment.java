package com.klinik.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table( name = "Drug_treatment")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class Drug_treatment  implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column( name = "id_drug")
    @Schema( name        = "id_drug",
            description = "ИД медикаментозного лечения",
            example     = "100",
            required    = true )

    private Long id_drug;

    @Column( name = "name")
    @Schema( name        = "name",
            description = "Наименование",
            example     = "Кортикостероиды",
            required    = true )
    private String name;

}
