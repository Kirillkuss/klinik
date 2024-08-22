package com.klinik.request.reports;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReportPatientRequest {
    
    @Schema( name        = "idPatient",
             description = "Ид пациента",
             example     = "1",
             required    = true )
    private Long idPatient;

    @Schema( name        = "start",
             description = "дата и время с какого",
             example     = "2021-01-24T14:02:35.584",
             required    = true )
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime start;

    @Schema( name        = "end",
             description = "дата и время по какое ",
             example     = "2024-08-24T14:02:35.584",
             required    = true )
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime end;
}
