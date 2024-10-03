package com.klinik.liquibase.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table( name = "user_test", schema = "klinik_schema")
@ToString
@Getter
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor
public class UserKlinik {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column( name = "id")
    @Schema( name        = "id",
            description = "ИД",
            example     = "1",
            required    = true )
    @JsonInclude(Include.NON_NULL)
    private Long id;

    @Column( name = "login_user")
    @Schema( name        = "loginUser",
            description = "логин",
            example     = "test",
            required    = true )
    private String loginUser;

    @Column( name = "password_user")
    @Schema( name        = "passwordUser",
            description = "пароль",
            example     = "456565sdf34",
            required    = true )
    private String passwordUser;

    @Column( name = "fio")
    @Schema( name        = "fio",
            description = "ФИО",
            example     = "Федоров Сергей Михайловчи",
            required    = true )
    private String fio;
    
}
