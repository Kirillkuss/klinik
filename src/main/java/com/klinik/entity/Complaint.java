package com.klinik.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.io.Serializable;
import javax.persistence.*;
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

    public Complaint( Long idComplaint, String functionalImpairment ){
        this.idComplaint = idComplaint;
        this.functionalImpairment = functionalImpairment;
    }

    public Complaint( String functionalImpairment ){
        this.functionalImpairment = functionalImpairment;  
    }

}
