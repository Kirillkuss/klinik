package com.klinik.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table( name = "drug")
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class Drug {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column( name = "id_dr")
    @Schema( name        = "id_dr",
            description = "ИД лекарства",
            example     = "100",
            required    = true )
    private Long id_dr;

    @Column( name = "name")
    @Schema( name        = "name",
            description = "Препараты",
            example     = "Карвалол 2 чайные ложки в день",
            required    = true )
    private String name;

    @Column( name = "drug_id")
    @Schema( name        = "drug_id",
            description = "ИД мед. лечения",
            example     = "1",
            required    = true )
    @JoinColumn(name = "drug_id", referencedColumnName = "id_drug")
    private Long drug_id;

    public Drug(){

    }

    public Drug( Long id_dr, String name, Long drug_id){
        this.id_dr = id_dr;
        this.name = name;
        this.drug_id = drug_id;
    }

    @Override
    public String toString() {
        return new StringBuilder("              Препарат { \n")
                      .append("                 1. Ид препарата: ").append(id_dr).append(",\n")
                      .append("                 2. Наименование: ").append(name).append(",\n")  
                      .append("                 3. Мед. лечения:").append(drug_id).append("\n                   }\n")
                      .toString();
    }
    
}
