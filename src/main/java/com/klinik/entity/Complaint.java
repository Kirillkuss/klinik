package com.klinik.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonInclude;

//Сущность жалоба
@Entity
@Table( name = "complaint")
@Setter
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class Complaint implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column( name = "id_complaint")
    @Schema( name        = "idComplaint",
            description = "ИД жалобы",
            example     = "100",
            required    = true )
    @JsonInclude(Include.NON_NULL)        
    private Long idComplaint;

    @Column( name = "functional_impairment")
    @Schema( name        = "functionalImpairment",
            description = "Наименование жалобы",
            example     = "Симптомы поражения пирамидного тракта",
            required    = true )
    private String functionalImpairment;

}
