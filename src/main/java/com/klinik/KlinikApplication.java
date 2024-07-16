package com.klinik;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource; 

@Slf4j 
@PropertySource("classpath:application.properties")
@SpringBootApplication
@OpenAPIDefinition( info = @Info( title = "КЛИНИК АПИ",
                                  version = "1.0.0",
                                  description = "КЛИНИК АПИ"))
public class KlinikApplication {

    public static void main(String[] args) {
        SpringApplication.run(KlinikApplication.class, args);
        log.info( " Klinika start success " );
    }
    

}
