package com.klinik.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import org.springframework.format.annotation.DateTimeFormat;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestTreatment {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column( name = "timeStartTreatment")
    @Schema( name        = "timeStartTreatment",
             description = "Дата начала лечения",
             example     = "2023-01-22 18:00:00.745",
             required    = true )
    private LocalDateTime timeStartTreatment;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column( name = "endTimeTreatment")
    @Schema( name        = "endTimeTreatment",
             description = "Дата окончания лечения",
             example     = "2024-08-22 18:00:00.745",
             required    = true )
    private LocalDateTime endTimeTreatment;

    @Schema( name        = "idDrug",
             description = "ИД мед леч",
             example     = "1",
             required    = true )
    private Long idDrug;

    @Schema( name        = "idCardPatient",
              description = "ИД карты пациента",
              example     = "1",
              required    = true )
    private Long idCardPatient;

    @Schema( name        = "idRehabilitationSolution",
             description = "ИД реаб леч",
             example     = "1",
             required    = true )
    private Long idRehabilitationSolution;

    @Schema( name        = "idDoctor",
             description = "ИД доктора",
             example     = "1",
             required    = true )
    private Long idDoctor;
    
}
