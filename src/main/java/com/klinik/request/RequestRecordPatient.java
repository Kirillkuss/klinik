package com.klinik.request;

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
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestRecordPatient {
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Schema( name        = "dateRecord",
             description = "Дата и время записи",
             example     = "2023-01-19T12:00:00.000Z",
             required    = true )
    private LocalDateTime dateRecord;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Schema( name        = "dateAppointment",
             description = "Дата и время приема",
             example     = "2023-02-01T14:00:00.605Z",
             required    = true )
    private LocalDateTime dateAppointment;
    @Schema( name        = "numberRoom",
             description = "Номер кабинета",
             example     = "444",
             required    = true )
    private Long numberRoom;
    @Schema( name        = "idDoctor",
             description = "Ид доктора",
             example     = "1",
             required    = true )
    private Long idDoctor;
    @Schema( name        = "idCardPatient",
             description = "Ид карты пациента",
             example     = "1",
             required    = true )
    private Long idCardPatient;
}
