package com.klinik.kafka.serializer;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.klinik.entity.Document;
import com.klinik.kafka.message.SendMessageBroker;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageBrokerSerializer implements Serializer<SendMessageBroker>{

    @Override
    public byte[] serialize( String topic, SendMessageBroker sendMessageBroker) {
        try {
            if ( sendMessageBroker == null ){
                log.info("Null received at serializing");
                return null;
            }
            return new ObjectMapper().registerModule(new JavaTimeModule())
                                     .writeValueAsBytes( sendMessageBroker );
        } catch ( Exception e ) {
            throw new SerializationException( "Error when serializing SendMessageBroker to byte[] \n" + e.getMessage() );
        }
    }
    
}
