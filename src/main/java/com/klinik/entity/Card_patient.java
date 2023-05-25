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

    @Hidden
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "complaint_id", referencedColumnName = "id_complaint")
    private Сomplaint complaint;

    @Hidden
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pacient_id", referencedColumnName = "id_patient")
    private Patient patient;

    public Card_patient(){

    }

    public Card_patient(Long id_card_patient, String diagnosis, Boolean allergy,  String note, String сonclusion , Сomplaint complaint, Patient patient){
        this.id_card_patient = id_card_patient;
        this.diagnosis = diagnosis;
        this.allergy = allergy;
        this.note = note;
        this.сonclusion = сonclusion;
        this.complaint = complaint;
        this.patient = patient;
    }

    public Card_patient(String diagnosis, Boolean allergy,  String note, String сonclusion , Сomplaint complaint, Patient patient){
        this.diagnosis = diagnosis;
        this.allergy = allergy;
        this.note = note;
        this.сonclusion = сonclusion;
        this.complaint = complaint;
        this.patient = patient;
    }

    


}
