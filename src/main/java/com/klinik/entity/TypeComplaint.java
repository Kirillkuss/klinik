package com.klinik.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table( name = "Type_complaint")
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class TypeComplaint {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column (name = "id_type_complaint")
    @Schema( name        = "id_type_complaint",
            description = "ИД поджалобы",
            example     = "1",
            required    = true )
    private Long id_type_complaint;

    @Column( name = "name")
    @Schema( name        = "name",
            description = "Наименование поджалобы",
            example     = "Парапарезы",
            required    = true )
    private String name;


   @Hidden 
   @OneToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "complaint_id", referencedColumnName = "id_complaint")
   private Сomplaint complaint;

    public TypeComplaint(){

    }

    public TypeComplaint( Long id_type_complaint, String name, Сomplaint complaint ){
        this.id_type_complaint = id_type_complaint;
        this.name = name;
        this.complaint = complaint;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                      .append(complaint)
                      .append("    Ид поджалобы: ").append(id_type_complaint).append(",\n")
                      .append("    Поджалоба: ").append(name).append("\n") 
                      .toString();
    }


    
}