package com.klinik;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication; 

@Slf4j 
@SpringBootApplication
@OpenAPIDefinition( info = @Info( title = "КЛИНИК АПИ",
                                  version = "1.0.0",
                                  description = "КЛИНИК АПИ"))
public class KlinikApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(KlinikApplication.class);
            application.setAdditionalProfiles("ssl");
            application.run(args);
        log.info( " Klinika start success " );
    }

}
