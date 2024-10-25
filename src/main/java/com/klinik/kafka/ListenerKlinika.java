package com.klinik.kafka;

import java.time.LocalDateTime;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import com.klinik.kafka.message.Animal;
import com.klinik.kafka.message.repository.AnimalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ListenerKlinika {

    private final AnimalRepository animalRepository;

    @KafkaListener( topics = "klinikSecond", groupId = "KlinikGroup")
    public void getAnimal( Animal animal ){
        if ( animal != null ){
            animal.setDateRecord( LocalDateTime.now() );
            animalRepository.save( animal );
            log.info( "Record animal to database");
        }
    }
}
