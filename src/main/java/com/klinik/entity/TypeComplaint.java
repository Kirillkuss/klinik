package com.klinik.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
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

    @Column( name = "complaint_id")
    @Schema( name        = "complaint_id",
            description = "ИД жалобы",
            example     = "1",
            required    = true )
   @JoinColumn(name = "complaint_id", referencedColumnName = "id_complaint")
    private Long complaint_id;

    public TypeComplaint(){

    }

    public TypeComplaint( Long id_type_complaint, String name, Long complaint_id ){
        this.id_type_complaint = id_type_complaint;
        this.name = name;
        this.complaint_id = complaint_id;
    }

    @Override
    public String toString() {
        return new StringBuilder("              Поджалоба { \n")
                      .append("                 1. Ид поджалобы: ").append(id_type_complaint).append(",\n")
                      .append("                 2. Наименование: ").append(name).append(",\n")  
                      .append("                 3. Ид жалобы:").append(complaint_id).append("\n                   }\n")
                      .toString();
    }


    
}
