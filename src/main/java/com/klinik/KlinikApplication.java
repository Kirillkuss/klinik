package com.klinik;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition( info = @Info( title = "ВЫПУСКНАЯ КВАЛИФИКАЦИОННАЯ РАБОТА БАКАЛАВРА",
                                  version = "1.0.0",
                                  description = "Тема: 'Разработка системы для оценки эффективности в реабилитации пациентов с рассеянным склерозом' ",
                                  contact = @Contact(name = "Обратная связь",
                                  url = "https://t.me/lissong",
                                  email = "lisa6318@mail.ru"))
                                  )

public class KlinikApplication {

    public static void main(String[] args) {
        SpringApplication.run(KlinikApplication.class, args);
        System.out.println("Klinika start");
    }

}
