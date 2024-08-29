package com.klinik;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j 
@SpringBootApplication
@OpenAPIDefinition( info = @Info( title = "КЛИНИК АПИ",
                                  version = "1.0.0",
                                  description = "КЛИНИК АПИ"))
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class KlinikApplication {

    public static void main(String[] args) {
        SpringApplication.run(KlinikApplication.class, args);
        log.info( " Klinika start success " );
    }

}
