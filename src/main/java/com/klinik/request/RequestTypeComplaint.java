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
public class RequestTypeComplaint {

    @Schema( name        = "name",
             description = "Наименование под жалобы",
             example     = "Парапарезы",
             required    = true )
    private String name;
    @Schema( name        = "idComplaint",
             description = "ИД под жалобы",
             example     = "1",
             required    = true )
    private Long idComplaint;
    
}
