package com.klinik.response.report.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;

@Getter
@Setter
@ToString
public class Сomplaint {

    @Column( name = "functional_impairment")
    @Schema( name        = "functional_impairment",
            description = "Функциональные нарушения",
            example     = "Симптомы поражения пирамидного тракта" )
    private String functional_impairment;

    public Сomplaint(){
    }

    public Сomplaint( String functional_impairment ){
        this.functional_impairment = functional_impairment;  
    }
    
}
