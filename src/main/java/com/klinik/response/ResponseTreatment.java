package com.klinik.response;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.klinik.entity.Treatment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseTreatment  {

    public List<Treatment> response;
    public Treatment treatment;
    public ResponseTreatment( ){
    }
    public ResponseTreatment( List<Treatment> response ){
        this.response = response;
    }
    public ResponseTreatment( Treatment response ){
        this.treatment = response;
    }
}
