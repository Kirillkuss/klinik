package com.klinik.entity;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.persistence.*;

@Entity
@Table( name = "card_patient")
@Setter
@Getter
@EqualsAndHashCode
@ToString
@Data
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
            example     = "true",
            required    = true )
    private Boolean allergy;

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

    /**@Hidden
    @Column( name = "complaint_id")
    @Schema( name        = "complaint_id",
            description = "ИД жалобы",
            example     = "1",
            required    = true )
    private Long complaint_id;
    @Hidden
    @Column( name = "pacient_id")
    @Schema( name        = "pacient_id",
            description = "ИД_пациента",
            example     = "1",
            required    = true )
    private Long pacient_id;*/

    @Hidden
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "complaint_id", referencedColumnName = "id_complaint")
    private Сomplaint complaint;

    @Hidden
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pacient_id", referencedColumnName = "id_patient")
    private Patient patient;


}
