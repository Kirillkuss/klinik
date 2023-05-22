package com.klinik.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table( name = "card_patient")
@Setter
@Getter
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
public class Card_patient {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column( name = "id_card_patient")
    @Schema( name        = "id_card_patient",
            description = "ИД питомца",
            example     = "1",
            required    = true )
    private Long id_card_patient;

    @Column( name = "diagnosis")
    @Schema( name        = "diagnosis",
            description = "ИД питомца",
            example     = "1",
            required    = true )
    private String diagnosis;

    @Column( name = "allergy")
    @Schema( name        = "allergy",
            description = "ИД питомца",
            example     = "1",
            required    = true )
    private Boolean allergy;

  /**   @Column( name = "time_prm_patient")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Schema( name        = "time_prm_patient",
            description = "2023-05-19T12:47:07.605Z",
            example     = "1",
            required    = true )
    private LocalDateTime time_prm_patient;

    @Column( name = "date_next_prm")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Schema( name        = "date_next_prm",
            description = "2023-05-19T12:47:07.605Z",
            example     = "1",
            required    = true )
    private LocalDateTime date_next_prm;*/

    @Column( name = "note")
    @Schema( name        = "note",
            description = "Примечание",
            example     = "Есть аллергия на цитрамон",
            required    = true )
    private String note;

    @Column( name = "сonclusion")
    @Schema( name        = "сonclusion",
            description = "Заключение",
            example     = "Болен",
            required    = true )
    private String сonclusion;
}
