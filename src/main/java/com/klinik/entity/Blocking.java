package com.klinik.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table( name = "User_Blocking")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Blocking implements Serializable{


    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column( name = "id_block")
    @Schema( name        = "idBlock",
             description = "ИД блокировки",
             example     = "100",
             required    = true )
    private Long idBlock;


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column( name = "date_block")
    @Schema( name        = "dateBlock",
             description = "Дата и время блокировки",
             example     = "2023-01-22 18:00:00.745",
             required    = true )
    private LocalDateTime dateBlock;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column( name = "date_plan_unblock")
    @Schema( name        = "datePlanUnblock",
             description = "Дата и время планируемой разблокировки",
             example     = "2023-07-22 18:00:00.745",
             required    = true )
    private LocalDateTime datePlanUnblock;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column( name = "date_unblock")
    @Schema( name        = "dateUnblock",
             description = "Дата и время разблокировки",
             example     = "2023-07-22 18:00:00.745",
             required    = true )
    private LocalDateTime dateUnblock;


    @Column( name = "status")
    @Schema( name        = "status",
             description = "статус",
             example     = "true",
             required    = true )
    private Boolean status;

    @Column( name = "status_block")
    @Schema( name        = "statusBlock",
             description = "Статус блокировки",
             example     = "1",
             required    = true )
    private Integer statusBlock; 

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    
}
