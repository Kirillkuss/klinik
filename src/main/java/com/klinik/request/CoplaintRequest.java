package com.klinik.request;

import jakarta.persistence.Column;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class CoplaintRequest {
    
    @Column( name = "idCard")
    @Schema( name        = "idCard",
             description = "Ид карты пациента",
             example     = "1",
             required    = true )
    private Long idCard;
    @Column( name = "idComplaint")
    @Schema( name        = "idComplaint",
             description = "ИД под жалобы",
             example     = "1",
             required    = true )
    private Long idComplaint;
    
}
