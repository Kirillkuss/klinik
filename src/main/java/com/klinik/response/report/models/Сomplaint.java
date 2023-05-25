package com.klinik.response.report.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Сomplaint {

    private String functional_impairment;

    public Сomplaint(){
    }

    public Сomplaint( String functional_impairment ){
        this.functional_impairment = functional_impairment;  
    }
    
}
