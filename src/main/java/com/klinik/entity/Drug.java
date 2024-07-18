package com.klinik.entity;

import java.io.Serializable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
