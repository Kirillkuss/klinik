package com.klinik.response.report;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.klinik.entity.CardPatient;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class CardPatinetReport {

    private CardPatient card;
    private Integer countTreatment;
    private List<ResponseReport> treatment;
    
}
