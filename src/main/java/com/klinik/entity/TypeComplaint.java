package com.klinik.entity;

import java.io.Serializable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table( name = "Type_complaint")
@Setter
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
@JsonInclude(Include.NON_NULL)
public class TypeComplaint implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column (name = "id_type_complaint")
    @Schema( name        = "idTypeComplaint",
             description = "ИД поджалобы",
             example     = "100",
             required    = true )
    private Long idTypeComplaint;

    @Column( name = "name")
    @Schema( name        = "name",
             description = "Наименование поджалобы",
             example     = "Парапарезы",
             required    = true )
    private String name;

   @Hidden 
   @OneToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "complaint_id", referencedColumnName = "id_complaint")
   @JsonInclude(Include.NON_NULL)
   private Complaint complaint;
}
