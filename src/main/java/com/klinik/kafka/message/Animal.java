package com.klinik.kafka.message;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table( name= "klinik_animal")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Animal implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id_animal")
    @Schema( name        = "id",
             description = "ИД питомца",
             example     = "1",
             required    = true )
    private Long id;

    @Column( name = "name_animal")
    @Schema( name        = "name",
             description = "Название питомца",
             example     = "Cat",
             required    = true )
    private String name;
    
    @Column( name = "amount")
    @Schema( name        = "amount",
             description = "Стоимость",
             example     = "400",
             required    = true )
    private BigDecimal amount;

    @Column( name = "count")
    @Schema( name        = "count",
             description = "Количество",
             example     = "30",
             required    = true )
    private Integer count;

    @Column( name = "date_record")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Schema( name        = "dateRecord",
             description = "дата записи в бд",
             example     = "2023-02-01T14:00:00.605Z",
             required    = true )
    private LocalDateTime dateRecord;

}

