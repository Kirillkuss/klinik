package com.klinik.entity;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
            description = "ИД карты пациента",
            example     = "100",
            required    = true )
    private Long id_card_patient;

    @Column( name = "diagnosis")
    @Schema( name        = "diagnosis",
            description = "Диагноз пациента",
            example     = "Рассеянный склероз",
            required    = true )
    private String diagnosis;

    @Column( name = "allergy")
    @Schema( name        = "allergy",
            description = "Аллергия на препараты",
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
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name                = "Card_patient_Complaint",
            joinColumns         = @JoinColumn(name = "card_patient_id", referencedColumnName = "id_card_patient"),
            inverseJoinColumns  = @JoinColumn(name = "complaint_id", referencedColumnName = "id_complaint")
    )
    @JsonInclude(Include.NON_NULL)
    private List<Сomplaint> complaint;

    @Hidden
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pacient_id", referencedColumnName = "id_patient")
    private Patient patient;

    public Card_patient(){

    }

    public Card_patient(Long id_card_patient, String diagnosis, Boolean allergy,  String note, String сonclusion , List<Сomplaint> complaint, Patient patient){
        this.id_card_patient = id_card_patient;
        this.diagnosis = diagnosis;
        this.allergy = allergy;
        this.note = note;
        this.сonclusion = сonclusion;
        this.complaint = complaint;
        this.patient = patient;
    }

    public Card_patient(String diagnosis, Boolean allergy,  String note, String сonclusion , List<Сomplaint> complaint, Patient patient){
        this.diagnosis = diagnosis;
        this.allergy = allergy;
        this.note = note;
        this.сonclusion = сonclusion;
        this.complaint = complaint;
        this.patient = patient;
    }

    


}
