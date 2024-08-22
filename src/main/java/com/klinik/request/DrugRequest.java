package com.klinik.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DrugRequest {

    @Schema( name        = "name",
             description = "Наименование",
             example     = "Карвалол 2 чайные ложки в день",
             required    = true )
    private String name;
    @Schema( name        = "idDrugTreatment",
             description = "ИД мед. лечения",
             example     = "1",
             required    = true )
    private Long idDrugTreatment;
    
}
