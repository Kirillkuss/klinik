package com.klinik.response.report;

import java.util.List;

import com.klinik.entity.CardPatient;
import com.klinik.entity.RecordPatient;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecordPatientReport {

    @JsonInclude(Include.NON_NULL)
    private CardPatient card;
    @JsonInclude(Include.NON_NULL)
    private Long count_record_for_time;
    @JsonInclude(Include.NON_NULL)
    private List<RecordPatient> listRecordPatient;

}
