package com.klinik.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table( name = "drug")
@Setter
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class Drug implements Serializable{

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id_dr")
    @Schema( name        = "idDrug",
             description = "ИД лекарства",
             example     = "100",
             required    = true )
    private Long idDrug;

    @Column( name = "name")
    @Schema( name        = "name",
             description = "Препараты",
             example     = "Карвалол 2 чайные ложки в день",
             required    = true )
    private String name;

    @Hidden
    @OneToOne( cascade = CascadeType.ALL )
    @JoinColumn( name = "drug_id", referencedColumnName = "id_drug" )
    private DrugTreatment drugTreatment ;
    
}
