package com.klinik;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

//http://localhost:8082/web/swagger-ui/index.html
//http://localhost:8082/web/login
//http://localhost:8082/web/klinika
@Slf4j 
@SpringBootApplication
@EnableScheduling
@EnableCaching
@OpenAPIDefinition( info = @Info( title = "КЛИНИК АПИ",
                                  version = "1.0.0",
                                  description = "КЛИНИК АПИ"))
public class KlinikApplication {

    public static void main(String[] args) {
        SpringApplication.run(KlinikApplication.class, args);
        log.info( " Klinika start success " );
    }

}
