package com.klinik.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table( name = "key_entity")
@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
@JsonInclude(Include.NON_NULL)
public class KeyEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column( name = "id_key")
    @Schema( name        = "id",
            description = "id",
            example     = "100",
            required    = true )
    @JsonInclude(Include.NON_NULL) 
    private Long id;

    @Column( name = "key_alice")
    private String alice;

    @Column( name = "date_create")
    @Schema( name        = "dateCreate",
             description  = "dateCreate",
             required     = true )
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateCreate;

    @Column( name = "key_public")
    @Schema( name        = "publicKey",
             description  = "publicKey",
             example      = "jdbjsfkwehwiuy782342iuhsifhsdifw",
             required     = true )
    private String publicKey;
    
    @Column( name = "key_private")
    @Schema( name        = "privateKey",
             description  = "privateKey",
             example      = "jdbjsfkwehwiuy782342iuhsifhsdifw",
             required     = true )
    private String privateKey;

}
