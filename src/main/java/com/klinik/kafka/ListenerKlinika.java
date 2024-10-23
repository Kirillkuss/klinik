package com.klinik.kafka;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Configuration
public class ListenerKlinika {

    @KafkaListener( topics = "topicKlinikSecond", groupId = "KlinikGroup")
    public void getMessageSecondTopic( String message ){
        log.info( message );
    }

    
    @KafkaListener( topics = "topicKlinikFourth", groupId = "KlinikGroup-AA")
    public void getMessageFourthTopic( String message ){
        log.info( message );
    }
    
}
