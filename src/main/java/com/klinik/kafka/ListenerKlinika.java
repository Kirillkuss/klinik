package com.klinik.kafka;

import java.time.LocalDateTime;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klinik.kafka.message.Animal;
import com.klinik.kafka.message.SendMessageBroker;
import com.klinik.kafka.message.repository.AnimalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ListenerKlinika {

    private final AnimalRepository animalRepository;

    @KafkaListener( topics = "klinikSecond", groupId = "KlinikGroup")
    public void getAMessage( SendMessageBroker sendMessageBroker ) throws Exception{
        log.info( sendMessageBroker.toString());
        ObjectMapper objectMapper = new ObjectMapper();
        Animal animal = objectMapper.readValue( new String( objectMapper.writeValueAsBytes(sendMessageBroker.getMessage()), "UTF-8" ), Animal.class );
        if ( animal != null ){
            animal.setDateRecord( LocalDateTime.now());
            animalRepository.save( animal );
            log.info( "save Animal" );
        } 
    }

}
