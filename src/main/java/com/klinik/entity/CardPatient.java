package com.klinik.entity;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table( name = "card_patient")
@Setter
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
@JsonInclude(Include.NON_NULL)
public class CardPatient  implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column( name = "id_card_patient")
    @Schema( name        = "idCardPatient",
            description = "ИД карты пациента",
            example     = "100",
            required    = true )
    private Long idCardPatient;

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
            example     = "Есть аллергия на цитрамон" )
    private String note;

    @Column( name = "сonclusion")
    @Schema( name        = "сonclusion",
            description = "Заключение",
            example     = "Болен")
    private String сonclusion;

    @Hidden
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name                = "Card_patient_Complaint",
            joinColumns         = @JoinColumn(name = "card_patient_id", referencedColumnName = "id_card_patient"),
            inverseJoinColumns  = @JoinColumn(name = "type_complaint_id", referencedColumnName = "id_type_complaint")
    )
    private List<TypeComplaint> typeComplaint = new ArrayList<>();

    @Hidden
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pacient_id", referencedColumnName = "id_patient")
    private Patient patient;

}
