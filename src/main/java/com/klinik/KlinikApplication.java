package com.klinik;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition( info = @Info( title = "API for Klinika DB",
                                  version = "1.0.0",
                                  description = "Лечение ПИТРС"))
public class KlinikApplication {

    public static void main(String[] args) {
        SpringApplication.run(KlinikApplication.class, args);
        System.out.println("Klinika start");
    }

}
